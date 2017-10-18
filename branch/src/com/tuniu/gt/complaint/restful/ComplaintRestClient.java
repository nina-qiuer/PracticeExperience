package com.tuniu.gt.complaint.restful;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.HttpClientHelper;
import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.entity.AgentStatusLog;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.vo.CmpImproveVo;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.punishprd.entity.RemarkEntity;
import com.tuniu.gt.sms.entity.MessageParamEntity;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.RemoteFileUploadUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestMethod;

/**
 * 投诉系统REST客户端
 */
public class ComplaintRestClient {
	

    private static Logger logger = Logger.getLogger(ComplaintRestClient.class);
	
	private static String tuniuURL = Constant.CONFIG.getProperty("TUNIU_URL");
	
	private static String routeURL = Constant.CONFIG.getProperty("ROUTE_URL");
	
	private static String destCateURL = Constant.CONFIG.getProperty("DEST_CATEGORY_URL");
	
	private static String nbURL = Constant.CONFIG.getProperty("NB_URL");
	
	private static String nbLogURL = Constant.CONFIG.getProperty("NB_LOG_URL");//NB日志追加
	
	private static String nbURLNEW = Constant.CONFIG.getProperty("NB_URL_NEW");//nb供应商验证  新分销平台
	
	private static String nbLogURLNEW = Constant.CONFIG.getProperty("NB_LOG_URL_NEW");//NB日志追加   新分销平台
	
	private static String nbAddCommitURL = Constant.CONFIG.getProperty("NB_ADDCOMMIT");//追加沟通
	
	private static String nbRoomIdURL = Constant.CONFIG.getProperty("NB_ROOMID");//获取roomId
	
	private static String nbUpdateURL = Constant.CONFIG.getProperty("NB_UPDATECOMMIT");//修改沟通类型 转为已完成
	
	private static String nbTimeOutURL = Constant.CONFIG.getProperty("NB_TIMEOUT");//获取供应商超时回复数据
	
	private static String nbUpLoadURL = Constant.CONFIG.getProperty("NB_FILELOAD");//上传图片
	
	private static String nbAddRoomURL = Constant.CONFIG.getProperty("NB_ADDROOM");//加入房间
	
	private static String nbLastCommitURL = Constant.CONFIG.getProperty("NB_LASTCOMMIT");//查询最新聊天动态的投诉单列表
	
	private static String oaGiftURL = Constant.CONFIG.getProperty("OA_GIFT_URL");
	
	private static String crmURL = Constant.CONFIG.getProperty("CRM_URL");
	
	private static String touristURL = Constant.CONFIG.getProperty("TOURIST_URL");
	
	private static String pgaBatchQueryURL = Constant.CONFIG.getProperty("PGA_BATCHQUERY_URL");
	
	private static String reportsURL = Constant.CONFIG.getProperty("REPORTS_URL");
	
	private static String crmCustLevelURL = Constant.CONFIG.getProperty("CRM_CUST_LEVEL_URL");
	
	private static String uploadURL = Constant.CONFIG.getProperty("GFS_URL");
	
	private static String getStatProductByProductIdsURL = Constant.CONFIG.getProperty("STAT_PRODUCT_BY_PRODUCTIDS_URL"); 
	
	private static String getContentListURL = Constant.CONFIG.getProperty("GET_CONTENT_LIST"); 
	
	private static String qmsURL = Constant.CONFIG.getProperty("QMS_URL");
	
	private static String SEND_MSG_NEW = Constant.CONFIG.getProperty("SEND_MSG_NEW"); // 新短信服务接口
	
	private static String CRM_IVR_RECORDS = Constant.CONFIG.getProperty("CRM_IVR_RECORDS");
	
	private static String POI_SEARCH = Constant.CONFIG.getProperty("POI_SEARCH");
	
	private static String CUST_SALER_URL = Constant.CONFIG.getProperty("CUST_SALER_URL");
	
	public static String getTouchRedEmailContentFromQms(Integer qcId) {
	    logger.info("getTouchRedEmailContentFromQms Begin: qcId is " + qcId);
	    TRestClient trestClient = new TRestClient((qmsURL + "/access/qcBill/" + qcId + "/getTouchRedEmailContent"));
        trestClient.setMethod("GET");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("qcId", qcId);
        trestClient.setData(JSONObject.fromObject(paramMap).toString());
        String originalContent = "";
        try {
            String execute = trestClient.execute();
            logger.info("getTouchRedEmailContentFromQms Ing: ResponseMessage is " + execute);
            JSONObject jObject = JSONObject.fromObject(execute);
            boolean isSucc = jObject.getBoolean("success");
            if (isSucc) {
                originalContent = jObject.getString("data");
            }
        } catch (Exception e) {
        	logger.error("getTouchRedEmailContentFromQms failed", e);
        }
        logger.info("getTouchRedEmailContentFromQms End: qcId is " + qcId);
        
	    return originalContent;
	}
	
