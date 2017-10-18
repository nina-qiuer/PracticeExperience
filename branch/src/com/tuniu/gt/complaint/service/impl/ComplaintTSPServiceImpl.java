package com.tuniu.gt.complaint.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.SysConstans;
import com.tuniu.gt.complaint.entity.AgencyEntity;
import com.tuniu.gt.complaint.entity.AgengcyPayoutEntity;
import com.tuniu.gt.complaint.entity.CardUniqueEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IFinanceSubbankService;
import com.tuniu.gt.complaint.util.StaxonUtils;
import com.tuniu.gt.complaint.vo.SystemVo;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.FreemarkerUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.toolkit.TripleDES;
import com.tuniu.operation.platform.base.json.JsonFormatter;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.operation.platform.tsg.base.rest.RestException;
import com.tuniu.operation.platform.tsg.base.rest.RestMethod;
import com.tuniu.operation.platform.tsg.client.caller.rest.RequestParam;
import com.tuniu.operation.platform.tsg.client.caller.rest.ResponseResult;
import com.tuniu.operation.platform.tsg.client.exception.NoServiceAvailableException;
import com.tuniu.operation.platform.tsg.client.exception.ServiceForbidenException;
import com.tuniu.operation.platform.tsg.client.proxy.tsg.TSPCommonClient;
import com.tuniu.trest.TRestClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tspService")
public class ComplaintTSPServiceImpl implements IComplaintTSPService {
	
	private static Logger logger = Logger.getLogger(ComplaintTSPServiceImpl.class);
	
