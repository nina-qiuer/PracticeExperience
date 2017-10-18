package com.tuniu.gt.satisfaction.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.satisfaction.entity.MQLogEntity;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionMsgSendEntity;
import com.tuniu.gt.satisfaction.service.IMQLogService;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionService;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionMsgSendService;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionService;
import com.tuniu.gt.sms.entity.MessageParamEntity;
import com.tuniu.gt.sms.entity.MessageTemplateParamValuePair;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;

import net.sf.json.JSONObject;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

@Service("complaint_action-fixSatisfactionData")
@Scope("prototype")
public class FixSatisfactionDataAction extends
		FrmBaseAction<IMQLogService, MQLogEntity> {

	private static Logger logger = Logger.getLogger("FixSatisfactionDataAction.class");

	public FixSatisfactionDataAction() {
		setManageUrl("fixSatisfactionData");
	}

	// 短信发送记录service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfactionMsgSend")
	private ISignSatisfactionMsgSendService signSatisfactionMsgSendService;

	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-mqLog")
	public void setService(IMQLogService service) {
		this.service = service;
	};

	// 售前客服满意度service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-preSaleSatisfaction")
	private IPreSaleSatisfactionService preSaleSatisfactionService;

	// 门市/上门签约客服满意度service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfaction")
	private ISignSatisfactionService signSatisfactionService;

	// MQ消息及接口信息记录service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-mqLog")
	private IMQLogService mqLogService;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;

	public String execute(){
		return "fix_satisfaction_data_list";
	}
	
	
	
	public String fixData() throws ParseException {
		String dataBegin = request.getParameter("search.dataBegin");
		String dataEnd = request.getParameter("search.dataEnd");
		Map timeMap = new HashMap();
		timeMap.put("dataBegin", dataBegin);
		timeMap.put("dataEnd", dataEnd);
		List list = mqLogService.getFixData(timeMap);
		for (Object obj : list) {
			Map map = (Map) obj;
			JSONObject jsonObj = JSONObject.fromObject(map.get("mqMsg"));
			String type = jsonObj.getString("type");
			String orderId = jsonObj.getString("orderId");
			String productType = jsonObj.getString("productType");
			Date createTime =(Date) map.get("createTime");

			if (orderId != null && productType != null && type != null) {
				if ("2".equals(productType)) {
					jsonObj = getOrderInfoByOrderIdFromGroup(orderId);
				} else if ("1".equals(productType)) {

				} else if ("3".equals(productType)) {

				}
				SignSatisfactionEntity signSatisfactionEntity = getSignSatisfactionEntity(productType, jsonObj);
				int preSaleSatisfactionCount = preSaleSatisfactionService.getCountByOrderId(Integer.parseInt(orderId));
				int signSatisfactionCount = signSatisfactionService.getCountByOrderId(Integer.parseInt(orderId));
				if ("1".equals(type)) { // 正常的订单
					boolean isSendMsg = getIsSendMsg(productType,signSatisfactionEntity.getSignType());
					if (isSendMsg) {
						if (signSatisfactionCount <= 0) { // 门市/上门客服满意度表订单信息不存在
							signSatisfactionEntity.setCreateTime(createTime);
							signSatisfactionService.insert(signSatisfactionEntity);
						}else{
							signSatisfactionEntity.setCreateTime(null);
							signSatisfactionService.update(signSatisfactionEntity);
						}
						sendMobileMsgAndSaveInfo(signSatisfactionEntity.getTelNo(),signSatisfactionEntity.getId(),signSatisfactionEntity.getCustomName());
					}
				}

			} else {
				logger.info("---------------MQ value is error: orderId=" + orderId
						+ "----productType=" + productType + "----type=" + type
						+ "---------------");
			}
		}
		return "fix_satisfaction_data_list";
	}

	/**
	 * 通过订单id获取跟团接口订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public JSONObject getOrderInfoByOrderIdFromGroup(String orderId) {
		JSONObject result = null;
		// String url =
		// "http://crm.dev.tuniu.org/interface/rest/server/order/OrderInfoQueryInterface.php";
		String url = Constant.CONFIG.getProperty("ORDER_INFO_GROUP");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("func", "getOrderDetailForComplaint");
		Map<String, String> routeMp = new HashMap<String, String>();
		routeMp.put("order_id", orderId);
		mp.put("params", routeMp);
		trestClient.setData(JSONObject.fromObject(mp).toString());

		try {
			String execute = trestClient.execute();
			System.out.println("-------------------------------------data fix : "+execute);
			MQLogEntity entity = new MQLogEntity();
			entity.setInteMsg(execute);
			entity.setCreateTime(new Date());
			mqLogService.insert(entity);
			// String execute =
			// "{\"success\":true,\"msg\":\"success\",\"errorCode\":130000,\"data\":{\"orderId\":\"2938021\",\"orderType\":0,\"preSaleId\":null,\"preSaleName\":null,\"customerLeaderId\":null,\"customerLeader\":null,\"routeId\":null,\"backDate\":null,\"outDate\":null,\"productLeaderId\":null,\"productLeader\":null,\"customName\":null,\"telNo\":null,\"telCount\":null,\"orderStatus\":3,\"faceSaleId\":null,\"faceSaleName\":null,\"orderSign\":null}}";
			JSONObject jsonObject = JSONObject.fromObject(execute);
			Boolean isSuccess = jsonObject.getBoolean("success");
			if (isSuccess) {
				result = jsonObject.getJSONObject("data");
			} else {
				logger.info("---------------getOrderInfoByOrderIdFromGroup() failed !----"
						+ execute + "---------------");
			}

		} catch (TRestException e) {
			logger.error("---------------getOrderInfoByOrderIdFromGroup() TRestException !---------------",e);
		}
		return result;
	}


	/**
	 * 封装PreSaleSatisfactionEntity对象
	 * 
	 * @param productType
	 * @param jsonObject
	 * @return
	 */
	private SignSatisfactionEntity getSignSatisfactionEntity(
			String productType, JSONObject jsonObject) {
		SignSatisfactionEntity signSatisfactionEntity = new SignSatisfactionEntity();
		if (null == jsonObject || jsonObject.isNullObject()) {
			logger.info("---------------getPreSaleSatisfactionEntity()  jsonObject is null !---------------");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if ("1".equals(productType)) {
				// 自助游
				signSatisfactionEntity.setOrderId(Integer.valueOf(jsonObject
						.getString("orderId")));
				String orderSign = jsonObject.getString("signType");
				signSatisfactionEntity.setSignType(isNull(orderSign));
				signSatisfactionEntity.setFaceSaleId(isNull(jsonObject
						.getString("receptionistId")));
				signSatisfactionEntity.setFaceSaleName(isNull(jsonObject
						.getString("receptionistName")));
				signSatisfactionEntity.setCustomerLeaderId(isNull(jsonObject
						.getString("salerManager")));
				signSatisfactionEntity.setCustomerLeader(isNull(jsonObject
						.getString("salerManagerName")));
				signSatisfactionEntity.setProductLeaderId(isNull(jsonObject
						.getString("productManagerId")));
				signSatisfactionEntity.setProductLeader(isNull(jsonObject
						.getString("productManagerName")));
				signSatisfactionEntity.setOrderType("1");
				JSONObject product = JSONObject.fromObject(jsonObject
						.getString("product"));
				String routeId = product.getString("productId");
				if (routeId != null) {
					signSatisfactionEntity
							.setRouteId(Integer.parseInt(routeId));
				}
				String outDateStr = product.getString("startDate");
				String endDateStr = product.getString("endDate");
				try {
					if (outDateStr != null && !"".equals(outDateStr)
							&& !"null".equals(outDateStr)) {
						signSatisfactionEntity.setOutDate(formatter
								.parse(outDateStr));
					}
					if (endDateStr != null && !"".equals(endDateStr)
							&& !"null".equals(endDateStr)) {
						signSatisfactionEntity.setBackDate(formatter
								.parse(endDateStr));
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				String telNo = jsonObject.getString("defaultContacterTel");
				String customName = jsonObject
						.getString("defaultContacterName");
				signSatisfactionEntity.setCustomName(isNull(customName));
				signSatisfactionEntity.setTelNo(isNull(telNo));
			} else if ("2".equals(productType)) {
				// 跟团
				signSatisfactionEntity.setOrderId(Integer.valueOf(jsonObject
						.getString("orderId")));
				String orderSign = jsonObject.getString("orderSign");
				signSatisfactionEntity.setSignType(isNull(orderSign));
				signSatisfactionEntity.setFaceSaleId(isNull(jsonObject
						.getString("faceSaleId")));
				signSatisfactionEntity.setFaceSaleName(isNull(jsonObject
						.getString("faceSaleName")));
				signSatisfactionEntity.setCustomerLeaderId(isNull(jsonObject
						.getString("customerLeaderId")));
				signSatisfactionEntity.setCustomerLeader(isNull(jsonObject
						.getString("customerLeader")));
				signSatisfactionEntity.setProductLeaderId(isNull(jsonObject
						.getString("productLeaderId")));
				signSatisfactionEntity.setProductLeader(isNull(jsonObject
						.getString("productLeader")));
				signSatisfactionEntity.setOrderType("0");
				String routeId = jsonObject.getString("routeId");
				if (routeId != null && !"null".equals(routeId)) {
					signSatisfactionEntity
							.setRouteId(Integer.parseInt(routeId));
				}
				String outDateStr = jsonObject.getString("outDate");
				String endDateStr = jsonObject.getString("backDate");
				try {
					if (outDateStr != null && !"".equals(outDateStr)
							&& !"null".equals(outDateStr)) {
						signSatisfactionEntity.setOutDate(formatter
								.parse(outDateStr));
					}
					if (endDateStr != null && !"".equals(endDateStr)
							&& !"null".equals(endDateStr)) {
						signSatisfactionEntity.setBackDate(formatter
								.parse(endDateStr));
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				String telNo = jsonObject.getString("telNo");
				String customName = jsonObject.getString("customName");
				signSatisfactionEntity.setCustomName(isNull(customName));
				signSatisfactionEntity.setTelNo(isNull(telNo));
			} else if ("3".equals(productType)) {
				// 团队
				signSatisfactionEntity.setOrderId(Integer.valueOf(jsonObject
						.getString("orderId")));
				String orderSign = jsonObject.getString("orderSign");
				signSatisfactionEntity.setSignType(isNull(orderSign));
				signSatisfactionEntity.setFaceSaleId(isNull(jsonObject
						.getString("faceSaleId")));
				signSatisfactionEntity.setFaceSaleName(isNull(jsonObject
						.getString("faceSaleName")));
				signSatisfactionEntity.setCustomerLeaderId(isNull(jsonObject
						.getString("customerLeaderId")));
				signSatisfactionEntity.setCustomerLeader(isNull(jsonObject
						.getString("customerLeader")));
				signSatisfactionEntity.setProductLeaderId(isNull(jsonObject
						.getString("productLeaderId")));
				signSatisfactionEntity.setProductLeader(isNull(jsonObject
						.getString("productLeader")));
				signSatisfactionEntity.setOrderType("2");
				String routeId = jsonObject.getString("routeId");
				if (routeId != null && !"null".equals(routeId)) {
					signSatisfactionEntity
							.setRouteId(Integer.parseInt(routeId));
				}
				String outDateStr = jsonObject.getString("outDate");
				String endDateStr = jsonObject.getString("backDate");
				try {
					if (outDateStr != null && !"".equals(outDateStr)
							&& !"null".equals(outDateStr)) {
						signSatisfactionEntity.setOutDate(formatter
								.parse(outDateStr));
					}
					if (endDateStr != null && !"".equals(endDateStr)
							&& !"null".equals(endDateStr)) {
						signSatisfactionEntity.setBackDate(formatter
								.parse(endDateStr));
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				String telNo = jsonObject.getString("telNo");
				if (isNum(telNo.split("\\|")[0])) {
					signSatisfactionEntity
							.setTelNo(isNull(telNo.split("\\|")[0]));
				} else {
					signSatisfactionEntity.setTelNo("");
				}
				String customName = jsonObject.getString("customName");
				signSatisfactionEntity.setCustomName(isNull(customName));
			}
		}
		signSatisfactionEntity.setCreateTime(new Date());
		return signSatisfactionEntity;
	}

	private Map getMap4updateTelCount(String productType,
			JSONObject jsonObject, String type) {
		Map map = new HashMap();
		if (null == jsonObject || jsonObject.isNullObject()) {
			logger.info("---------------getPreSaleSatisfactionEntity()  jsonObject is null !---------------");
			map = null;
		} else {
			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if ("1".equals(productType)) {
				// 自助游
			} else if ("2".equals(productType)) {
				// 跟团
				map.put("orderId",
						Integer.parseInt(jsonObject.getString("orderId")));
				map.put("orderId", jsonObject.getString("telCount"));
			} else if ("3".equals(productType)) {

			}
		}
		return map;
	}

	/**
	 * 判断是否需要发送满意度调查短信
	 * 
	 * @param productType
	 * @param signType
	 * @return
	 */
	private boolean getIsSendMsg(String productType, String signType) {
		boolean isSendMsg = false;
		if ("1".equals(productType)) {
			// 自助游 orderSign=1门市签约，orderSign=2上门签约
			if ("1".equals(signType) || "2".equals(signType)) {
				isSendMsg = true;
			}
		} else if ("2".equals(productType)) {
			// 跟团 orderSign=1门市签约，orderSign=5上门签约
			if ("1".equals(signType) || "5".equals(signType)) {
				isSendMsg = true;
			}
		} else if ("3".equals(productType)) {
			// 跟团 orderSign=1门市签约，orderSign=4上门签约
			if ("1".equals(signType) || "4".equals(signType)) {
				isSendMsg = true;
			}
		}
		return isSendMsg;
	}

	/**
	 * 获取本机IP
	 * 
	 * @return 返回IP地址
	 * @throws UnknownHostException
	 */
	protected String myIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public void sendMobileMsgAndSaveInfo(String telNo, int satisId,
			String customName) {
//		HttpClient client = new HttpClient();
//		// client.getHostConfiguration().setHost("www.imobile.com.cn", 80,
//		// "http");
//		String url = Constant.CONFIG.getProperty("SEND_MSG_SATISFACTION");
//		String msg = "尊敬的"
//				+ customName
//				+ "，感谢您订购途牛旅游产品，诚邀您对门市/上门签约人员的服务进行点评，1非常满意，2满意，3一般，4不满意，回复数字加点评意见即可。";
//		PostMethod post = new PostMethod(url);
//		NameValuePair client_ip = null;
//		try {
//			client_ip = new NameValuePair("client_ip", myIp());
//		} catch (UnknownHostException e1) {
//			logger.error("-----------------------------------获取ip异常-------------------------------------",e1);
//		}
//		NameValuePair mobile_num = new NameValuePair("mobile_num", telNo);
//		NameValuePair sms_send_content = new NameValuePair("sms_send_content",
//				msg);
//		NameValuePair sms_task_id = new NameValuePair("sms_task_id", "102");
//		NameValuePair op_id = new NameValuePair("op_id", "104");
//		post.setRequestBody(new NameValuePair[] { client_ip, mobile_num,
//				sms_send_content, sms_task_id, op_id });
//		HttpMethod method = post;// 使用POST方式提交数据
//		try {
//			client.getParams().setParameter(
//					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//			client.executeMethod(method);
//		} catch (HttpException e1) {
//			logger.error(e1.getMessage(), e1);
//		} catch (IOException e1) {
//			logger.error(e1.getMessage(), e1);
//		}
//		// 打印服务器返回的状态
//		logger.info(method.getStatusLine());
//		// 打印结果页面
//		String response = null;
//		try {
//			response = new String(method.getResponseBodyAsString().getBytes(
//					"utf-8"));
//		} catch (UnsupportedEncodingException e1) {
//			logger.error(e1.getMessage(), e1);
//		} catch (IOException e1) {
//			logger.error(e1.getMessage(), e1);
//		}
//		// 打印返回的信息
//		logger.info(response);
//		saveMsgSendInfo(customName, satisId, response);
//		method.releaseConnection();
		
		
		String content = "尊敬的"
				+ customName
				+ "，感谢您订购途牛旅游产品，诚邀您对门市/上门签约人员的服务进行点评，1非常满意，2满意，3一般，4不满意，回复数字加点评意见即可。";
		MessageParamEntity messageParamEntity=new MessageParamEntity();
    	messageParamEntity.setTemplateId(1268);//人工触发短信
    	messageParamEntity.setMobileNum(Arrays.asList(telNo.split(",")));
    	MessageTemplateParamValuePair messageContent=new MessageTemplateParamValuePair("content", content);
    	List<MessageTemplateParamValuePair> messageContentList=new ArrayList<MessageTemplateParamValuePair>();
    	messageContentList.add(messageContent);
    	messageParamEntity.setSmsTemplateParams(messageContentList);
    	JSONObject messageJson = JSONObject.fromObject(messageParamEntity);
    	tspService.sendShortMessageService(messageJson);
	}

	private void saveMsgSendInfo(String customerName, int satisId,
			String response) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		String telNo = document.selectSingleNode(
				"/TUNIU_SMS/SmsDetails/sms/mobile_num").getText();
		String msg = document.selectSingleNode(
				"/TUNIU_SMS/SendResult/sms_send_content").getText();
		String result = document.selectSingleNode(
				"/TUNIU_SMS/SmsDetails/sms/code_num").getText();
		String errorCode = result;
		if (!"0".equals(result)) {
			result = "1";
		}
		String errorMsg = document.selectSingleNode(
				"/TUNIU_SMS/SmsDetails/sms/code_name").getText();
		logger.info("telNo=" + telNo + "-----msg=" + msg + "-----result=" + result
				+ "-----errorCode=" + errorCode + "-----errorMsg=" + errorMsg);
		SignSatisfactionMsgSendEntity signSatisfactionMsgSendEntity = new SignSatisfactionMsgSendEntity();
		signSatisfactionMsgSendEntity.setTelNo(telNo);
		signSatisfactionMsgSendEntity.setCustomerName(customerName);
		signSatisfactionMsgSendEntity.setMsg(msg);
		signSatisfactionMsgSendEntity.setCreateTime(new Date());
		signSatisfactionMsgSendEntity.setResult(Integer.parseInt(result));
		signSatisfactionMsgSendEntity.setErrorCode(errorCode);
		signSatisfactionMsgSendEntity.setErrorMsg(errorMsg);
		signSatisfactionMsgSendEntity.setSatisId(satisId);
		signSatisfactionMsgSendService.insert(signSatisfactionMsgSendEntity);
	}

	public String isNull(String value) {
		return value == null ? "" : "null".equals(value) ? "" : value;
	}

	public static boolean isNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

}