	public static int addQcBill(QcVo qc) {
		qc.setGroupFlag(1); // 投诉质检组
		qc.setQcTypeId(4); // 内外部客户反馈、投诉-投诉质检
		qc.setQcAffairSummary("客人投诉");
		logger.info("addQcBill Begin: cmpId is " + qc.getCmpId());
		TRestClient trestClient = new TRestClient(qmsURL + "/access/qcBill/sendToQms");
		trestClient.setMethod("POST");
		trestClient.setData(JSONObject.fromObject(qc).toString());
		int qcId = 0;
		try {
			String execute = trestClient.execute();
			logger.info("addQcBill Ing: ResponseMessage is " + execute);
			JSONObject jObject = JSONObject.fromObject(execute);
			boolean isSucc = jObject.getBoolean("success");
			if (isSucc) {
				qcId = jObject.getInt("data");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("addQcBill End: cmpId is " + qc.getCmpId() + ", qcId is " + qcId);
		return qcId;
	}
	
	/**
	 * 提交质量成本（质量工具+不成团）
	 * qcDataList 不能为空
	 */
	public static void submitQualityCost(List<Map<String, Object>> qcDataList) {
		logger.info("submitQualityCost Begin: qcDataList.size is " + qcDataList.size());
		TRestClient trestClient = new TRestClient(qmsURL + "/access/qualityCost/addQualityCosts");
		trestClient.setMethod("POST");
		for (Map<String, Object> map : qcDataList) {
			Date auditTime = (Date) map.get("auditTime");
			map.put("auditTime", auditTime.getTime());
		}
		trestClient.setData(JSONArray.fromObject(qcDataList).toString());
		try {
			String execute = trestClient.execute();
			logger.info("submitQualityCost Ing: ResponseMessage is " + execute);
			JSONObject jObject = JSONObject.fromObject(execute);
			boolean isSucc = jObject.getBoolean("success");
			if (isSucc) {
				logger.info("QualityCost Submit Success，num is " + qcDataList.size());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("submitQualityCost End");
	}
	
	/**
	 *  1. 对客有赔偿，并且对客赔偿金额 不全是质量工具赔款，并且投诉事宜分类  一级-二级 ！= ”旅行前咨询 - 不成团“
     *  2. 投诉单存在理论赔付发质检
	 *  3. 改进报告发质检
	 * @param qc
	 * @return
	 */
	public static int sendQcBill(QcVo qc) {
		qc.setGroupFlag(1); // 投诉质检组
		qc.setQcTypeId(4); // 内外部客户反馈、投诉-投诉质检
		qc.setQcAffairSummary("客人投诉");
		TRestClient trestClient = new TRestClient(qmsURL + "/access/qcBill/sendToQms");
		trestClient.setMethod("POST");
		trestClient.setData(JSONObject.fromObject(qc).toString());
		logger.info("sendQcBill request :"+JSONObject.fromObject(qc).toString());
		int qcId = 0;
		try {
			String execute = trestClient.execute();
			JSONObject jObject = JSONObject.fromObject(execute);
			logger.info("sendQcBill response is " + execute);
			boolean isSucc = jObject.getBoolean("success");
			if (isSucc) {
				qcId = jObject.getInt("data");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("sendQcBill End: cmpId is " + qc.getCmpId() + ", qcId is " + qcId);
		return qcId;
	}
	
	
	
	
	private static String  crmTourURL = Constant.CONFIG.getProperty("CRM_TOUR_URL");//通过订单号查询出游人信息
	
	/**
	 * 向网站发送投诉订单状态
	 */
	public static int sendOrderStatus(int orderId, String status) {
		logger.info("sendOrderStatus Begin: orderId is " + orderId + ", status is " + status);
		TRestClient trestClient = new TRestClient(tuniuURL);
		trestClient.setMethod("post");

		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("order_id", orderId);
		params.put("type", 2);
		params.put("status", status);
		params.put("update_time", DateUtil.formatDateTime(new Date()));

		data.put("func", "order.updateOrderStatus");
		data.put("params", params);

		trestClient.setData(JSONObject.fromObject(data).toString());
		int error = 1;
		try {
			String execute = trestClient.execute();
			logger.info("sendOrderStatus Ing: ResponseMessage is " + execute);
			JSONObject jObject = JSONObject.fromObject(execute);
			error = jObject.getInt("ERROR");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("sendOrderStatus End: orderId is " + orderId + ", status is " + status + ", error is " + error);
		return error;
	}

	/**
	 * 通过线路编号获取线路名称
	 * @param routeCode 线路编号
	 * @return
	 */
	public static String queryRouteNameByCode(String routeCode) {
		logger.info("queryRouteNameByCode Begin: routeCode is " + routeCode);
		String result = "";
		TRestClient trestClient = new TRestClient(routeURL);
		trestClient.setMethod("post");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("method", "getNumericInfoById");
		Map<String, String> routeMp = new HashMap<String, String>();
		routeMp.put("route_id", routeCode);
		mp.put("param", routeMp);
		trestClient.setData(JSONObject.fromObject(mp).toString());
		try {
			String execute = trestClient.execute();
			logger.info("queryRouteNameByCode Ing: ResponseMessage is " + execute);
			JSONObject fromObject = JSONObject.fromObject(execute);
			Object returnStatusObj = fromObject.get("returnStatus");
			if (null != returnStatusObj && returnStatusObj.toString().equals("1")) {
				Object object = fromObject.get("returnMessage");
				JSONArray fromObject2 = JSONArray.fromObject(object);
				if (null != fromObject2 && !fromObject2.isEmpty()) {
					Object object2 = fromObject2.get(0);
					JSONObject fromObject3 = JSONObject.fromObject(object2);
					if (null != fromObject3) {
						Object name = fromObject3.get("name");
						if (null != name) {
							result = name.toString();
						}
					}
				}
			}
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("queryRouteNameByCode End: routeCode is " + routeCode + ", result is " + result);
		return result;
	}
	
	/**
	 * 通过产品线ID获取产品目的地大类
	 * @param productLineID 产品线ID
	 * @return
	 */
	public static Map<String, Object> getDestCategory(int productLineId) {
		Map<String, Object> destCategory = new HashMap<String, Object>();
		destCategory.put("destCategoryId", 0);
		destCategory.put("destCategoryName", "");
		TRestClient trestClient = new TRestClient(destCateURL);
		trestClient.setMethod("GET");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productLineId", productLineId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("func", "getDestinationNameAndId");
		data.put("params", params);
		logger.info("getDestCategory Begin: requestData: " + JSONObject.fromObject(data).toString());
		trestClient.setData(JSONObject.fromObject(data).toString());
		try {
			String execute = trestClient.execute();
			logger.info("getDestCategory Ing: ResponseMessage is " + execute);
			JSONObject jObject = JSONObject.fromObject(execute);
			Object resultData = jObject.get("data");
			JSONArray fmtData = JSONArray.fromObject(resultData);
			if (null != fmtData && !fmtData.isEmpty()) {
				Object object = fmtData.get(0);
				JSONObject fmtObject = JSONObject.fromObject(object);
				if (null != fmtObject && !fmtObject.isEmpty()) {
					int catType = Integer.valueOf(fmtObject.get("catType").toString());
					String name = (String) fmtObject.get("name");
					destCategory.put("destCategoryId", catType);
					destCategory.put("destCategoryName", name);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("getDestCategory End: productLineId is " + productLineId + ", destCategory is " + JSONObject.fromObject(destCategory));
		return destCategory;
	}
	
	/**
	 * 推送消息到NB系统，isDistributeFlag 判断是否是新分销订单投诉，使用新的NB系统
	 * @param paramList
	 * @param isDistributeFlag
	 */
	public static void recordNbLog(List<Map<String, Object>> paramList, boolean isDistributeFlag) {
		TRestClient trestClient = new TRestClient(isDistributeFlag ? nbLogURLNEW: nbLogURL);
		trestClient.setMethod("post");
		String reqData = JSONArray.fromObject(paramList).toString();
		logger.info("recordNbLog Bgn：" + reqData);
		trestClient.setData(reqData);
		try {
			String execute = trestClient.execute();
			JSONObject jObject = JSONObject.fromObject(execute);
			logger.info("recordNbLog Ing：" + jObject);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("recordNbLog End");
	}
	
	/**
	 * 根据供应商Id判断是否使用NB系统，isDistributeFlag 判断是否是新分销订单投诉
	 * @param agencyId
	 * @param isDistributeFlag
	 * @return
	 */
	public static int getNbFlag(int agencyId, boolean isDistributeFlag) {
		logger.info("getNbFlag Begin: agencyId is " + agencyId);
		int nbFlag = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("agencyId", agencyId);
			map.put("menuId", "7-41"); // 对应NB系统投诉确认菜单
			String jsonStr = JSONObject.fromObject(map).toString();
			HttpClientHelper client = new HttpClientHelper();
			
			String url = isDistributeFlag ? nbURLNEW : nbURL;//新分销
			
			String responseMsg =  client.executeGet(url, jsonStr);
			logger.info("getNbFlag Ing: ResponseMessage is" + responseMsg);
			if(!"".equals(responseMsg)){
				
				JSONObject jsonObject = JSONObject.fromObject(responseMsg);
				if ((Boolean) jsonObject.get("success")) {
					nbFlag = 1;
				}
			}
		    
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("getNbFlag End: agencyId is " + agencyId + ", nbFlag is " + nbFlag);
		return nbFlag;
	}
	
	/**
	 * oa礼品接口获取礼品数据
	 */
	public static String giftListFromOa() {
		logger.info("giftListFromOa Begin");
		String resultStr = "[]";
		try {
			TRestClient client = new TRestClient(oaGiftURL, TRestMethod.GET, "");
			String ret = client.execute();
			logger.info("giftListFromOa Ing: ResponseMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			int isSuccess = jsonObject.getInt("flag");
			if (isSuccess == 1) {
				JSONArray giftInfo = jsonObject.getJSONArray("gift_info");
				resultStr = giftInfo.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("giftListFromOa End: resultStr is " + resultStr);
		return resultStr;
	}
	
	
	
	/**
	 * 提交礼品申请至oa
	 */
	public static Map<String, Object> addGiftApplyToOa(Map<String, Object> paramMap) {
		String jsonStr = JSONObject.fromObject(paramMap).toString();
		logger.info("addGiftApplyToOa Begin: paramMap is " + jsonStr);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			TRestClient client = new TRestClient(oaGiftURL + "/apply", TRestMethod.POST, jsonStr);
			String ret = client.execute();
			logger.info("addGiftApplyToOa Ing: ResponseMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			int isSuccess = jsonObject.getInt("flag");
			if (isSuccess == 1) {
				resultMap.put("oaId", jsonObject.getInt("id"));
				resultMap.put("oaUrl", jsonObject.getString("out_url"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("addGiftApplyToOa End: resultMap is " + JSONObject.fromObject(resultMap));
		return resultMap;
	}
	
	/**
	 * 投诉处理完成状态通知点评系统
	 * 投诉已完成、撤销时调用
	 * @param orderId
	 */
	public static void complaintState2Crm(ComplaintEntity entity) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", entity.getOrderId());
		param.put("complaintStatus", entity.getState());
		String jsonStr = JSONObject.fromObject(param).toString();
		logger.info("complaintState2Crm Begin: param is " + jsonStr);
		String result = "";
		try {
			TRestClient client = new TRestClient(crmURL, TRestMethod.POST, jsonStr);
			result = client.execute();
			//JSONObject jsonObject = JSONObject.fromObject(result);
			//boolean isSuccess = jsonObject.getBoolean("success");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("complaintState2Crm End: result is " + result);
	}
	
	
	/**
	 * 根据id获取联系人信息
	 */
	public static Map<String, Object> getTouristById(int id) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		JSONObject jsonInput = new JSONObject();
		JSONObject jsonParam = new JSONObject();
		jsonInput.put(Integer.toString(0), id);
		jsonParam.accumulate("func", "getTouristById");
		jsonParam.accumulate("params", jsonInput);
		logger.info("getTouristById Begin: param is " + jsonParam.toString());
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(touristURL, jsonParam.toString());
			
		    //TRestClient client = new TRestClient(touristURL, TRestMethod.GET, jsonParam.toString());
			//TRestClient client = new TRestClient("http://fab.tuniu.org/interface/tourist", TRestMethod.GET, jsonParam.toString());
			//String ret = client.execute();
			logger.info("getTouristById Ing: ret is " + ret);
			if(!"".equals(ret)){
				JSONObject jsonObject = JSONObject.fromObject(ret);	
				Boolean isSuccess = jsonObject.getBoolean("res");
				if(isSuccess) {
					JSONObject info = jsonObject.getJSONObject("0");
					if (null != info && !info.isNullObject()) {
						resMap.put("name", info.getString("name"));
						resMap.put("tel", info.getString("tel"));
						resMap.put("email", info.getString("email"));
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("getTouristById End: resMap is " + JSONObject.fromObject(resMap));
		return resMap;
	}
	
	public static int addTourist(String name, String tel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("tel", tel);
		param.put("person_type", 2);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("func", "addTourist");
		data.put("params", param);
		String jsonStr = JSONObject.fromObject(data).toString();
		logger.info("addTourist Begin: param is " + jsonStr);
		int personId = 0;
		try {
			TRestClient client = new TRestClient(touristURL, TRestMethod.POST, jsonStr);
			String result = client.execute();
			logger.info("addTourist Ing: resultMsg is " + result);
			JSONObject jsonObject = JSONObject.fromObject(result);
			boolean isSuccess = jsonObject.getBoolean("res");
			if (isSuccess) {
				personId = jsonObject.getInt("person_id");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("addTourist End: personId is " + personId);
		
		//如果接口返回值为0，说明注册联系人失败，记录警告日志。
		if(personId == 0)
		{
			logger.warn("addTourist failed!");
		}
		
		return personId;
	}
	
	
	/**
	 * 查询同团期的所有订单
	 */
    public static String querySameGroupOrders(String startTimeStr, String routeId) {
    	String orderNos = "";
    	if (!"0".equals(routeId)) {
    		String url = Constant.CONFIG.getProperty("COMPLAINT_TWICE_CONSUMING");
            TRestClient trestClient = new TRestClient(url);
            trestClient.setMethod("post");
            Map<String, Object> mp = new HashMap<String, Object>();
            mp.put("func", "getAllCtOrderNo");
            Map<String, Object> routeMp = new HashMap<String, Object>();
            routeMp.put("routeId", routeId);
            routeMp.put("startTime", startTimeStr);
            mp.put("params", routeMp);
            String reqData = JSONObject.fromObject(mp).toString();
            trestClient.setData(reqData);
            logger.info("querySameGroupOrders Bgn：" + reqData);
            try {
                String execute = trestClient.execute();
                JSONObject obj = JSONObject.fromObject(execute);
                logger.info("querySameGroupOrders Ing：" + obj);
                if (obj.containsKey("success") && obj.getBoolean("success")) {
                    orderNos = obj.getJSONObject("data").getString("orderIds");
                }
            } catch (TRestException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("querySameGroupOrders End：" + orderNos);
    	}
        return orderNos;
    }
	
    public static EarlyWarningOrderPage queryOrderBatch(EarlyWarningOrderPage page) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	if (page.getPageNo() > 0) {
    		params.put("page", page.getPageNo());
    	}
    	if (page.getPageSize() > 0) {
    		params.put("limit", page.getPageSize());
    	}
    	params.put("isSign", 1); // 1已签约 0未签约
    	if ("".equals(page.getOrderType())) {
    		params.put("orderType", "1,2,3,4,6,7,9");
    	} else {
    		params.put("orderType", page.getOrderType());
    	}
    	//params.put("productTypeId", page.getProductType());
    	params.put("productId", page.getRouteId());
    	params.put("beginTimeStart", page.getStartTimeBegin());
    	params.put("beginTimeEnd", page.getStartTimeEnd());
    	params.put("returnTimeStart", page.getBackTimeStart());
    	params.put("returnTimeEnd", page.getBackTimeEnd());
    	params.put("desType", page.getDestCategoryId());
    	params.put("startCityName", page.getStartCity());
    	params.put("desCityName", page.getBackCity());
    	params.put("flightNo", page.getFlightNo());
    	params.put("departureTimeStart", page.getFlightDtBegin());
    	params.put("departureTimeEnd", page.getFlightDtEnd());
    	params.put("arriveTimeStart", page.getFlightLtBegin());
    	params.put("arriveTimeEnd", page.getFlightLtEnd());
    	params.put("departureCityName", page.getFlightDcitys());
    	params.put("arriveCityName", page.getFlightLcitys());
    	params.put("agencyId", page.getAgencyId());
    	params.put("agencyName", page.getAgencyName());
    	params.put("groupDateNum", page.getGroupTermNum());
    	params.put("selfGroupNum", page.getSelfGroupNum());
    	TRestClient trestClient = new TRestClient(pgaBatchQueryURL);
        trestClient.setMethod("GET");
        String reqData = JSONObject.fromObject(params).toString();
        trestClient.setData(reqData);
        logger.info("queryOrderBatch Bgn：" + reqData);
        try {
            String execute = trestClient.execute();
            JSONObject result = JSONObject.fromObject(execute);
            logger.info("queryOrderBatch Ing：" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
			boolean isSuccess = jsonObject.getBoolean("success");
			if (isSuccess) {
				JSONObject dataObj = jsonObject.getJSONObject("data");
				if (!dataObj.isEmpty()) {
					page.setTotalRecords(JsonUtil.getIntValue(dataObj, "totalCount"));
					page.setTotalPages(JsonUtil.getIntValue(dataObj, "pageCount"));
					JSONArray orderListJ = dataObj.getJSONArray("orderList");
					List<EarlyWarningOrderEntity> orderList = new ArrayList<EarlyWarningOrderEntity>();
					for (int i=0; i<orderListJ.size(); i++) {
						JSONObject orderJ = orderListJ.getJSONObject(i);
						EarlyWarningOrderEntity order = new EarlyWarningOrderEntity();
						order.setOrderId(JsonUtil.getIntValue(orderJ, "orderId"));
						order.setOrderType(JsonUtil.getIntValue(orderJ, "orderType"));
						order.setRouteId(JsonUtil.getIntValue(orderJ, "productId"));
						order.setRouteName(JsonUtil.getStringValue(orderJ, "productName"));
						order.setProductType(JsonUtil.getStringValue(orderJ, "productType"));
						order.setStartDate(JsonUtil.getSqlDateValue(orderJ, "beginTime", "yyyy-MM-dd HH:mm:ss"));
						order.setBackDate(JsonUtil.getSqlDateValue(orderJ, "backTime", "yyyy-MM-dd HH:mm:ss"));
						order.setDestCategoryId(JsonUtil.getIntValue(orderJ, "desType"));
						order.setDestCategoryName(JsonUtil.getStringValue(orderJ, "desTypeName"));
						order.setStartCity(JsonUtil.getStringValue(orderJ, "startCityName"));
						order.setBackCity(JsonUtil.getStringValue(orderJ, "desCityName"));
						order.setGroupTermNum(JsonUtil.getStringValue(orderJ, "groupDateNum"));
						order.setChildCnt(JsonUtil.getIntValue(orderJ, "childCnt"));
						order.setAdultCnt(JsonUtil.getIntValue(orderJ, "adultCnt"));
						order.setSelfGroupNum(JsonUtil.getStringValue(orderJ, "selfGroupNum"));
						
						JSONArray flightListJ = orderJ.getJSONArray("flightList");
						List<EwOrderFlightEntity> flightList = new ArrayList<EwOrderFlightEntity>();
						for (int j=0; j<flightListJ.size(); j++) {
							JSONObject flightJ = flightListJ.getJSONObject(j);
							EwOrderFlightEntity flight = new EwOrderFlightEntity();
							flight.setFlightNo(JsonUtil.getStringValue(flightJ, "flightNo"));
							flight.setDepartureTime(JsonUtil.getDateValue(flightJ, "departureTime", "yyyy-MM-dd HH:mm"));
							flight.setArriveTime(JsonUtil.getDateValue(flightJ, "arriveTime", "yyyy-MM-dd HH:mm"));
							flight.setDepartureCityName(JsonUtil.getStringValue(flightJ, "departureCityName"));
							flight.setArriveCityName(JsonUtil.getStringValue(flightJ, "arriveCityName"));
							flightList.add(flight);
						}
						order.setFlightList(flightList);
						
						JSONArray agencyListJ = orderJ.getJSONArray("agencyList");
						List<EwOrderAgencyEntity> agencyList = new ArrayList<EwOrderAgencyEntity>();
						for (int k=0; k<agencyListJ.size(); k++) {
							JSONObject agencyJ = agencyListJ.getJSONObject(k);
							EwOrderAgencyEntity agency = new EwOrderAgencyEntity();
							agency.setAgencyId(JsonUtil.getIntValue(agencyJ, "agencyId"));
							agency.setAgencyName(JsonUtil.getStringValue(agencyJ, "agencyName"));
							agencyList.add(agency);
						}
						order.setAgencyList(agencyList);
						
						int contactId = 0;
						String contactName = "";
						String contactPhone = "";
						JSONArray contactListJ = orderJ.getJSONArray("contactList");
						if (null != contactListJ && contactListJ.size() > 0) {
							JSONArray destListJ = getContactList(contactListJ, 2);
							if (destListJ.size() == 0) {
								destListJ = getContactList(contactListJ, 1);
							}
							if (destListJ.size() > 0) {
								JSONObject contact = destListJ.getJSONObject(0);
								contactId = JsonUtil.getIntValue(contact, "personId");
								contactName = JsonUtil.getStringValue(contact, "personName");
								contactPhone = JsonUtil.getStringValue(contact, "phone");
							}
						}
						order.setContactId(contactId);
						order.setContactName(contactName);
						order.setContactPhone(contactPhone);
						orderList.add(order);
					}
					page.setEwoList(orderList);
				}
			}
        } catch (TRestException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("queryOrderBatch End：" + 0);
    	
    	return page;
    }
    
    private static JSONArray getContactList(JSONArray arr, int personType) {
    	JSONArray arrSub = new JSONArray();
    	for (int i=0; i<arr.size(); i++) {
    		JSONObject obj = arr.getJSONObject(i);
    		if (personType == obj.getInt("personType")) {
    			if (StringUtil.isNotEmpty(obj.getString("phone"))) {
    				arrSub.add(obj);
    			}
    		}
    	}
    	return arrSub;
    }
    
    /**
     * 根据deptId列表获取到部门详情
     * @param deptId
     * @return
     * @throws TRestException 
     */
    public static JSONObject queryDepts(List<Integer> deptIds) throws TRestException{
        logger.info("queryDepts Bgn: deptIds is " + deptIds);
        TRestClient trestClient = new TRestClient(reportsURL);
        trestClient.setMethod("GET");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subSystem", "crm");
        map.put("key", "254f1f45891bc9ee");
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("service", "query_depts");
        
        Map<String, Object> needMap = new HashMap<String, Object>();
        needMap.put("lead", true);
        
        dataMap.put("need", needMap);
        
        List<Map<String, Object>> condList = new ArrayList<Map<String,Object>>();
        
        for(Integer deptId : deptIds){
            Map<String, Object> condMap = new HashMap<String, Object>();
            condMap.put("type", 1);
            condMap.put("value", deptId);
            condList.add(condMap);
        }
        
        dataMap.put("cond", condList);
        
        map.put("data", dataMap);
        
        String requestMsg = JSONObject.fromObject(map).toString();
        logger.info("getReporter Ing: requestMsg is " + requestMsg);
        trestClient.setData(requestMsg);
        String result = trestClient.execute();
        logger.info("queryDepts Ing: ResponseMsg is " + result);
        JSONObject jsonObj = JSONObject.fromObject(result);
        return jsonObj;
        
    }
    
    /**
     * 查询汇报人
     */
	public static UserEntity getReporter(String managerName) {
		logger.info("getReporter Bgn: managerName is " + managerName);
    	UserEntity user = new UserEntity();
    	if (StringUtil.isNotEmpty(managerName)) {
    		try {
    			//周瑜、汤怡2汇报人为 秦伟    业务联系人：周莉9
    			if("周瑜".equals(managerName)||"汤怡2".equals(managerName)){
    				user.setId(2089);
					user.setRealName("秦伟");
					user.setUserName("qinwei");
					user.setEmail("qinwei@tuniu.com");
					logger.info("getReporter End: reporterName is " + user.getRealName());
					return user;
    			}
    			//于江勇上级不收到投诉邮件     业务联系人 ：张伟10、邰瑾
    			if("于勇江".equals(managerName)){
					return user;
    			}
    			TRestClient trestClient = new TRestClient(reportsURL);
    			trestClient.setMethod("GET");
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("subSystem", "crm");
    			map.put("key", "254f1f45891bc9ee");
    			
    			Map<String, Object> dataMap = new HashMap<String, Object>();
    			dataMap.put("service", "query_sales_report");
    			
    			List<Map<String, Object>> condList = new ArrayList<Map<String,Object>>();
    			Map<String, Object> condMap = new HashMap<String, Object>();
    			condMap.put("type", 2);
    			condMap.put("value", managerName);
    			condList.add(condMap);
    			dataMap.put("cond", condList);
    			
    			map.put("data", dataMap);
    			
    			String requestMsg = JSONObject.fromObject(map).toString();
    			logger.info("getReporter Ing: requestMsg is " + requestMsg);
    			trestClient.setData(requestMsg);
    			String result = trestClient.execute();
    			logger.info("getReporter Ing: ResponseMsg is " + result);
    			JSONObject jsonObj = JSONObject.fromObject(result);
    			if ((Boolean) jsonObj.get("success")) {
    				Object obj = jsonObj.get("data");
    				if (obj instanceof JSONObject) {
    					JSONObject dataObj = jsonObj.getJSONObject("data");
    					JSONObject leadsObj = dataObj.getJSONObject("leads");
    					JSONObject repObj = leadsObj.getJSONObject(managerName);
    					if (null != repObj && !"null".equals(repObj.toString())) {
    						int userId = JsonUtil.getIntValue(repObj, "salerId");
    						if (userId != 19 && userId != 16) { // 过滤老于、锋锋
    							user.setId(userId);
        						user.setRealName(JsonUtil.getStringValue(repObj, "name"));
        						user.setUserName(JsonUtil.getStringValue(repObj, "spelling"));
        						user.setEmail(JsonUtil.getStringValue(repObj, "spelling")+"@tuniu.com");
    						}
    					}
    				}
    			}
    		} catch (Exception e) {
    			logger.error(e.getMessage(), e);
    		}
    	}
		logger.info("getReporter End: reporterName is " + user.getRealName());
		return user;
    }
	
	/**
	 * 根据custId获取会员等级信息
	 */
	public static Integer getCustLevelNum(Integer orderId) {
		logger.info("getCustLevel Bgn: orderId is " + orderId);
		Integer custLevelNum = -1;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId.longValue());
			JSONObject json = new JSONObject();
			json.put("func", "queryCustLevel");
			json.put("params", params);
			String requestMsg = json.toString();
			logger.info("getCustLevel Ing: requestMsg is " + requestMsg);
			TRestClient client = new TRestClient(crmCustLevelURL, TRestMethod.GET, requestMsg);
			String responseMsg = client.execute();
			logger.info("getCustLevel Ing: responseMsg is " + responseMsg);
			JSONObject jsonObject = JSONObject.fromObject(responseMsg);
			Boolean isSuccess = jsonObject.getBoolean("success");
			if(isSuccess) {
				if (null != jsonObject.get("data")) {
					JSONObject data = jsonObject.getJSONObject("data");
					custLevelNum = JsonUtil.getIntValue(data, "custLevel");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return custLevelNum;
		} finally {
			logger.info("getCustLevel End: custLevel is " + custLevelNum);
		}
		
		return custLevelNum;
	}
	
	/**
	 * 上传附件
	 */
	public static String uploadFile(File file, String fileName) {
		logger.info("uploadFile Bgn: fileName is " + fileName);
		String url = "";
		try {
			//将文件上传到远程服务器
			String result = RemoteFileUploadUtil.uploadFile(uploadURL, file, fileName);
			JSONObject fromObject = JSONObject.fromObject(result);
			logger.info("uploadFile Ing: result is " + fromObject.toString());
			if(fromObject.get("success") != null){
			    boolean success = fromObject.getBoolean("success");   
			    if(success){
			        Object object = fromObject.get("data");
			        JSONArray dataList = JSONArray.fromObject(object);
			        if(null != dataList && !dataList.isEmpty()){
			            Object object2 = dataList.get(0);
			            JSONObject fromObject2 = JSONObject.fromObject(object2);
			            url = fromObject2.get("url").toString();
			        }
			    }
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("uploadFile End: url is " + url);
		return url;
	}
	
	/**
	 * 获取供应商超时回复
	 * 
	 */
	public static void queryTimeOutCommit(String jsonStr) {
		
		logger.info("queryTimeOutCommit Begin: paramMap is " + jsonStr);
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(nbTimeOutURL, jsonStr);
			logger.info("queryTimeOutCommit Ing: returnMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
		
			if ((Boolean) jsonObject.get("success")) {
				
				JSONObject ja = jsonObject.getJSONObject("data");
				JSONArray temp = ja.getJSONArray("list");
				for(int i=0;i<temp.size();i++){
					String userName ="";
					String complaintId = temp.getJSONObject(i).getString("complaintID");
					String roomId = temp.getJSONObject(i).getString("roomID");
					String userId = temp.getJSONObject(i).getString("salerId");
					if(!"".equals(userId.trim())&&null!=userId){
						
					    userName = (String) MemcachesUtil.getObj(userId);
					    String title="供应商沟通提醒";
					    String content="【投诉质检-供应商沟通超时】"+"\n投诉单号:"+complaintId+"\n房间号:"+roomId+"\n";
					    new RTXSenderThread(Integer.parseInt(userId),userName, title, content).start();
						logger.info("queryTimeOutCommit sendMessage:  " + Integer.parseInt(userId)+"--"+userName+"--"+content);
					}
				
				}
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取所有的投诉单沟通类型
	 * 
	 */
	public static List<AgencyToChatEntity> queryCommitType(String jsonStr) {
		
		List<AgencyToChatEntity> list =new ArrayList<AgencyToChatEntity>();
		logger.info("queryCommitType Begin: paramMap is " + jsonStr);
		try {
		
			HttpClientHelper client =new HttpClientHelper();
			String ret =  client.executeGet(nbLastCommitURL, jsonStr);
			logger.info("queryCommitType Ing: returnMessage is " + ret);
			if(!"".equals(ret)){
				
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if ((Boolean) jsonObject.get("success")) {
					
					JSONObject ja = jsonObject.getJSONObject("data");
					JSONArray temp = ja.getJSONArray("list");
					for(int i=0;i<temp.size();i++){
						
						AgencyToChatEntity at =new AgencyToChatEntity();
						String complaintId = temp.getJSONObject(i).getString("complaintID");//投诉单ID
						String roomId = temp.getJSONObject(i).getString("roomID");//房间号
						String status = temp.getJSONObject(i).getString("status");//沟通状态
						String flag = temp.getJSONObject(i).getString("timeoutReply");//标识是否超时 0=未超时，1=已超时
						if(Constans.COMMIT_NO_ANSWER.equals(status)){
							
							at.setStatusName(Constans.COMMIT_NO_ANSWER_NAME);
							
						}else if(Constans.COMMIT_ING.equals(status)){
							
							at.setStatusName(Constans.COMMIT_ING_NAME);
							
						}else if(Constans.COMMIT_FINISH.equals(status)){
							
							at.setStatusName(Constans.COMMIT_FINISH_NAME);
						}
						if("1".equals(flag)){
							
							at.setTimeOut(Constans.TIME_OUT_NAME);
							
						}
						at.setComplaintId(complaintId);
						at.setRoomId(roomId);
						at.setStatusNum(status);
						at.setTimeOutflag(flag);
						list.add(at);
					 }
				   }
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return list;
		}
	
	}
	
	
	/**
	 * 追加沟通
	 * 
	 */
	public static int sendAddChatToNB(String jsonStr) {
		
		logger.info("sendAddChatToNB Begin: paramMap is " + jsonStr);
		int flag=0;
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(nbAddCommitURL, jsonStr);
			
			logger.info("sendAddChatToNB Ing: returnMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			if ((Boolean) jsonObject.get("success")) {
				
				flag=0;
			}else{
				
				flag=1;
				
			}
		} catch (Exception e) {
			
			flag=1;
			logger.error(e.getMessage(), e);
		}
		return flag;
	}
	
	
	
	/**
	 * 修改沟通类型 + 转为已完成
	 * @param jsonStr
	 * @return
	 */
	public static int sendUpdateNB(String jsonStr) {
		
		logger.info("sendUpdateNB Begin: paramMap is " + jsonStr);
		int flag=0;
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(nbUpdateURL, jsonStr);
			logger.info("sendUpdateNB Ing: returnMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			if ((Boolean) jsonObject.get("success")) {
				
				flag=0;
			}else{
				
				flag=1;	
			}
		} catch (Exception e) {
			
			flag=1;
			logger.error(e.getMessage(), e);
		}
		return flag;
	}
	
	
	/**
	 * 获取roomID
	 * 
	 */
	public static Map<String, Object> queryRoomId(String jsonStr) {
		
		Map<String, Object> map =new HashMap<String, Object>();
		logger.info("queryRoomId Begin: paramMap is " + jsonStr);
		String roomId="";
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(nbRoomIdURL, jsonStr);
			logger.info("queryRoomId Ing: returnMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			if ((Boolean) jsonObject.get("success")) {
				JSONObject ja = jsonObject.getJSONObject("data");
				roomId = ja.getString("roomID");
				map.put("roomId", roomId);
				
			}else{
				
				map.put("roomId", "");
				
			}
			map.put("msg", jsonObject.get("msg"));
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			map.put("roomId", "");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 发送文件到Nb
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> sendUploadNB(Map<String, Object> fileMap) {
		Map<String,Object> map =new HashMap<String, Object>();
		try {
			
			File file = (File) fileMap.get("pic");
			String fileName = (String) fileMap.get("fileName");
			String typeName = (String) fileMap.get("typeName");
			//调用NB上传接口
			String result = uploadFileToNb(nbUpLoadURL, file, fileName,typeName);
			if(!"".equals(result)){
				byte[] resultByte  =Base64.decodeBase64(result.getBytes("utf-8"));//解码
				JSONObject fromObject = JSONObject.fromObject(new String(resultByte));
				logger.info("uploadFile Ing: result is " + fromObject.toString());
				if(fromObject.get("success") != null){
				    boolean success = fromObject.getBoolean("success");   
				    if(success){
				    	JSONObject object = fromObject.getJSONObject("data");
				        map.put("largeUrl", object.getString("url"));//大图
				        map.put("smallUrl",  object.getString("thumbnail"));//小图
				        map.put("height", object.getString("height"));//高度
				        map.put("width", object.getString("width"));//宽度
				        map.put("imageName", object.getString("attachName"));//文件名字
				        }
				    }else{
				    	
				    	 map.put("largeUrl", "");
				    }
			}else{
				
				   map.put("largeUrl", "");
			}
		} catch (Exception e) {
			
		     map.put("largeUrl", "");
			 logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/**
	 * 上传图片到NB
	 * @param uploadURL
	 * @param file
	 * @param fileName
	 * @param fileType
	 * @return
	 */
    public static String uploadFileToNb(String uploadURL, File file, String fileName,String fileType )
    {
        String result = "";
        try
        {
            String fileNameEncode = Base64.encodeBase64URLSafeString(fileName.getBytes("utf8"));
            PostMethod filePost = new PostMethod(uploadURL);
            filePost.addRequestHeader("platform", "complaint");
            FilePart fp = new FilePart("file", file);
            fp.setContentType("Content-Type");
            Part[] parts = {new StringPart("fileType", fileType),new StringPart("fileName", fileNameEncode),fp};
            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, filePost.getParams());
            filePost.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);// 设置连接时间
            client.getHttpConnectionManager().getParams().setSoTimeout(10000);//设置返回时间
            int status = client.executeMethod(filePost);
            if (status != HttpStatus.SC_OK)
            {
                result = filePost.getResponseBodyAsString();
            }
            else if (status == HttpStatus.SC_OK)
            {
                result = filePost.getResponseBodyAsString();
               
            }

        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
	
	
    /**
	 * 获取roomID
	 * 
	 */
	public static int checkAddRoomDeal(String jsonStr) {
		
		logger.info("checkAddRoomDeal Begin: paramMap is " + jsonStr);
		int flag= 0;
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(nbAddRoomURL, jsonStr);
			logger.info("checkAddRoomDeal Ing: returnMessage is " + ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			if ((Boolean) jsonObject.get("success")) {
				
				flag =1 ;
				
			}else{
				
				flag = 0;
			}
		} catch (Exception e) {
			
			flag = 0;
			logger.error(e.getMessage(), e);
		 
		}
		return flag;
	}
	
	/**
	 * #统计信息查询接口-按产品统计
	 * 暂时只实现返回第一个产品id对应产品的点评总数功能
	 * @param productIds
	 * @return
	 */
	public static Integer getStatProductByProductIds(List<Integer> productIds) {
		logger.info("getStatProductByProductIds Begin: productIds is "
				+ productIds);
		
		Integer remarkAmount = 0;//点评次数
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		paramMap.put("productIds", productIds);
		System.out.println(JSONObject.fromObject(paramMap));
		TRestClient client = new TRestClient(getStatProductByProductIdsURL,
				TRestMethod.GET, JSONObject.fromObject(paramMap).toString());
		String ret;
		try {
			ret = client.execute();
			logger.info("getStatProductByProductIds Ing: returnMessage is "
					+ ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			
			if(jsonObject.getBoolean("success")){
				JSONObject jdata = jsonObject.getJSONObject("data");
				int totalItems = jdata.getInt("totalItems");
				if(totalItems==1){
					JSONArray statSpecs = jdata.getJSONArray("statSpecs");
					JSONObject statSpec = statSpecs.getJSONObject(0);
					System.out.println(statSpec);
					remarkAmount = statSpec.getInt("remarkAmount");
				}
				
			}
				
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		}
		
		return remarkAmount;
	}
	
	/**
	 * 点评详情接口
	 * 
	 * @param paramMap  包括productId,点评时间范围开始：remarkTimeStart,点评时间范围结束remarkTimeEnd
	 * @return
	 */
	public static List<RemarkEntity> getContentList(Map<String,Object> paramMap) {
	    paramMap.put("orderByRemarkTime", 1);
	    paramMap.put("hasRemark", 1);
	    paramMap.put("relation", 1);
	    String paramStr = JSONObject.fromObject(paramMap).toString();
		logger.info("getStatProductByProductIds Begin: param is "+ paramStr);
		TRestClient client = new TRestClient(getContentListURL,
				TRestMethod.GET, paramStr);
		String ret;
		List<RemarkEntity> remarkList = null;
		try {
			ret = client.execute();
			logger.info("getStatProductByProductIds Ing: returnMessage is "
					+ ret);
			JSONObject jsonObject = JSONObject.fromObject(ret);
			
			if(jsonObject.getBoolean("success")){
				JSONObject jdata = jsonObject.getJSONObject("data");
				remarkList = new ArrayList<RemarkEntity>();
				convertToRemarkEntity(jdata,remarkList);
			}
				
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		}
		
		return remarkList;
	}
	
	/**
	 * 获取指定时间范围内点评数
	 * 
	 * @param paramMap 
	 * productId 产品id
	 * remarkTimeStart：点评时间范围开始
	 * remarkTimeEnd：点评时间范围结束
	 * @return
	 */
    public static Integer getRemarkAmount(Map<String, Object> paramMap) {
        Integer productId = (Integer) paramMap.get("productId");
        paramMap.put("hasRemark", 1);
        paramMap.put("relation", 1);
        String paramStr = JSONObject.fromObject(paramMap).toString();
        logger.info("getContentAmount Begin["+productId+"]: param is " + paramStr);
        int totalItems = 0;
        TRestClient client = new TRestClient(getContentListURL, TRestMethod.GET, paramStr);
        String ret;
        try {
            ret = client.execute();
            logger.info("getContentAmount Ing["+productId+"]: returnMessage is " + ret);
            JSONObject jsonObject = JSONObject.fromObject(ret);

            if(jsonObject.getBoolean("success")) {
                JSONObject jdata = jsonObject.getJSONObject("data");
                totalItems = jdata.getInt("totalItems");
                if(totalItems == 0) { // 规避有评论但是totalItems显示为0的方案
                    JSONArray contents = jdata.getJSONArray("contents");
                    totalItems = contents.size();
                }
            }

            logger.info("getContentAmount End["+productId+"]:点评数为" + totalItems + "条");

        } catch(TRestException e) {
            logger.error(e.getMessage(), e);
            logger.error("获取点评数出错", e);
        }
        return totalItems;
    }
    
    
	private static void convertToRemarkEntity(JSONObject jdata,
			List<RemarkEntity> remarkList) {
		JSONArray contents = jdata.getJSONArray("contents");
		for (int i = 0; i < contents.size(); i++) {
			JSONObject content = contents.getJSONObject(i);
			RemarkEntity remark = new RemarkEntity();
			remark.setRouteId(content.getInt("productId"));
			remark.setOrderId(content.getInt("orderId"));
			remark.setSatisfaction(content.getInt("satisfaction"));
			remark.setRemarkTime(content.getString("remarkTime"));
			remark.setStartTime(content.getString("startTime"));
			remark.setCompTextContent(content.getJSONObject("compTextContent").getString("dataSvalue"));
			StringBuilder sb = new StringBuilder();
			JSONArray subTextContents = content.getJSONArray("subTextContents");
			String notes;
			String dataSvalue;
			for (int j = 0; j < subTextContents.size(); j++) {
				JSONObject subTextContent = subTextContents.getJSONObject(j);
				notes = subTextContent.getString("notes");
				dataSvalue = subTextContent.getString("dataSvalue");
				sb.append(notes).append(":").append(dataSvalue).append("\r");
			}
			remark.setSubTextContent(sb.toString());
			remarkList.add(remark);
		}
		Collections.reverse(remarkList);//接口获取的数据按时间倒叙排列，页面展示需要按照正向排列
	}

	
	
	/**
	 *通过订单号查询出游人信息
	 * 
	 */
	public static List<String> queryTourCustomer(String jsonStr) {
		
		logger.info("queryTourCustomer Begin: paramMap is " + jsonStr);
		List<String> list = new ArrayList<String>();
		try {
			
			 TRestClient client = new TRestClient(crmTourURL, TRestMethod.POST, jsonStr);
		     String ret = client.execute();
			logger.info("queryTourCustomer Ing: returnMessage is " + ret);
			if(!"".equals(ret)){
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if ((Boolean) jsonObject.get("success")) {
					JSONArray temp = jsonObject.getJSONArray("data");
					for(int i=0;i<temp.size();i++){
						
						String customerName = temp.getJSONObject(i).getString("name");//出游人姓名
						list.add(customerName);
					}
					
				}
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 发送质检点数据到QMS
	 * 
	 */
	public static int sendQcPointToQMS(String jsonStr) {
		
		logger.info("sendQcPointToQMS Begin: paramMap is " + jsonStr);
		int flag=0;
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(qmsURL + "/access/qcBill/addQcPoint", jsonStr);
			logger.info("sendQcPointToQMS Ing: returnMessage is " + ret);
			if("".equals(ret)){
				
				flag=1;
				
			}else{
				
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if ((Boolean) jsonObject.get("success")) {
					
					flag=0;
					
				}else{
					
					flag=1;
				}
			}
		} catch (Exception e) {
			
			flag=1;
			logger.error(e.getMessage(), e);
		}
		return flag;
	}
	
	
	
	/**
	 * 发送点评数据到QMS
	 * 
	 */
	public static int sendReviewToQMS(String jsonStr) {
		
		logger.info("sendReviewToQMS Begin: paramMap is " + jsonStr);
		int flag=0;
		try {
			
			HttpClientHelper client = new HttpClientHelper();
			String ret =  client.executeGet(qmsURL + "/access/qcBill/addInnerQc", jsonStr);
			logger.info("sendReviewToQMS Ing: returnMessage is " + ret);
			if("".equals(ret)){
				
				flag=1;
				
			}else{
				
				JSONObject jsonObject = JSONObject.fromObject(ret);
				if ((Boolean) jsonObject.get("success")) {
					
					flag=0;
					
				}else{
					
					flag=1;
				}
			}
		} catch (Exception e) {
			
			flag=1;
			logger.error(e.getMessage(), e);
		}
		return flag;
	}
	
    /**
     * 新短信服务接口
     * @param mpe
     */
    public static boolean sendMessageNew(MessageParamEntity mpe) {
        boolean success = false;
        String param = JSONObject.fromObject(mpe).toString();
        logger.info("sendMessageNew Begin: param is " + param);
        
        TRestClient trestClient = new TRestClient(SEND_MSG_NEW);
        trestClient.setMethod("POST");
        trestClient.setData(JSONObject.fromObject(mpe).toString());
        try {
            String execute = trestClient.execute();
            logger.info("sendMessageNew Ing: ResponseMessage is " + execute);
            JSONObject jObject = JSONObject.fromObject(execute);
            boolean isSucc = jObject.getBoolean("success");
            if(isSucc) {
                success = true;
            }else{
                success = false;
            }
        } catch (Exception e) {
            success = false;
            logger.error(e.getMessage(), e);
        }
        logger.info("sendMessageNew End: param is " + param);
        
        return success;
    }
    
    public static  List<AgentStatusLog>  queryComplaintCallRecords(Map<String, Object> paramMap){
        List<AgentStatusLog> agentStatusLog = new ArrayList<AgentStatusLog>();
        String param = JSONObject.fromObject(paramMap).toString();
        logger.info("queryComplaintCallRecords Begin: param is " + param);
        
        TRestClient trestClient = new TRestClient(CRM_IVR_RECORDS);
        trestClient.setMethod("POST");
        trestClient.setData(JSONObject.fromObject(paramMap).toString());
        try {
            String execute = trestClient.execute();
            logger.info("queryComplaintCallRecords Ing: ResponseMessage is " + execute);
            JSONObject result = JSONObject.fromObject(execute);
            boolean isSucc = result.getBoolean("success");
            if(isSucc) {
                int count = JsonUtil.getIntValue(result, "num");
                if(count>0){
                    JSONArray data = result.getJSONArray("data");
                    agentStatusLog=AgentStatusLog.fromObject(data);
                }
            }else{
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("queryComplaintCallRecords End: param is " + param);
        
        return agentStatusLog;
    }
    
    public static JSONArray queryDistrictByParentId(int parentId){ //中国  40002
        JSONArray districArray = new JSONArray();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("parent_id", parentId);
        String param = JSONObject.fromObject(paramMap).toString();
        logger.info("queryDistrictByParentId Begin: param is " + param);
        
        TRestClient trestClient = new TRestClient(POI_SEARCH);
        trestClient.setMethod("GET");
        trestClient.setData(param);
        try {
            String execute = trestClient.execute();
            logger.info("queryDistrictByParentId Ing: ResponseMessage is " + execute);
            JSONObject responseJSON = JSONObject.fromObject(execute);
            boolean isSucc = responseJSON.getBoolean("success");
            if(isSucc) {
                JSONObject  dataObject = responseJSON.getJSONObject("data");
                JSONArray resultArray = dataObject.getJSONArray("result");
                if(CollectionUtil.isNotEmpty(resultArray)){
                    for(int i=0;i<resultArray.size();i++){
                        JSONObject resultItem = (JSONObject)resultArray.get(i);
                        if(parentId==40002){
                            districArray.add(resultItem.get("name"));
                        }else if(JsonUtil.getIntValue(resultItem, "type_id")==10){
                            districArray.add(resultItem.get("name"));
                        }
                    }
                }
            }else{
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("queryDistrictByParentId End: param is " + param);
        
        return districArray;
    }
    
    
    //根据name查id
    public static Integer queryDistrictIdByName(String name){
        Integer poiId = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        String param = JSONObject.fromObject(paramMap).toString();
        logger.info("queryDistrictIdByName Begin: param is " + param);
        
        TRestClient trestClient = new TRestClient(POI_SEARCH);
        trestClient.setMethod("GET");
        trestClient.setData(param);
        try {
            String execute = trestClient.execute();
            logger.info("queryDistrictIdByName Ing: ResponseMessage is " + execute);
            JSONObject responseJSON = JSONObject.fromObject(execute);
            boolean isSucc = responseJSON.getBoolean("success");
            if(isSucc) {
                JSONObject  dataObject = responseJSON.getJSONObject("data");
                JSONArray resultArray = dataObject.getJSONArray("result");
                if(CollectionUtil.isNotEmpty(resultArray)){
                    JSONObject jsonObj = resultArray.getJSONObject(0);
                    poiId = JsonUtil.getIntValue(jsonObj, "id");
                }
            }else{
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("queryDistrictByParentId End: param is " + param);
        
        return poiId;
    }
    
    /**
     * 发起改进报告
     * @param cmpImproveVo
     * @return
     */
    public static int addCmpImproveBill(CmpImproveVo cmpImproveVo){
    	logger.info("addCmpImproveBill Begin: cmpId is " + cmpImproveVo.getCmpId());
    	logger.info("addCmpImproveBill Begin: " + JSONObject.fromObject(cmpImproveVo).toString());
		TRestClient trestClient = new TRestClient(qmsURL + "/access/cmpImprve/addImproveBill");
		trestClient.setMethod("POST");
		trestClient.setData(JSONObject.fromObject(cmpImproveVo).toString());
		
		int impBillId = 0;
		try {
			String execute = trestClient.execute();
			logger.info("addCmpImproveBill Ing: ResponseMessage is " + execute);
			JSONObject jObject = JSONObject.fromObject(execute);
			boolean isSucc = jObject.getBoolean("success");
			if (isSucc) {
				impBillId = jObject.getInt("data");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("addCmpImproveBill End: cmpId is " + cmpImproveVo.getCmpId() + ", impBillId is " + impBillId);
    	return impBillId;
    }
    
    /**
     * 根据会员id获取会员维护人
     * @param getCustSaler
     * @return
     */
    public static Integer getCustSaler(Integer custId){
    	Integer custUserId = 0;
		TRestClient trestClient = new TRestClient(CUST_SALER_URL);
		trestClient.setMethod("GET");
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custId", custId);
        String paramStr = JSONObject.fromObject(paramMap).toString();
		trestClient.setData(paramStr);
		logger.info("getCustSaler custId:"+custId+" Begin: param is " + paramStr);
		try {
			String execute = trestClient.execute();
			logger.info("getCustSaler custId:"+custId+" Ing: ResponseMessage is " + execute);
			JSONObject jsonObject = JSONObject.fromObject(execute);
			if (jsonObject.getBoolean("success")) {
				JSONArray dataArray = jsonObject.getJSONArray("data");
				JSONObject dataInfo = dataArray.getJSONObject(0);
				if (null != dataInfo && !dataInfo.isEmpty()) {
					int handOverId = dataInfo.getInt("handoverSalerId");
					if(handOverId!=0){
						custUserId = handOverId == 0?0:handOverId;
					}else{
						int salerId = dataInfo.getInt("salerId");
						custUserId = salerId == 0?0:salerId;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("getCustSaler End: custId is " + custId);
    	return 9998;
    }
    
    public static Map<String,Object> getCustSalerAndHandSaler(Integer custId){
        Map<String,Object> salerMap = new HashMap<String, Object>();
        TRestClient trestClient = new TRestClient(CUST_SALER_URL);
        trestClient.setMethod("GET");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("custId", custId);
        String paramStr = JSONObject.fromObject(paramMap).toString();
        trestClient.setData(paramStr);
        logger.info("getCustSalerAndHandSaler custId:"+custId+" Begin: param is " + paramStr);
        try {
            String execute = trestClient.execute();
            logger.info("getCustSalerAndHandSaler custId:"+custId+" Ing: ResponseMessage is " + execute);
            JSONObject jsonObject = JSONObject.fromObject(execute);
            if (jsonObject.getBoolean("success")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject dataInfo = dataArray.getJSONObject(0);
                if (null != dataInfo && !dataInfo.isEmpty()) {
                    int handOverId = dataInfo.getInt("handoverSalerId");
                    int salerId = dataInfo.getInt("salerId");
                    salerMap.put("handsalerId", handOverId);
                    salerMap.put("salerId", salerId);
                    salerMap.put("handsalerName", dataInfo.get("handoverSalerName") != null?dataInfo.getString("handoverSalerName"):"");
                    salerMap.put("salerName", dataInfo.get("salerName") != null?dataInfo.getString("salerName"):"");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("getCustSalerAndHandSaler End: custId is " + custId);
        return salerMap;
    }
	
	public static void main(String[] args) {
//		Map<String,Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("productId", 32384677);
//		paramMap.put("remarkTimeStart", "2015-10-04 00:00:00");
//		paramMap.put("remarkTimeEnd", "2015-10-11 00:00:00");
//		List<RemarkEntity> list = getContentList(paramMap);
//		System.out.println(list);
//	    System.out.println(addTourist("潘俊", "15940053176"));
	    
	   /* MessageParamEntity entity = new MessageParamEntity();
        entity.setTemplateId(642);
        entity.setMobileNum(Arrays.asList("18551847579".split(",")));

        MessageTemplateParamValuePair mtpvp2 = new MessageTemplateParamValuePair("prdName", "韩国首尔5日游");
        MessageTemplateParamValuePair mtpvp1 = new MessageTemplateParamValuePair("dealName", "FLP");
        List<MessageTemplateParamValuePair> smsTemplateParams = new ArrayList<MessageTemplateParamValuePair>();
        smsTemplateParams.add(mtpvp1);
        smsTemplateParams.add(mtpvp2);
        entity.setSmsTemplateParams(smsTemplateParams);
        sendMessageNew(entity);*/
	    
//	    System.out.println(querySameGroupOrders("2016-04-19","aaa"));
//	    System.out.println(getCustLevelNum(7426224));
	    System.out.println(queryDistrictByParentId(40002));
//	    System.out.println(queryDistrictByParentId(queryDistrictIdByName("安徽")));
	}
}