	@Resource
	private TSPCommonClient tspCommonClient;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	@Override
	public Map<String, Object> getProductInfo(Long productId) {
		logger.info("getBOHInfo Bgn: productId is " + productId);
		Map<String, Object> productInfo = new HashMap<String, Object>();
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("BOH.NM.ProductController.queryCommonProductInfos");

			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> productIds = new ArrayList<Map<String, Object>>();
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("productId", productId);
			productIds.add(pmap);
			map.put("productQueryVo", productIds); // 产品ids
			map.put("requestSourceCode", 0); // 请求来源1=电话 2=网站3=无线，投诉系统传0
			map.put("marketChannelCode", 100); // (Integer)渠道编号，可固定100
			String requestMsg = JSONObject.fromObject(map).toString();
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			tspCommonClient.setSocketTimeout(10);
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("getBOHInfo return is " + retStr);
			JSONObject jsonObj = JSONObject.fromObject(retStr);
			
			
//			  TRestClient trestClient = new TRestClient("http://10.40.50.62:12501/boh/product/common/info");
//			  trestClient.setMethod("GET"); trestClient.setData(requestMsg);
//			  String execute = trestClient.execute(); JSONObject jsonObj =
//			  JSONObject.fromObject(execute);
			 
			
			if ((Boolean) jsonObj.get("success")) {
				Object data = jsonObj.get("data");
				JSONArray fmtData = JSONArray.fromObject(data);
				if (null != fmtData && !fmtData.isEmpty()) {
					Object productObj = fmtData.get(0);
					JSONObject fmtProduct = JSONObject.fromObject(productObj);
					if (null != fmtProduct && !fmtProduct.isEmpty()) {
						int productID = JsonUtil.getIntValue(fmtProduct, "productId");//产品ID
						int productType = JsonUtil.getIntValue(fmtProduct, "productType"); //产品类型 1跟团游，2自助游，3酒店，4机票，5团队游，6门票，8自驾游
						String productName = JsonUtil.getStringValue(fmtProduct, "productName");//产品名称
						int managerId = JsonUtil.getIntValue(fmtProduct, "managerId");//产品经理id
						String managerName = JsonUtil.getStringValue(fmtProduct, "managerName");//产品经理名称
						int ownerId = JsonUtil.getIntValue(fmtProduct, "ownerId");//产品专员id
						String ownerName = JsonUtil.getStringValue(fmtProduct, "ownerName");//产品专员名称
						int productLineTypeId = JsonUtil.getIntValue(fmtProduct, "productNewLineTypeId"); //产品线类型 12周边14国内长线16出境短线18出境长线（跟团、自驾、自助
						int productLineDestId = JsonUtil.getIntValue(fmtProduct, "productNewLineDestId");//产品线目的地id
						String productLineDestName = JsonUtil.getStringValue(fmtProduct, "productNewLineDestName");//产品线目的地
						String brandName = JsonUtil.getStringValue(fmtProduct, "brandName");//品牌名称
						int  brandId = JsonUtil.getIntValue(fmtProduct, "brandId");//品牌id
						
						String classBrandParentName = JsonUtil.getStringValue(fmtProduct, "classBrandParentName");//一级品类名称
						int  classBrandParentId = JsonUtil.getIntValue(fmtProduct, "classBrandParentId");//一级品类ID
						if("".equals(classBrandParentName)){
							
							classBrandParentName = CommonUtil.getProductTypeName(classBrandParentId);
							if("".equals(classBrandParentName)){
								
								classBrandParentName = CommonUtil.getProductTypeById(productType);
							}
						}
						String subProductTypeName = JsonUtil.getStringValue(fmtProduct, "classBrandName");//子品类名称
						int  subProductType = JsonUtil.getIntValue(fmtProduct, "classBrandId");//子品类id
						Integer  destGroupId = JsonUtil.getIntValue(fmtProduct, "destGroupId");//目的地分组id
						
						productInfo.put("productId", productID);
						productInfo.put("productName",  productName);
						productInfo.put("managerId", managerId);
						productInfo.put("managerName", managerName);
						productInfo.put("ownerId", ownerId);
						productInfo.put("ownerName", ownerName);
						productInfo.put("brandName", brandName);
						productInfo.put("brandId",  brandId);
						productInfo.put("classBrandParentId", classBrandParentId);
						productInfo.put("classBrandParentName",  classBrandParentName);
						productInfo.put("subProductType", subProductType);
						productInfo.put("subProductTypeName",  subProductTypeName);
						productInfo.put("productLineTypeId", productLineTypeId);
						productInfo.put("productLineDestId", productLineDestId);
						productInfo.put("productLineDestName", productLineDestName);
						productInfo.put("destGroupId", destGroupId);
						JSONObject departmentObject = fmtProduct.getJSONObject("departmentInfo");
						Map<String, Object> depMap =new HashMap<String, Object>();
						if(!departmentObject.isNullObject()){
							depMap.put("productLineId",JsonUtil.getIntValue(departmentObject, "productLineId"));
							depMap.put("businessUnitId", JsonUtil.getIntValue(departmentObject, "businessUnitId"));
							depMap.put("businessUnitName",  JsonUtil.getStringValue(departmentObject, "businessUnitName"));
							depMap.put("departmentId", JsonUtil.getIntValue(departmentObject, "departmentId"));
							depMap.put("departmentName",  JsonUtil.getStringValue(departmentObject, "departmentName"));
							depMap.put("groupId", JsonUtil.getIntValue(departmentObject, "groupId"));
							depMap.put("groupName",  JsonUtil.getStringValue(departmentObject, "groupName"));
							productInfo.put("departmentInfo", depMap);
						}else{
							depMap.put("productLineId",0);
							depMap.put("businessUnitId", 0);
							depMap.put("businessUnitName","");
							depMap.put("departmentId", 0);
							depMap.put("departmentName", "");
							depMap.put("groupId", 0);
							depMap.put("groupName",  "");
							productInfo.put("departmentInfo", depMap);
						}
					
						productInfo.put("errCode",0);
					}
				}else{
					
					productInfo.put("errCode", 1);
					String msg = JsonUtil.getStringValue(jsonObj, "msg");
					if("".equals(msg)){
						
						msg ="获取BOH接口失败";
					}
					productInfo.put("errorMesg", msg);
					
				}
			}else{
				
				productInfo.put("errCode", 1);
				productInfo.put("errorMesg", JsonUtil.getStringValue(jsonObj, "msg"));
			}
		} catch (Exception e) {
			
			productInfo.put("errCode", 1);
			productInfo.put("errorMesg", e.getMessage());
			logger.error("getBOHInfo error is " + e.getMessage(),e);
		}
		return productInfo;
	}
	
	
	/**
	 * 发送邮件
	 */
	public boolean sendMail(MailerThread mailerThread) {
		
		boolean sendResult = false;
		logger.info("sendMail Bgn:  " + mailerThread.getTitle());
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("PLA.EMAIL.EdmController.sendInternalEmail");
			Map<String, Object> map = getSemdMailName(mailerThread);
			String jsonStr = JSONObject.fromObject(map).toString();
			logger.info("sendMail detail is " + jsonStr);
		    requestParam.setData(jsonStr);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("sendMail return is " + retStr);
			JSONObject jsonObj = JSONObject.fromObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				sendResult =true;
			}
		} catch (Exception e) {
			logger.error("发送邮件报错",e);
		}
		return sendResult;
	}

	
	private Map<String, Object> getSemdMailName(MailerThread mail){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemCode", Constans.SYSTEM_CODE);//系统三字码
		map.put("businessId", Constans.BUSINESS_ID);//业务ID
		map.put("senderName", "robot@tuniu.com");//发件人
		map.put("subject", mail.getTitle()==null?"":mail.getTitle());//邮件主题
		List<String> list = new ArrayList<String>();
		List<String> cclist = new ArrayList<String>();
		if (mail.isTestEnv()) { // 非生产环境，将邮件发送给测试人员
			
			list.add("fangyouming@tuniu.com");
			list.add("zhangsensen@tuniu.com");
			cclist.add("liubing@tuniu.com");
			StringBuilder sb = new StringBuilder();
			sb.append("收件人：").append(Arrays.toString(mail.getRecipientsArr())).append("<br>");
			sb.append("抄送人：").append(Arrays.toString(mail.getCcArr())).append("<br>");
			sb.append(mail.getContent());
			mail.setContent(sb.toString());
			map.put("recipientEmails", list);
			map.put("ccEmails", cclist);
			map.put("msgText", mail.getContent());//邮件内容
			
		}else{
		
			map.put("recipientEmails", mail.getRecipientsArr());
			map.put("ccEmails",mail.getCcArr()==null?new ArrayList<String>():mail.getCcArr());
			map.put("msgText", mail.getContent()==null?"":mail.getContent());//邮件内容
			
		}
	
		return map;
	}

	/**
	 * 查询跟团线路关联的主从线路id
	 * @param routeId
	 * @return
	 */
	@Override
	public long queryMainRouteId(int routeId) {
		logger.info("queryMainRouteId Bgn: routeId is " + routeId);
		long mainRouteId = routeId;
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam
					.setServiceName("BOH.NM.ProductController.queryMainRouteId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("routeId", routeId);
			String requestMsg = JSONObject.fromObject(map).toString();
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);
			logger.info("requestMsg is:"+requestMsg);
			ResponseResult result = tspCommonClient
					.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("queryMainRouteId return is " + retStr);
			JSONObject jsonObj = JSONObject.fromObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				Object data = jsonObj.get("data");
				JSONArray routeInfoArray = JSONArray.fromObject(data);
				if (null != routeInfoArray && !routeInfoArray.isEmpty()) {
					for (Object routeInfo : routeInfoArray) {
						JSONObject routeInfoJson = JSONObject
								.fromObject(routeInfo);
						if (JsonUtil.getIntValue(routeInfoJson, "isMainRoute") == 1) {
							mainRouteId = JsonUtil.getIntValue(routeInfoJson,
									"routeId");
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("查询BOH接口出错",e);
		}

		return mainRouteId;
	}

	/**
	 * 获取供应商信息 BOSS3
	 * @param orderId
	 * @return
	 */
	public Map<String, Object> getAgencyInfo(int orderId) {
		
        
		logger.error("getBoss3AgencyInfo Bgn: orderId is " + orderId);
		Map<String, Object> agencyfo = new HashMap<String, Object>();
		try {
			
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("CFM.confirm.ConfirmQueryController.queryVendorInfo");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			map.put("systemType", 1001); // systemType传1001 就返回跟团资源的一个供应商信息
			String requestMsg = JSONObject.fromObject(map).toString();
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.error("getBoss3AgencyInfo return is " + retStr);
			JSONObject jsonObj = JSONObject.fromObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONObject jsonData = jsonObj.getJSONObject("data");
				JSONArray temp = jsonData.getJSONArray("vendorInfoList");
				if (null != temp && !temp.isEmpty()) {
					Object productObj = temp.get(0);
					JSONObject fmtProduct = JSONObject.fromObject(productObj);
					if (null != fmtProduct && !fmtProduct.isEmpty()) {
						int agencyId = JsonUtil.getIntValue(fmtProduct, "resourceId");//供应商编码
						String agencyName = JsonUtil.getStringValue(fmtProduct, "resourceName");//供应商名称
						agencyfo.put("agencyId", agencyId);
						agencyfo.put("agencyName",  agencyName);
						agencyfo.put("errCode", 0);
					}
				}
			}else{
				
				agencyfo.put("errCode", 1);
				agencyfo.put("errorMesg",JsonUtil.getStringValue(jsonObj, "msg"));
			}
		} catch (Exception e) {
			
			agencyfo.put("errCode", 1);
			agencyfo.put("errorMesg", e.getMessage());
		}
		return agencyfo;
		
		
	}
	
	@Override
	public String[] getWetherAlarmCitys(String alarmCode) {
		String[] citys = new String[]{};
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("alarmCode", alarmCode);
        String reqData = JSONObject.fromObject(mp).toString();
        
        RequestParam requestParam = new RequestParam();
		// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
		requestParam.setServiceName("PLA.BAP.WeatherController.alarm");
		requestParam.setData(reqData);
		requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
		try {
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			JSONObject resultStr = JSONObject.fromObject(retStr);
	        JSONObject jsonObject = JSONObject.fromObject(resultStr);
			boolean isSuccess = jsonObject.getBoolean("success");
			if (isSuccess) {
				JSONObject dataObj = jsonObject.getJSONObject("data");
				JSONArray alarmInfoArr = dataObj.getJSONArray("alarmInfoList");
				for (int i=0; i<alarmInfoArr.size(); i++) {
					JSONObject infoObj = (JSONObject) alarmInfoArr.get(i);
					if (DateUtil.getTomorrowStr().equals(infoObj.getString("date"))) {
						JSONArray cityArr = infoObj.getJSONArray("alarmCity");
						citys = (String[]) cityArr.toArray(new String[cityArr.size()]);
					}
				}
			}
		} catch (RestException e) {
			logger.error(e.getMessage(), e);
		} catch (NoServiceAvailableException e) {
			logger.error(e.getMessage(), e);
		} catch (ServiceForbidenException e) {
			logger.error(e.getMessage(), e);
		}
        return citys;
	}
	
	public SystemVo getSystem(long id, int type) {
		SystemVo system = null;
		List<SystemVo> sysList = getNumericalCache();
		for (SystemVo sys : sysList) {
			if (type == sys.getSysType()) {
				List<Map<String, Integer>> numList = sys.getNumericals();
				for (Map<String, Integer> map : numList) {
					int min = map.get("min");
					int max = map.get("max");
					if (min <= id && id <= max) {
						system = sys;
					}
				}
			}
		}
		return system;
	}
	
	@SuppressWarnings("unchecked")
	private List<SystemVo> getNumericalCache() {
		List<SystemVo> sysList = (List<SystemVo>) MemcachesUtil.getObj("CMP_SYS_LIST");
		if (null == sysList || sysList.isEmpty()) {
			sysList = getSystemList();
			for (SystemVo sys : sysList) {
				sys.setNumericals(getNumerical(sys.getSysType(), sys.getSysCode()));
			}
			MemcachesUtil.setInToday("CMP_SYS_LIST", sysList);
		}
		return sysList;
	}
	
	private List<SystemVo> getSystemList() {
		SystemVo ngPrd = new SystemVo();
		ngPrd.setSysName(SysConstans.NG_BOSS_PRD_NAME);
		ngPrd.setSysCode(SysConstans.NG_BOSS_PRD_CODE);
		ngPrd.setSysType(SysConstans.NG_BOSS_PRD_TYPE);
		ngPrd.setSysUrl(SysConstans.NG_BOSS_PRD_URL);
		
		SystemVo tdPrd = new SystemVo();
		tdPrd.setSysName(SysConstans.TD_PRD_NAME);
		tdPrd.setSysCode(SysConstans.TD_PRD_CODE);
		tdPrd.setSysType(SysConstans.TD_PRD_TYPE);
		tdPrd.setSysUrl(SysConstans.TD_PRD_URL);
		
		SystemVo bossPrd = new SystemVo();
		bossPrd.setSysName(SysConstans.BOSS_PRD_NAME);
		bossPrd.setSysCode(SysConstans.BOSS_PRD_CODE);
		bossPrd.setSysType(SysConstans.BOSS_PRD_TYPE);
		bossPrd.setSysUrl(SysConstans.BOSS_PRD_URL);
		
		List<SystemVo> sysList = new ArrayList<SystemVo>();
		sysList.add(ngPrd);
		sysList.add(tdPrd);
		sysList.add(bossPrd);
		
		return sysList;
	}
	
	private List<Map<String, Integer>> getNumerical(int intervalGroup, int intervalType) {
		List<Map<String, Integer>> resultList = new ArrayList<Map<String,Integer>>();
		
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("intervalGroup", intervalGroup);
        mp.put("intervalType", intervalType);
        String reqData = JSONObject.fromObject(mp).toString();
        
        RequestParam requestParam = new RequestParam();
		requestParam.setServiceName("TBS.Numerical.IntervalController.queryIntreval");
		requestParam.setData(reqData);
		requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
		try {
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			JSONObject resultStr = JSONObject.fromObject(retStr);
	        JSONObject jsonObject = JSONObject.fromObject(resultStr);
			boolean isSuccess = jsonObject.getBoolean("success");
			if (isSuccess) {
				JSONArray dataArr = jsonObject.getJSONArray("data");
				for (int i=0; i<dataArr.size(); i++) {
					JSONObject dataObj = (JSONObject) dataArr.get(i);
					Map<String, Integer> map = new HashMap<String, Integer>();
					map.put("min", dataObj.getInt("min"));
					map.put("max", dataObj.getInt("max"));
					resultList.add(map);
				}
			}
		} catch (RestException e) {
			logger.error(e.getMessage(), e);
		} catch (NoServiceAvailableException e) {
			logger.error(e.getMessage(), e);
		} catch (ServiceForbidenException e) {
			logger.error(e.getMessage(), e);
		}
        return resultList;
	}
	
	@Override
	public Map<String, Object> getGuideInfo(int orderId) {
		
		Map<String, Object> guideInfo = new HashMap<String, Object>();
		guideInfo.put("guideId", "");
		guideInfo.put("guideName", "");
		guideInfo.put("guideCall", "");
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail");
			Map<String, Object> map = new HashMap<String, Object>();
			List<Integer> orderIds = new ArrayList<Integer>();
			orderIds.add(orderId);
			map.put("orderIds", orderIds); // 订单号
			String requestMsg = JSONObject.fromObject(map).toString();
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail return is " + retStr);
			JSONObject jsonObj = JSONObject.fromObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				Object data = jsonObj.get("data");
				JSONObject fmtData = JSONObject.fromObject(data);
				if (null != fmtData && !fmtData.isEmpty()) {
					JSONArray list = fmtData.getJSONArray("orderList");
					if(null!=list&&list.size()>0){
						
						JSONObject  orderObject = list.getJSONObject(0);
						JSONArray guideList = orderObject.getJSONArray("guideList");
						if(null!=guideList&& guideList.size()>0){
							
							JSONObject  guideObject = guideList.getJSONObject(0);
							String guideId = JsonUtil.getStringValue(guideObject, "guideId");//导游编号
							String guideName = JsonUtil.getStringValue(guideObject, "realname");//导游姓名
							guideInfo.put("guideId", guideId);
							guideInfo.put("guideName", guideName);
							JSONArray callList = guideObject.getJSONArray("contactList");
							
							if(null!=callList && callList.size()>0){
							
								  JSONObject  callObject = callList.getJSONObject(0);
								  String contactNum = JsonUtil.getStringValue(callObject, "contactNum");//导游电话
								  guideInfo.put("guideCall", contactNum);
							}
							
						}
						
					}
					
				}
					
			}			
		} catch (Exception e) {
			logger.error("TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail error is " + e.getMessage(),e);
		}
		return guideInfo;
	}


    @Override
    public List<Integer> queryAllRelatedRouteIds(int routeId) {
        logger.info("queryAllRelatedRouteIds Bgn: routeId is " + routeId);
        List<Integer>  routeIds = new ArrayList<Integer>();
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("BOH.NM.ProductController.queryMainRouteId");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("routeId", routeId);
            String requestMsg = JSONObject.fromObject(map).toString();
            requestParam.setData(requestMsg);
            requestParam.setMethod(RestMethod.GET);
            logger.info("requestMsg is:"+requestMsg);
            ResponseResult result = tspCommonClient.responseCaller(requestParam);
            String retStr = result.getReturnStr();// 获取返回的数据
            logger.info("queryAllRelatedRouteIds return is " + retStr);
            JSONObject jsonObj = JSONObject.fromObject(retStr);
            if ((Boolean) jsonObj.get("success")) {
                Object data = jsonObj.get("data");
                JSONArray routeInfoArray = JSONArray.fromObject(data);
                if (null != routeInfoArray && !routeInfoArray.isEmpty()) {
                    for (Object routeInfo : routeInfoArray) {
                        JSONObject routeInfoJson = JSONObject.fromObject(routeInfo);
                        routeIds.add(JsonUtil.getIntValue(routeInfoJson, "routeId"));
                    }
                    logger.info("构造主从线路id集合："+JSONArray.fromObject(routeIds));
                }
            }else {
                routeIds.add(routeId);
            }
        } catch (Exception e) {
            logger.error("查询BOH接口出错",e);
        }

        return routeIds;
    }
    
    public  String getRefundState(int refundId) {
        
        String stateStr = "";
        
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("ICS.sellincome.RefundController.queryRepEntityById");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("sourceSystem", "cmp");
            param.put("id", refundId);
            String jsonStr = JSONObject.fromObject(param).toString();
            logger.info("getRefundState Begin: param is " + jsonStr);
            requestParam.setData(jsonStr);
            requestParam.setMethod(RestMethod.POST);
            
            ResponseResult result = tspCommonClient.responseCaller(requestParam);
            String retStr = result.getReturnStr();// 获取返回的数据
            logger.info("getRefundState Ing: resultMsg is " + retStr);
            JSONObject jsonObject = JSONObject.fromObject(retStr);
            boolean isSuccess = jsonObject.getBoolean("success");
            if (isSuccess) {
                JSONObject dataObj = jsonObject.getJSONObject("data");
                int state = dataObj.getInt("state");
                switch (state) {
                case 1:
                    stateStr = "等待审核";
                    break;
                case 2:
                    stateStr = "已审核未分配";
                    break;
                case 3:
                    stateStr = "已审核已分配，未财务确认";
                    break;
                case 4:
                    stateStr = "财务已流程确认";
                    break;
                case 99:
                    stateStr = "完成退款";
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("getRefundState End: stateStr is " + stateStr);
        return stateStr;
    }
    
    /**
     * 供应商赔付调用财务接口, 向供应商索赔
     */
    public  void claimForSupplier(Map<String, Object> paramMap) {
        String result = "";
        
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("ICS.purchasepay.AgencyReparationsController.insertAgencyReparations");
            paramMap.put("sourceSystem", "cmp");
            paramMap.put("type", 1); // 1:供应商
            paramMap.put("formType", 1); // 1-赔款单，2-红冲单，3-调整单
            String jsonStr = JSONObject.fromObject(paramMap).toString();
            logger.info("ClaimForSupplier Begin: param is " + jsonStr);
            
            requestParam.setData(jsonStr);
            requestParam.setMethod(RestMethod.POST);
            
            ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
            result = responseResult.getReturnStr();// 获取返回的数据
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("ClaimForSupplier End: result is " + result);
    }
    
    /**
     * 对客赔付调用财务接口
     */
    public  int refundForCust(Map<String, Object> param) {
        
        int refundId = 0;
        
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("ICS.sellincome.RefundController.addRep");
            param.put("sourceSystem", "cmp");
            String jsonStr = JSONObject.fromObject(param).toString();
            logger.info("refundForCust Begin: param is " + jsonStr);
            requestParam.setData(jsonStr);
            requestParam.setMethod(RestMethod.POST);
            tspCommonClient.setSocketTimeout(1000);
            ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
            String result = responseResult.getReturnStr();// 获取返回的数据
            logger.info("refundForCust Ing: resultMsg is " + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            boolean isSuccess = jsonObject.getBoolean("success");
            if (isSuccess) {
                JSONObject dataObj = jsonObject.getJSONObject("data");
                refundId = dataObj.getInt("id");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("refundForCust End: refundId is " + refundId);
        return refundId;
    }
    
    /**
     * 查询供应商结算信息
     */
    public  JSONObject getAgencyInfo(String agencyId) {
        logger.info("getAgencyInfo Begin: agencyId is " + agencyId);
        JSONObject result = new JSONObject();
        JSONObject param = new JSONObject();
        
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("ICS.nb.SupplierAgencyController.findAgencyInfo");
            param.put("sourceSystem", "CMP");
            param.put("id", agencyId);
            requestParam.setData(param.toString());
            
            requestParam.setMethod(RestMethod.POST);
            
            ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
            String resultStr = responseResult.getReturnStr();// 获取返回的数据
            logger.info("getAgencyInfo Ing["+agencyId+"]: ResponseMessage is " + resultStr);
            JSONObject jsonObject = JSONObject.fromObject(resultStr);
            
            if (jsonObject.getBoolean("success")) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray rows = data.getJSONArray("rows");
                if(rows.size()>0){
                    result.put("foreignCurrencyType", rows.getJSONObject(0).getInt("foreignCurrencyType"));
                    result.put("currencyName", rows.getJSONObject(0).getString("currencyName"));
                    result.put("currencyUnit", rows.getJSONObject(0).getString("currencyUnit"));
                }
            }
        } catch (Exception e) {
            logger.error("trestClient.execute() error", e);
        }
        logger.info("getAgencyInfo End: agencyId is " + agencyId + ", result is " + result);
        return result;
    }
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-finance_subbank")
    private IFinanceSubbankService financeSubbankService;
    
    /**
     * PLA.csp.Json2SoapController.invoke
     * @return
     */
    public void syncSubBankInfo(String lastUpdate){
        
        int pStart = 1;
        int pLimit = 200;
        
        int restSubbankCount = 0; // 剩余待同步银行分行信息数量
        
        TspServiceEnum tspServiceEnum = TspServiceEnum.SYCH_SUBBANK;
        RequestParam requestParam = tspServiceEnum.getDefaultRequestParam();
        Map<String, Object> paramMap = tspServiceEnum.getDefaultRequestParamMap();
        
        Map<String, Object> main = (((Map<String, Object>) ((Map<String, Object>) paramMap.get("jsonData")).get("main")));
        
        main.put("pLastUpdateDate", lastUpdate);
        main.put("pStart", pStart);
        main.put("pLimit", pLimit);
        
        try {
            Map<String,Object> outerDataMap;
            Map<String,Object> resultMap;
            List<Map<String,Object>> bankMapList;
            List<FinanceSubbankEntity> subbankList;
            do {
                tspServiceEnum.setOrUpdateRequstData();
                Map<String,Object> responseMap = tspServiceEnum.getResultMap(tspCommonClient);
                if((Boolean) responseMap.get("success")) {
                    outerDataMap = JsonFormatter.toObject(responseMap.get("data") + "", Map.class);
                    resultMap = JsonFormatter.toObject(outerDataMap.get("result") + "", Map.class);
                    
                    if(restSubbankCount==0){
                        if("0".equals(resultMap.get("count") + "")){ //如果获取数量为0,则直接返回
                            logger.info("无新银行分行数据需要更新,最近更新时间["+lastUpdate+"]");
                            return;
                        }
                        restSubbankCount = Integer.valueOf(resultMap.get("count") + "");
                    }
                    
                    bankMapList = (List<Map<String, Object>>) resultMap.get("data");
                    subbankList = new ArrayList<FinanceSubbankEntity>();
                    subbankList = FinanceSubbankEntity.fromMapList(bankMapList);
                    financeSubbankService.batchSych(subbankList);
                    logger.info("批量同步银行信息到数据库" + JSONArray.fromObject(subbankList));

                    restSubbankCount -= bankMapList.size();
                    

                    pStart = pStart + bankMapList.size();
                    main.put("pStart", pStart);

                    if(restSubbankCount < pLimit) {
                        pLimit = restSubbankCount;
                        main.put("pLimit", pLimit);
                    }
                    
                }else{
                    break;
                }
            }while(restSubbankCount>0);
            
            
        } catch (Exception e) {
            logger.error("trestClient.execute() error", e);
        }
    }


    @Override
    public boolean changePrdStatus(Map<String, Object> paramMap) {
        boolean success = false;
        RequestParam requestParam = new RequestParam();
        requestParam.setServiceName("PCS.BFM.ProductController.changeProductStatus");
        String jsonStr = JSONObject.fromObject(paramMap).toString();
        logger.info("changePrdStatus Begin: param is " + jsonStr);
        requestParam.setData(jsonStr);
        requestParam.setMethod(RestMethod.GET);
        
        ResponseResult responseResult;
        try {
            responseResult = tspCommonClient.responseCaller(requestParam);
            String result = responseResult.getReturnStr();// 获取返回的数据
            logger.info("changePrdStatus Ing: resultMsg is " + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            boolean isSuccess = jsonObject.getBoolean("success");
            if (isSuccess) {
                success = true;
            }
        } catch(Exception e) {
            logger.error("changePrdStatus error,",e);
        }
        
        return success;
    }

/**
 * PLA.csp.Json2SoapController.invoke
 * @return
 */
    public JSONArray fetchBigBanks() {
        int pStart = 1;
		int pLimit = 300;

        int restBigBankCount = 0; // 剩余待同步银行分行信息数量
        JSONArray bigBankArray = new JSONArray();
        TspServiceEnum tspServiceEnum = TspServiceEnum.FETCH_BIGBANK;
        RequestParam requestParam = tspServiceEnum.getDefaultRequestParam();
        Map<String, Object> paramMap = tspServiceEnum.getDefaultRequestParamMap();

        Map<String, Object> main = (((Map<String, Object>) ((Map<String, Object>) paramMap.get("jsonData")).get("main")));
        main.put("pStart", pStart);
        main.put("pLimit", pLimit);
        try {
            Map<String, Object> outerDataMap;
            Map<String, Object> resultMap;
            List<Map<String, Object>> bankMapList;
            do {
                tspServiceEnum.setOrUpdateRequstData();
                Map<String, Object> responseMap = tspServiceEnum.getResultMap(tspCommonClient);
                if((Boolean) responseMap.get("success")) {
                    outerDataMap = JsonFormatter.toObject(responseMap.get("data") + "", Map.class);
                    resultMap = JsonFormatter.toObject(outerDataMap.get("result") + "", Map.class);
                    if(restBigBankCount == 0) {
						if ("0".equals(resultMap.get("count") + "")) { // 如果获取数量为0,则直接返回
                            return bigBankArray;
                        }
                        restBigBankCount = Integer.valueOf(resultMap.get("count") + "");
                    }

                    bankMapList = (List<Map<String, Object>>) resultMap.get("data");
                    for(Map<String, Object> map : bankMapList) {
                        bigBankArray.add(map.get("bankName"));
                    }

                    restBigBankCount -= bankMapList.size();

                    pStart = pStart + bankMapList.size();
                    main.put("pStart", pStart);

                    if(restBigBankCount < pLimit) {
                        pLimit = restBigBankCount;
                        main.put("pLimit", pLimit);
                    }

                } else {
                    break;
                }
            } while(restBigBankCount > 0);

        } catch(Exception e) {
            logger.error("fetchBigBanks  error", e);
        }

        return bigBankArray;
    }



enum TspServiceEnum{
    SYCH_SUBBANK("PLA.csp.Json2SoapController.invoke"){
        public Map<String,Object> getDefaultRequestParamMap(){
            Map<String,Object> paramMap = getParamMap();
            paramMap.put("url", "http://10.10.41.11:7081/CuxWebSevApp-CuxCeWebSevPro-context-root/CuxCeBankSevPort?WSDL");
            paramMap.put("namespace", "http://dev/CuxCeBankSev.wsdl");
            paramMap.put("socketTimeout", "60");
            paramMap.put("action", "http://dev/CuxCeBankSev.wsdl/main");
            
            Map<String, Object> jsonData = new LinkedHashMap<String, Object>(); 
            Map<String, Object> main = new LinkedHashMap<String, Object>();
            main.put("pSourceSystem", "CMP");
            main.put("pHomeCountry", null);
            main.put("pBankName", null);
            main.put("pSubbankName", null);
            main.put("pSubbankProvince", null);
            main.put("pSubbankCity", null);
            main.put("pLineNumber", null);
            main.put("pSwiftCode", null);
            
            jsonData.put("main", main);
            
            paramMap.put("jsonData", jsonData);
            return paramMap;
        }
    },FETCH_BIGBANK("PLA.csp.Json2SoapController.invoke"){
        public Map<String,Object> getDefaultRequestParamMap(){
            Map<String,Object> paramMap = getParamMap();
            paramMap.put("url", "http://10.10.39.11:7008/Application2-Project2-context-root/CuxCeBankNameWebAppPort?WSDL");
            paramMap.put("namespace", "http://dbtndev/CuxCeBankNameWebApp.wsdl");
            paramMap.put("socketTimeout", "30");
            paramMap.put("action", "http://dbtndev/CuxCeBankNameWebApp.wsdl/main");
            
            Map<String, Object> jsonData = new LinkedHashMap<String, Object>(); 
            Map<String, Object> main = new LinkedHashMap<String, Object>();
            main.put("pSourceSystem", "CMP");
            main.put("pBankNumber", null);
            main.put("pBankName", null);
            main.put("pStart", null);
            main.put("pLimit", 200);
            
            jsonData.put("main", main);
            
            paramMap.put("jsonData", jsonData);
            return paramMap;
        }
    };
    private String serviceName;
    
    private RequestParam requestParam;
    private Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
    private Map<String,Object> responseMap;
    
    
    TspServiceEnum(String serviceName){
        this.serviceName = serviceName;
    }
    
    public Map<String, Object> getParamMap(){
        return paramMap;
    }
    
    public RequestParam getDefaultRequestParam(){
        requestParam = new RequestParam();
        requestParam.setServiceName(serviceName);
        return requestParam;
    }
    
    public Map<String,Object> getDefaultRequestParamMap(){
        
        return getParamMap();
    }
    
    public Map<String,Object> getResultMap(TSPCommonClient tspCommonClient) throws NoServiceAvailableException, ServiceForbidenException, JsonParseException, JsonMappingException, IOException{
        tspCommonClient.setSocketTimeout(6000);
        tspCommonClient.setConnectTimeout(6000);
        ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
        String resultStr = responseResult.getReturnStr();// 获取返回的数据
        responseMap = JsonFormatter.toObject(resultStr, Map.class);
        return responseMap;
    }
    
    public void setOrUpdateRequstData() throws JsonGenerationException, JsonMappingException, IOException{
        requestParam.setData(JsonFormatter.toJsonString(paramMap));
    }
}



	

	@Override
	public Integer getRestdayType(Date tempDate) {
		Integer dayType =0;
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("PLA.BAP.HolidayController.query"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("year", DateUtil.getYear(tempDate));
			map.put("month", DateUtil.getMonth(tempDate));
			map.put("day", DateUtil.getDay(tempDate));
			String requestMsg = JSONObject.fromObject(map).toString();
			logger.info("PLA.BAP.HolidayController.query send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("PLA.BAP.HolidayController.query return is " + retStr);
			JSONObject resultStr = JSONObject.fromObject(retStr);
			if ((Boolean) resultStr.get("success")) {
				JSONObject dataArr = resultStr.getJSONObject("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					JSONArray dates = dataArr.getJSONArray("dates");
					if (null != dates && !dates.isEmpty()) {
						JSONObject date = dates.getJSONObject(0);
						if (null != date && !date.isEmpty()) {
							dayType = JsonUtil.getIntValue(date, "type");
						}
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return dayType;
	}

	@Override
	public String addInCompleteCard(ComplaintSolutionEntity complaintSolution) {
		String cardUniqueId="";
		CardUniqueEntity cardUniqueEntity = new CardUniqueEntity();
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("cardNo", TripleDES.encrypt(complaintSolution.getAccount()));
	    paramMap.put("accName", complaintSolution.getReceiver());
	    paramMap.put("province", complaintSolution.getBankProvince());
	    paramMap.put("city", complaintSolution.getBankCity());
	    paramMap.put("bankName", complaintSolution.getBigBank());
	    paramMap.put("bankBranchName", complaintSolution.getBank());
	    String param = JSONObject.fromObject(paramMap).toString();
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("FBS.ID.CardInCompleteController.addInCompleteCard"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			String requestMsg = JSONObject.fromObject(paramMap).toString();
			logger.info("FBS.ID.CardInCompleteController.addInCompleteCard send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("FBS.ID.CardInCompleteController.addInCompleteCard return is " + retStr);
			JSONObject resultStr = JSONObject.fromObject(retStr);
			if ((Boolean) resultStr.get("success")) {
				JSONObject cardInfo = resultStr.getJSONObject("data");
				if (null != cardInfo && !cardInfo.isEmpty()) {
					cardUniqueId = JsonUtil.getStringValue(cardInfo, "cardUniqueId");
				}
			}else{
				logger.info("FBS.ID.CardInCompleteController.addInCompleteCard failed id: " + complaintSolution.getId());
			}
		} catch (Exception e){
			logger.error("addInCompleteCard error is " + e.getMessage(),e);
			e.printStackTrace();
		}
		return cardUniqueId;
	}
	
	@Override
	public CardUniqueEntity queryCardInfoByCardId(String cardUniqueId) {
		CardUniqueEntity cardUniqueEntity = new CardUniqueEntity();
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("cardUniqueId", cardUniqueId);
	    String param = JSONObject.fromObject(paramMap).toString();
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("FBS.ID.CardInCompleteController.queryInCompleteCardByCardUniqueId"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			String requestMsg = JSONObject.fromObject(paramMap).toString();
			logger.info("FBS.ID.CardInCompleteController.queryInCompleteCardByCardUniqueId send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("FBS.ID.CardInCompleteController.queryInCompleteCardByCardUniqueId return is " + retStr);
			JSONObject resultStr = JSONObject.fromObject(retStr);
			if ((Boolean) resultStr.get("success")) {
				JSONObject cardInfo = resultStr.getJSONObject("data");
				if (null != cardInfo && !cardInfo.isEmpty()) {
					cardUniqueEntity.setCardUniqueId(JsonUtil.getStringValue(cardInfo, "cardUniqueId"));
					cardUniqueEntity.setBankName(JsonUtil.getStringValue(cardInfo, "bankName"));
					cardUniqueEntity.setCardNo(JsonUtil.getStringValue(cardInfo, "cardNo"));
					cardUniqueEntity.setProvince(JsonUtil.getStringValue(cardInfo, "province"));
					cardUniqueEntity.setCity(JsonUtil.getStringValue(cardInfo, "city"));
					cardUniqueEntity.setBankBranchName(JsonUtil.getStringValue(cardInfo, "bankBranchName"));
					cardUniqueEntity.setAccName(JsonUtil.getStringValue(cardInfo, "accName"));
				}
			}
		} catch (Exception e){
			logger.error("queryCardInfoByCardId error is " + e.getMessage(),e);
			e.printStackTrace();
		}
		return cardUniqueEntity;
	}
	
	@Override
	public String sendShortMessageService(JSONObject params) {
		String resultStr=null;
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("PLA.SMS.SmsController.sendSms"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			String paramStr = params.toString();
			logger.info("PLA.SMS.SmsController.sendSms send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			resultStr = result.getReturnStr();// 获取返回的数据
			logger.info("PLA.SMS.SmsController.sendSms return is " + resultStr);
		} catch (Exception e){
			logger.error("PLA.SMS.SmsController.sendSms error is " + e.getMessage(),e);
			e.printStackTrace();
		}
		return resultStr;
	}	

	@Override
	public String getHandoverSaler(JSONObject params) {
		String resultStr=null;
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("NWS.OrderController.queryPeopleInfoByOrderId"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			String paramStr = params.toString();
			logger.info("NWS.OrderController.queryPeopleInfoByOrderId send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info("NWS.OrderController.queryPeopleInfoByOrderId return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					if(!dataInfo.getString("handoverSalerName").equals("null")){
						resultStr=dataInfo.getString("handoverSalerName");
					}
				}
			}
		} catch (Exception e){
			logger.error("NWS.OrderController.queryPeopleInfoByOrderId error is " + e.getMessage(),e);
		}
		return resultStr;
	}
	
	@Override
	public JSONObject getAgencyContactInfo(Integer agencyId, Integer complaint) {
		JSONObject resultObject = new JSONObject();
		//查询投诉单，判断是否为新分销投诉，来区分调用的TSP服务，true：新分销接口     false：旧接口
		boolean isDistributeFlag = complaintService.getDistributeFlag(complaint);
		String tspUrl = isDistributeFlag ?  "PHX.VND.ams.AgencyContactController.getAgencyContactList" : "VND.ams.AgencyContactController.getAgencyContactList";
				
		try {
			RequestParam requestParam = new RequestParam();
			Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("sourceSystem", "CMP");
	        paramMap.put("agencyId", agencyId);
	        List<Integer> contactTypeIds = new ArrayList<Integer>();
	        contactTypeIds.add(0);
	        paramMap.put("contactTypeIds",contactTypeIds);
	        String paramStr = JSONObject.fromObject(paramMap).toString();
			requestParam.setServiceName(tspUrl); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			logger.info(tspUrl + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(tspUrl + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);	
			if ((Boolean) resultJson.get("success")) {
				resultObject = resultJson.getJSONObject("data");
			}
		} catch (Exception e){
			logger.error(tspUrl + " error is " + e.getMessage(),e);
		}
		
		return resultObject;
	}
	
	@Override
	public int checkAgencyInfo(String name, Integer complaintId) {
		int agencyId =0;
		try {
			AgencyEntity agencyEntity = getAgencyEntity(name,complaintId);
			if (agencyEntity!=null) {
				agencyId = agencyEntity.getAgencyId();
			}
		} catch (Exception e) {
			logger.info("checkAgencyInfo name:"+ name + " complaintId:" + complaintId);
		}
		return agencyId;
	}
	

	@Override
	public AgencyEntity getAgencyEntity(String name, Integer complaintId) {
		AgencyEntity agencyEntity = null;
		//查询投诉单，判断是否为新分销投诉，来区分调用的TSP服务，true：新分销接口     false：旧接口
		boolean isDistributeFlag = complaintService.getDistributeFlag(complaintId);
		String tspUrl = isDistributeFlag ? "PHX.VND.ams.AgencyController.searchAgencyList" : "VND.ams.AgencyController.searchAgencyList";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(tspUrl); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sourceSystem", Constans.SYSTEM_CODE);//系统码
			map.put("companyName", name);//供应商名称
			map.put("start", 0);//开始
			map.put("limit", 1);//结束
			String requestMsg = JSONObject.fromObject(map).toString();
			logger.info(tspUrl + " send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info(tspUrl + " return is " + retStr);
			JSONObject resultStr = JSONObject.fromObject(retStr);
			if ((Boolean) resultStr.get("success")) {
				JSONObject dataArr = resultStr.getJSONObject("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					JSONArray rows = dataArr.getJSONArray("rows");
					if (null != rows && !rows.isEmpty()) {
						JSONObject agency = rows.getJSONObject(0);
						if (null != agency && !agency.isEmpty()) {
							agencyEntity = new AgencyEntity();
							agencyEntity.setAgencyId(agency.getInt("agencyId"));
							agencyEntity.setCompanyName(agency.getString("companyName"));
							agencyEntity.setAgencyName(agency.getString("fullName"));
						}
					}
				}
			}
		} catch (Exception e){
			logger.error(tspUrl + " error is " + e.getMessage(),e);
		}
		return agencyEntity;
	}
	
	@Override
	public Integer queryRefundCompanyId(Integer orderId){
		Integer resultStr=12;
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("PGA.NWS.PipelineController.queryOrders"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			List<Integer> orderIdList = new ArrayList<Integer>();
			Map<String, Object> map = new HashMap<String, Object>();
			orderIdList.add(orderId);
			map.put("key", "QuerySignAndAgencyInfo");
			map.put("orderIds", orderIdList); // 请求来源1=电话 2=网站3=无线，投诉系统传0
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info("PGA.NWS.PipelineController.queryOrders send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info("PGA.NWS.PipelineController.queryOrders return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONArray dataArray = resultJson.getJSONArray("data");
				JSONObject dataInfo = dataArray.getJSONObject(0);
				if (null != dataInfo && !dataInfo.isEmpty()) {
				    JSONArray agencyArray = dataInfo.getJSONArray("agency");
                    JSONObject agency = agencyArray.getJSONObject(0);
                    if(0 != agency.getInt("saleOrgId"))
                    {
                        resultStr=agency.getInt("saleOrgId");
                    }else{
                        JSONArray signArray = dataInfo.getJSONArray("sign");
                        JSONObject sign = signArray.getJSONObject(0);
                        if(1==sign.getInt("isSign")){//订单已签约
                            if(0!=sign.getInt("signManagerId")){
                                resultStr=sign.getInt("signManagerId");
                            }
                        }
                    }
					
				}
			}
		} catch (Exception e){
			logger.error("PGA.NWS.PipelineController.queryOrders error is " + e.getMessage(),e);
		}
		return resultStr;
	}
	
	public List<BaseEntity> getClassBrandParentFromBoh(Integer productTypeId){
		List<BaseEntity> resultList= new ArrayList<BaseEntity>();
		String serviceName = "BOH.PL.ProductTypeController.root";
		try {
			RequestParam requestParam = new RequestParam();
			Map<String, Object> map = new HashMap<String, Object>();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			map.put("limit", 0);
			map.put("productTypeId", productTypeId);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					JSONArray dataArr = dataInfo.getJSONArray("rows");
					for(int i = 0;i<dataArr.size();i++){
						JSONObject jsonParam = dataArr.getJSONObject(i);
						BaseEntity tempEntity = new BaseEntity();
						tempEntity.setId(jsonParam.getInt("productTypeId"));
						tempEntity.setName(jsonParam.getString("productTypeName"));
						resultList.add(tempEntity);
					}
				}
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return resultList;
	}
	
	public List<BaseEntity> getClassBrandFromBoh(Integer productTypeId){
		List<BaseEntity> resultList= new ArrayList<BaseEntity>();
		String serviceName = "BOH.PL.ProductTypeController.sub";
		try {
			RequestParam requestParam = new RequestParam();
			Map<String, Object> map = new HashMap<String, Object>();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			map.put("limit", 0);
			map.put("productTypeId", productTypeId);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					JSONArray dataArr = dataInfo.getJSONArray("rows");
					for(int i = 0;i<dataArr.size();i++){
						JSONObject jsonParam = dataArr.getJSONObject(i);
						BaseEntity tempEntity = new BaseEntity();
						tempEntity.setId(jsonParam.getInt("productTypeId"));
						tempEntity.setName(jsonParam.getString("productTypeName"));
						resultList.add(tempEntity);
					}
				}
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return resultList;
	}
	
	public List<BaseEntity> getDestClassFormBoh(Integer productTypeId){
		List<BaseEntity> resultList= new ArrayList<BaseEntity>();
		String serviceName = "BOH.PL.ProductTypeController.destClass";
		try {
			RequestParam requestParam = new RequestParam();
			Map<String, Object> map = new HashMap<String, Object>();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			map.put("productTypeId", productTypeId);
			map.put("limit", 0);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					JSONArray dataArr = dataInfo.getJSONArray("rows");
					for(int i = 0;i<dataArr.size();i++){
						JSONObject jsonParam = dataArr.getJSONObject(i);
						BaseEntity tempEntity = new BaseEntity();
						tempEntity.setId(jsonParam.getInt("destClassId"));
						tempEntity.setName(jsonParam.getString("destClassName"));
						resultList.add(tempEntity);
					}
				}
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return resultList;
	}
	
	public List<BaseEntity> getDestGroupInfoFromBoh(Integer class_brand_id,Integer product_new_line_type_id){
		List<BaseEntity> resultList= new ArrayList<BaseEntity>();
		String serviceName = "BOH.PL.DestClassController.destGroup";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", class_brand_id);
			map.put("destClassId", product_new_line_type_id);
			map.put("limit", 0);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					JSONArray dataArr = dataInfo.getJSONArray("rows");
					for(int i = 0;i<dataArr.size();i++){
						JSONObject jsonParam = dataArr.getJSONObject(i);
						BaseEntity tempEntity = new BaseEntity();
						tempEntity.setId(jsonParam.getInt("destGroupId"));
						tempEntity.setName(jsonParam.getString("destGroupName"));
						resultList.add(tempEntity);
					}
				}
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return resultList;
	}
	
	public Map<String,String> getContactPhoneMapFromPGA(Integer orderId){
		Map<String,String> resultList= new HashMap<String,String>();
		JSONObject dataInfo = getComplaintOrderInfoFromPGA(orderId);
		if (null != dataInfo && !dataInfo.isEmpty()) {
			JSONArray dataArr = dataInfo.getJSONArray("contactList");
			for(int i = 0;i<dataArr.size();i++){
				JSONObject jsonParam = dataArr.getJSONObject(i);
				String concactTel = jsonParam.getString("tel");
				if(StringUtil.isNotEmpty(concactTel)&&(!"0".equals(concactTel))){
					resultList.put(concactTel, jsonParam.getString("contactName"));
				}
			}
		}
		return resultList;
	}
	
	public JSONArray getSpecialCustListFromCRM(List<String> phoneList){
		JSONArray resultList= new JSONArray();
		String serviceName = "CRM.CSR.SpecialListController.TR010104";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			
			List<JSONObject> param = new ArrayList<JSONObject>();
			for(String phone:phoneList){
				JSONObject tempJson = new JSONObject();
				tempJson.put("valType", "C0101002002");
				tempJson.put("value", phone);
				param.add(tempJson);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("paramList", param);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			
			Map<String, String> requestHead = new HashMap<String, String>();
			requestHead.put("Content-Type", "text/plain;charset=UTF-8");
			requestParam.setHeader(requestHead);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			requestParam.setBase64(false);
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String resultString = result.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + resultString);
			JSONObject resultJson = JSONObject.fromObject(resultString);
			if ((Boolean) resultJson.get("success")) {
				JSONObject dataInfo = resultJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					resultList = dataInfo.getJSONArray("resultList");
				}
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return resultList;
	}
	
	public JSONObject getComplaintOrderInfoFromPGA(Integer orderId){
		JSONObject result= new JSONObject();
		String serviceName = "NWS.OrderController.complaintQualityTesting";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
//			ResponseResult response = tspCommonClient.responseCaller(requestParam);
//			String responseString = response.getReturnStr();// 获取返回的数据
//			logger.info(serviceName + " return is " + responseString);
//			JSONObject responseJson = JSONObject.fromObject(responseString);
			
			
			TRestClient trestClient = new TRestClient("http://public-api.bj.pga.tuniu.org/pga-web/nws/order/complaint");
			  trestClient.setMethod("GET"); trestClient.setData(paramStr);
			  String execute = trestClient.execute(); 
			  JSONObject responseJson = JSONObject.fromObject(execute);
			
			
			
			if ((Boolean) responseJson.get("success")) {
				result = responseJson.getJSONObject("data");
			}
		} catch (Exception e){
			logger.error(serviceName + " error is " + e.getMessage(),e);
		}
		return result;
	}
	
	public String getOrderDetailPageUrl(Integer orderId) {
		String result = new String();
		String serviceName = "PGA.NWS.PipelineController.getAdaptedApiConf";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好,
														// 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			map.put("serviceKey", "Common-OrderDetailPage");
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setData(paramStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult response = tspCommonClient.responseCaller(requestParam);
			String responseString = response.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + responseString);
			JSONObject responseJson = JSONObject.fromObject(responseString);
			if ((Boolean) responseJson.get("success")) {
				JSONObject dataInfo = responseJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					result = dataInfo.getString("outerService");
				}
			}
		} catch (Exception e) {
			logger.error(serviceName + " error is " + e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 申诉发起OA赔偿金承担方修改申请
	 */
	public String launchSupplierAppeal(AgengcyPayoutEntity agengcyPayoutEntity){
		String result = null;
		String serviceName = "SOA.SOA.Flow2ResourceController.launchCollaborationForTspNoToken";
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName(serviceName); // 服务名需在xml中consumerConfig中配置好,否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("senderLoginName", agengcyPayoutEntity.getAppealerId().toString());////表单发起人， 8位工号（不够左侧0补齐）或者ucid
			map.put("subject", "赔偿金承担方修改申请");//表单标题，业务系统提供，建议同表单名称
		    //业务系统拼接json格式的字符串（可直接拼接json字符串，也可根据xml转换为json串），字符串具体内容参加文档“新OA提供(赔偿金承担方修改申请)接口data字段说明20170609”
			map.put("data", getParamData(agengcyPayoutEntity));
			map.put("param","0"); //OA方提供, 为控制是否流程发送。0：发送缺省值 1：不发送（申请人待办）
			//OA侧根据表单提供具体的模板编号唯一标识,例如“赔偿金承担方修改申请”模板编号为:pcjcdfxg002
			map.put("templateCode","pcjcdfxg002");
			String paramStr = JSONObject.fromObject(map).toString();
			logger.info(serviceName + " send is " + paramStr);
			requestParam.setBase64(false);//写死
			requestParam.setMethod(RestMethod.POST);//写死
			requestParam.setData(paramStr);
			ResponseResult response = tspCommonClient.responseCaller(requestParam);
			String responseString = response.getReturnStr();// 获取返回的数据
			logger.info(serviceName + " return is " + responseString);
			JSONObject responseJson = JSONObject.fromObject(responseString);
			if ((Boolean) responseJson.get("success")) {
				JSONObject dataInfo = responseJson.getJSONObject("data");
				if (null != dataInfo && !dataInfo.isEmpty()) {
					result = dataInfo.getString("oaId");
				}
			} else{
				String  msg = responseJson.get("msg").toString();
		    	if (StringUtil.isNotEmpty(msg)){
		    		logger.error(decodeUnicode(msg));
		    	}
			}
		} catch (Exception e) {
			logger.error(serviceName + " error is " + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 组装json字符串，用于发起表单
	 * @return 表单内容对应json字符串
	 */
	private String getParamData(AgengcyPayoutEntity agengcyPayoutEntity) {
		String str = "";
		try {
			//根据模板获取xml内容
			str = FreemarkerUtil.getContent("appealAgengcyPayout.ftl", agengcyPayoutEntity);
			//底层组提供xml转换为json格式工具，调用方也可直接参照文档格式拼接json字符串
			str = StaxonUtils.xml2json(str);
		} catch (XMLStreamException e) {
			logger.error("XMLStreamException complaintId:"+agengcyPayoutEntity.getComplaintId());
		} catch (Exception e) {
			logger.error("xml convert failed complaintId:"+agengcyPayoutEntity.getComplaintId());
		}
		System.out.println(str);
		return str;	
	}
	
	private static String decodeUnicode(final String dataStr) {
		if (dataStr.indexOf("\\u") == -1) {
			return dataStr;
		}
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}
	
	/**
	 * 表单数据封装
	 * @param obj
	 * @return
	 */
	private Map<String, Object> getDataMap(AgengcyPayoutEntity agengcyPayoutEntity) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		return dataMap;
	}
	/**
     * 查询机票运营人员
     */
    public List<String> queryTicketOrder(Integer orderId) {
        String result = "";
        List<String> agentList = new ArrayList<String>();
        try{
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName("PGA.NWS.PipelineController.doService");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderId", orderId);
            paramMap.put("serviceKey", "Common-QueryAccessForPeopleKey");
            String jsonStr = JSONObject.fromObject(paramMap).toString();
            logger.info("queryTicketOrder Begin: param is " + jsonStr);

            requestParam.setData(jsonStr);
            requestParam.setMethod(RestMethod.POST);

            ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
            result = responseResult.getReturnStr();// 获取返回的数据
            JSONObject responseJson = JSONObject.fromObject(result);
            if((Boolean)responseJson.get("success")){
                JSONObject dataInfo = responseJson.getJSONObject("data");
                if(null != dataInfo && !dataInfo.isEmpty()){
                    JSONArray resultList = dataInfo.getJSONArray("staffPermissionList");
                    if(null != resultList && resultList.size() > 0){

                        for(int i = 0; i < resultList.size(); i++){

                            JSONObject agent = resultList.getJSONObject(i);
                            Integer agentType = JsonUtil.getIntValue(agent, "staffType");
                            if(agentType.intValue() == 22 || agentType.intValue() == 23){
                                String agentName = JsonUtil.getStringValue(agent, "staffName");
                                agentList.add(agentName);
                            }
                        }

                    }
                }
            }
        }
        catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        logger.info("queryTicketOrder End: result is " + result);
        return agentList;
    }
}
	
	
