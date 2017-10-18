package com.tuniu.qms.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.operation.platform.tsg.base.rest.RestException;
import com.tuniu.operation.platform.tsg.base.rest.RestMethod;
import com.tuniu.operation.platform.tsg.client.caller.rest.RequestParam;
import com.tuniu.operation.platform.tsg.client.caller.rest.ResponseResult;
import com.tuniu.operation.platform.tsg.client.exception.NoServiceAvailableException;
import com.tuniu.operation.platform.tsg.client.exception.ServiceForbidenException;
import com.tuniu.operation.platform.tsg.client.proxy.CommonClient;
import com.tuniu.qms.access.client.PgaClient;
import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.OrderBillAgency;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.JsonUtil;
import com.tuniu.qms.common.util.Rtx;
import com.tuniu.qms.common.util.StaxonUtils;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.model.MessageParamEntity;

@Service
public class TspServiceImpl implements TspService {
	
	@Resource
	private CommonClient tspCommonClient;

	@Autowired
	private UserService userService;
	
    @Autowired
    private DepartmentService  depService;
    
    @Autowired
    private MailTaskService mailTaskService;
    
	private final static Logger logger = LoggerFactory.getLogger(TspServiceImpl.class);
	
	@Override
	public Product getProductInfo(Integer prdId) {
		Product prd = new Product();
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("BOH.NM.ProductController.queryCommonProductInfos"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦

			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> productIds = new ArrayList<Map<String, Object>>();
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("productId", prdId);
			productIds.add(pmap);
			map.put("productQueryVo", productIds); // 产品ids
			map.put("requestSourceCode", 0); // 请求来源1=电话 2=网站3=无线，投诉系统传0
			map.put("marketChannelCode", 100); // (Integer)渠道编号，可固定100
			String requestMsg = JSON.toJSONString(map);
			logger.info("BOH.NM.ProductController.queryCommonProductInfos send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("BOH.NM.ProductController.queryCommonProductInfos result is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONArray dataArr = jsonObj.getJSONArray("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					JSONObject prdObj = dataArr.getJSONObject(0);
					if (null != prdObj && !prdObj.isEmpty()) {
						prd.setId( JsonUtil.getIntValue(prdObj, "productId"));
						prd.setPrdName(JsonUtil.getStringValue(prdObj, "productName") );
						prd.setCateId(JsonUtil.getIntValue(prdObj, "classBrandParentId"));//一级品类ID
						prd.setCateName(JsonUtil.getStringValue(prdObj, "classBrandParentName"));//一级品类名称
						if("".equals(prd.getCateName())){ //如果品类名称为空
							
							String cateName = CommonUtil.getProductTypeName(prd.getCateId());
							if("".equals(cateName)){
								
								cateName = CommonUtil.getProductTypeById(JsonUtil.getIntValue(prdObj, "productType"));
							}
							prd.setCateName(cateName);
						}
						prd.setSubCateId(JsonUtil.getIntValue(prdObj, "classBrandId") );//二级品类ID
						prd.setSubCateName(JsonUtil.getStringValue(prdObj, "classBrandName"));//二级品类名称
						prd.setBrandId(JsonUtil.getIntValue(prdObj, "brandId"));
						prd.setBrandName(JsonUtil.getStringValue(prdObj, "brandName"));
						prd.setPrdLineDestName(JsonUtil.getStringValue(prdObj, "productNewLineDestName"));//新产品线目的地
						Integer destCateId = JsonUtil.getIntValue(prdObj, "productNewLineTypeId");//新产品线类型
						prd.setDestCateId(destCateId);
						prd.setDestCateName(CommonUtil.getDestCateName(destCateId));
						prd.setPrdManagerId(JsonUtil.getIntValue(prdObj, "managerId") );
						prd.setPrdManager(JsonUtil.getStringValue(prdObj, "managerName"));
						prd.setProducterId(JsonUtil.getIntValue(prdObj, "ownerId") );
						prd.setProducter(JsonUtil.getStringValue(prdObj, "ownerName") );
						
						JSONObject depInfo = prdObj.getJSONObject("departmentInfo");
						if(null!= depInfo){
							
							prd.setProductLineId(JsonUtil.getIntValue(depInfo, "productLineId") );
							prd.setBusinessUnitId(JsonUtil.getIntValue(depInfo, "businessUnitId") );
							prd.setBusinessUnitName(JsonUtil.getStringValue(depInfo, "businessUnitName") );
							prd.setPrdDepId(JsonUtil.getIntValue(depInfo, "departmentId"));
							prd.setPrdDepName(JsonUtil.getStringValue(depInfo, "departmentName") );
							prd.setPrdTeamId(JsonUtil.getIntValue(depInfo, "groupId") );
							prd.setPrdTeamName(JsonUtil.getStringValue(depInfo, "groupName") );
						}else{
							
							prd.setProductLineId(0 );
							prd.setBusinessUnitId(0);
							prd.setBusinessUnitName("");
							prd.setPrdDepId(0);
							prd.setPrdDepName("");
							prd.setPrdTeamId(0 );
							prd.setPrdTeamName("");
							
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prd;
	}

	/**
	 * 通过tsp调用供应商接口 查询供应商数据
	 */
	public Map<String, Object> getAgencyInfo(String agencyName) {
		
		Map<String , Object> resultMap= new HashMap<String, Object>();
		resultMap.put("flag", Constant.NO);
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("VND.ams.AgencyController.searchAgencyList"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sourceSystem", QcConstant.SYSTEM_CODE);//系统码
			map.put("companyName", agencyName);//供应商名称
			map.put("start", 0);//开始
			map.put("limit", 1);//结束
			String requestMsg = JSON.toJSONString(map);
			logger.info("VND.ams.AgencyController.searchAgencyList send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("VND.ams.AgencyController.searchAgencyList return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
		
			if ((Boolean) jsonObj.get("success")) {
				JSONObject dataArr = jsonObj.getJSONObject("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					JSONArray rows = dataArr.getJSONArray("rows");
					if (null != rows && !rows.isEmpty()) {
						JSONObject agency = rows.getJSONObject(0);
						if (null != agency && !agency.isEmpty()) {
							String agencyId = JsonUtil.getStringValue(agency, "agencyId");
							resultMap.put("agencyId", agencyId);
							resultMap.put("flag", Constant.YES);
				}
			  }
			 }
			}
		} catch (Exception e){
			
			e.printStackTrace();
		}
		return resultMap;
		
	}

	/**
	 * 获取导游信息
	 */
	public Map<String, Object> getGuideInfo(Integer orderId) {
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
			String requestMsg = JSON.toJSONString(map);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONObject fmtData = jsonObj.getJSONObject("data");
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
			
			logger.error("TOR.NM.TeamGuideController.batchQueryGroupAndGuideDetail error is " + e.getMessage());
			e.printStackTrace();
		}
		return guideInfo;
	}

	/**
	 * 需要获取导游信息的pag数据 
	 */
	public OrderBill getOrderInfo(Integer ordId) {
		
		OrderBill ord = PgaClient.getOrderInfo(ordId);
		try {
			
			if(null!=ord.getAngencies() && ord.getAngencies().size()>0){
				
				for(OrderBillAgency agency : ord.getAngencies()){
					
					Map<String, Object> map = queryAgencyDetail(agency.getAgencyId());
					agency.setAgencyAccessTime(map.get("agencyAccessTime")==null?"":String.valueOf(map.get("agencyAccessTime")));
					agency.setSignCompany(map.get("signCompany")==null?"":String.valueOf(map.get("signCompany")));
					String agencyOwnerName = String.valueOf(map.get("agencyOwner"));
					if(agencyOwnerName.length()>0){
						
						agency.setAgencyOwner(agencyOwnerName.substring(0,agencyOwnerName.length()-1));
					}else{
						
						agency.setAgencyOwner(agencyOwnerName);
					}
					
				}
				
			}
			
		} catch (Exception e) {
			
			logger.error("get agencyOwner fail" + e.getMessage());
		}
	
		
		try {
			
			//查询导游信息
			if (null != ord ) {
				
				Map<String, Object> guideInfo = getGuideInfo(ordId);
		    	if (!guideInfo.isEmpty()) {
		    			
		    		String guideId = (String) guideInfo.get("guideId");
		    		String guideName = (String) guideInfo.get("guideName");
		    		String guideCall = (String) guideInfo.get("guideCall");
		    		ord.setGuideId(guideId);
		    		ord.setGuideName(guideName);
		    		ord.setGuideCall(guideCall);
		    	}
			}
			
		} catch (Exception e) {
		
			logger.error("get guide fail" + e.getMessage());
		}
	
		
		try {
			
			if (null != ord ) {
				
				
				if(null!=ord.getSalerId()&&ord.getSalerId()>0){
		    		
		    	      User user = userService.get(ord.getSalerId());
		    		  if(null!=user){
		    			  
		    		    Department dep = depService.get(user.getDepId());
					   	if(null!=dep && dep.getPid()>0){
				       		
					   	    ord.setSalerTeam(dep.getName());
					       	Department tdep = depService.get(dep.getPid());
					       	if(null!=tdep && tdep.getPid()>0){
					       		
					       		ord.setSalerDep(tdep.getName());
					        	Department fdep = depService.get(tdep.getPid());
					        	if(null!=fdep && fdep.getPid()>0){
		  				   			
					        		ord.setSalerBusinessUnit(fdep.getName());
		  				   		}
					       	}
					   		
				       	}
		    			  
		    		  }
		    	}
				
			}
			
		} catch (Exception e) {
			
			logger.error("get saler fail " + e.getMessage());
		}
				
		ord.setGroundPrice(0.0);
		
		return ord;
	}
	
	
	public Double getTotalPrice(Integer ordId) {
		
		Double totalPrice = 0.0;
		try {
			
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("ICS.purchasepay.PurchaseController.queryPurchaseListByOrderIds"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			Map<String, Object> map = new HashMap<String, Object>();
			List<Integer> orderIdList = new ArrayList<Integer>();
			orderIdList.add(ordId);
			map.put("sourceSystem", "QMS"); 
			map.put("orderIds", orderIdList); 
			String requestMsg = JSON.toJSONString(map);
			logger.info("ICS.purchasepay.PurchaseController.queryPurchaseListByOrderIds request:"+ requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("ICS.purchasepay.PurchaseController.queryPurchaseListByOrderIds response:"+ retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONObject dataArr = jsonObj.getJSONObject("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					JSONArray list = dataArr.getJSONArray("rows");
					for (int i=0; i<list.size(); i++) {
						JSONObject object = list.getJSONObject(i);
						Double price = JsonUtil.getDoubleValue(object, "totalPrice");
						Integer resourceType = JsonUtil.getIntValue(object, "resourceTypeFl");
						if(resourceType == Constant.RESOURCE_TYPE){//机票+地接产品
							
							totalPrice+=price;
						}
						
					}
				}
			}
		} catch (Exception e) {
			
			logger.error("get groundPrice fail " + e.getMessage());
			e.printStackTrace();
		}
		return totalPrice;
	}
	
	@Override
	public PrdLineDepInfoDto getPrdLineDepInfo(Integer prdLineId) {
		List<Integer> prdLineIds = new ArrayList<Integer>();
		prdLineIds.add(prdLineId);
		List<PrdLineDepInfoDto> prdLineDepInfos = getPrdLineDepInfos(prdLineIds);
		if (!prdLineDepInfos.isEmpty()) {
			return prdLineDepInfos.get(0);
		}
		return null;
	}

	@Override
	public List<PrdLineDepInfoDto> getPrdLineDepInfos(List<Integer> prdLineIds) {
		List<PrdLineDepInfoDto> prdLineDepInfos = new ArrayList<PrdLineDepInfoDto>();
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("TBS.ProductLine.ProductLineController.queryManager");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("productLineIds", prdLineIds);
			String requestMsg = JSON.toJSONString(paraMap);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONArray data = jsonObj.getJSONArray("data");
				if (null != data) {
					for (int i=0; i<data.size(); i++) {
						JSONObject depInfoObj = data.getJSONObject(i);
						PrdLineDepInfoDto depInfo = new PrdLineDepInfoDto();
						depInfo.setPrdLineId(JsonUtil.getIntValue(depInfoObj, "productLineId"));
						depInfo.setBusinessUnitId(JsonUtil.getIntValue(depInfoObj, "businessUnitId"));
						depInfo.setBusinessUnitName(JsonUtil.getStringValue(depInfoObj, "businessUnitName"));
						depInfo.setPrdDepId(JsonUtil.getIntValue(depInfoObj, "departmentId"));
						depInfo.setPrdDepName(JsonUtil.getStringValue(depInfoObj, "departmentName"));
						depInfo.setPrdTeamId(JsonUtil.getIntValue(depInfoObj, "groupId"));
						depInfo.setPrdTeamName(JsonUtil.getStringValue(depInfoObj, "groupName"));
						depInfo.setPrdManagerId(JsonUtil.getIntValue(depInfoObj, "managerId"));
						depInfo.setPrdManager(JsonUtil.getStringValue(depInfoObj, "managerName"));
						prdLineDepInfos.add(depInfo);
					}
				}
			}			
		} catch (Exception e) {
			logger.error("TBS.ProductLine.ProductLineController.queryManager error is " + e.getMessage());
			e.printStackTrace();
		}
		return prdLineDepInfos;
	}

	@Override
	public boolean changePrdStatus(Map<String, Object> paramMap) {
		
	   boolean success = false;
	   try {
	        RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("PCS.BFM.ProductController.changeProductStatus");
			String jsonStr = JSON.toJSONString(paramMap);
	        logger.info("changePrdStatus Begin: param is " + jsonStr);
	        requestParam.setData(jsonStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			//超时时间
			requestParam.setConnectTimeout(6);
			requestParam.setSocketTimeout(6);
	        
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
	        logger.info("changePrdStatus resultMsg is:" + retStr);
	    	JSONObject jsonObj = JSON.parseObject(retStr);
	    	if ((Boolean) jsonObj.get("success")) {
	    		
	                success = true;
	        }else{
	        	String msg = (String)jsonObj.get("msg");
	        	logger.error("上下线失败，错误信息,", msg);
	        }
	        
	       } catch(Exception e) {
	    	   
	            logger.error("changePrdStatus error,",e);
	       }
	        
	        return success;
	}

	@Override
	public Integer queryMainRouteId(int routeId) {
		
		logger.info("queryMainRouteId Bgn: routeId is " + routeId);
		int mainRouteId = routeId;
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("BOH.NM.ProductController.queryMainRouteId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("routeId", routeId);
			String jsonStr = JSON.toJSONString(map);
		    requestParam.setData(jsonStr);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("queryMainRouteId return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				JSONArray data = jsonObj.getJSONArray("data");
				if (null != data && !data.isEmpty()) {
					for (int i=0; i<data.size(); i++) {
						JSONObject depInfoObj = data.getJSONObject(i);
						if (JsonUtil.getIntValue(depInfoObj, "isMainRoute") == 1) {
							mainRouteId = JsonUtil.getIntValue(depInfoObj,"routeId");
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("查询BOH接口出错",e);
		}

		return mainRouteId;
	}

	@Override
	public boolean sendMail(MailTask mail) {
		
		boolean sendResult = false;
		logger.info("sendMail Bgn:  " + mail.getId());
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("PLA.EMAIL.EdmController.sendInternalEmail");
			Map<String, Object> map = getSemdMailName(mail);
			String jsonStr = JSON.toJSONString(map);
			logger.info("sendMail detail is " + jsonStr);
		    requestParam.setData(jsonStr);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("sendMail return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
				sendResult = true;
				/*String msg = JsonUtil.getStringValue(jsonObj, "msg");
				if(msg.contains(QcConstant.SEND_TYPE) ){*/
				/*}*/
			}
		} catch (Exception e) {
			logger.error("发送邮件报错",e);
		}
		return sendResult;
	}

	
	/**
	 * 发送rtx提醒
	 */
	public boolean sendRtx(Rtx rtx) {
		
		boolean sendResult = false;
		logger.info("sendRtx Bgn:  " + rtx.getUids());
		try {
			RequestParam requestParam = new RequestParam();
			// 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
			requestParam.setServiceName("PLA.RTX.RtxController.send");
			String jsonStr =  JSON.toJSON(rtx).toString();
		    requestParam.setData(jsonStr);
			requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("sendRtx return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
			if ((Boolean) jsonObj.get("success")) {
					
				sendResult = true;
			}
		} catch (Exception e) {
			
			logger.error("发送RTX报错",e);
		}
		return sendResult;
	}
	
	private Map<String, Object> getSemdMailName(MailTask mail){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("systemCode", QcConstant.SYSTEM_CODE);//系统三字码
		map.put("businessId", QcConstant.BUSINESS_ID);//业务ID
		map.put("senderName", "robot@tuniu.com");//发件人
		map.put("subject", mail.getSubject()==null?"":mail.getSubject());//邮件主题
		List<String> list = new ArrayList<String>();
		List<String> cclist = new ArrayList<String>();
		
		if (!"prd".equals(Config.get("env.name"))) { // 非生产环境，将邮件发送给测试人员
			
			list.add("chenyu8@tuniu.com");
			list.add("zhangsensen@tuniu.com");
			list.add("liwang2@tuniu.com");
			cclist.add("liubing@tuniu.com");
			StringBuilder sb = new StringBuilder();
			sb.append("收件人：").append(Arrays.toString(mail.getReAddrs())).append("<br>");
			sb.append("抄送人：").append(Arrays.toString(mail.getCcAddrs())).append("<br>");
			sb.append(mail.getContent());
			mail.setContent(sb.toString());
			map.put("recipientEmails", list);
			map.put("ccEmails",cclist );
			map.put("msgText", mail.getContent());//邮件内容
			
		}else{
		
			map.put("recipientEmails", mail.getReAddrs());
			map.put("ccEmails",mail.getCcAddrs()==null?new ArrayList<String>():mail.getCcAddrs());
			map.put("msgText", mail.getContent()==null?"":mail.getContent());
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryAgencyDetail(Integer agencyId) {
	
		Map<String , Object> resultMap= new HashMap<String, Object>();
		try {
			RequestParam requestParam = new RequestParam();
			requestParam.setServiceName("VND.ams.AgencyController.agencyDetail"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sourceSystem", QcConstant.SYSTEM_CODE);//系统码
			map.put("agencyId", agencyId);//供应商ID 
			String requestMsg = JSON.toJSONString(map);
			logger.info("VND.ams.AgencyController.agencyDetail send is " + requestMsg);
			requestParam.setData(requestMsg);
			requestParam.setMethod(RestMethod.GET);// 可以不填，不填按照服务中要求的方法去请求
			// RequestParam 还有很多属性可以设置这里就不一一列举了
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			String retStr = result.getReturnStr();// 获取返回的数据
			logger.info("VND.ams.AgencyController.agencyDetail return is " + retStr);
			JSONObject jsonObj = JSON.parseObject(retStr);
		    String agencyOwnerName= "";
			if ((Boolean) jsonObj.get("success")) {
				JSONObject dataArr = jsonObj.getJSONObject("data");
				if (null != dataArr && !dataArr.isEmpty()) {
					
					String signCompany = JsonUtil.getStringValue(dataArr, "fullName");//签约公司
					String agencyAccessTime = JsonUtil.getStringValue(dataArr, "admitDate");//供应商准入时间
					resultMap.put("signCompany", signCompany);
					resultMap.put("agencyAccessTime", agencyAccessTime);
					JSONArray agencyOwners = dataArr.getJSONArray("agencyOwnerList");
					if (null != agencyOwners && !agencyOwners.isEmpty()) {
						for (int i=0; i<agencyOwners.size(); i++) {
							
							JSONObject object = agencyOwners.getJSONObject(i);
							agencyOwnerName += JsonUtil.getStringValue(object, "agencyOwnerName")+",";//供应商名称
							
				   }
				}
			  }
			}
			resultMap.put("agencyOwner", agencyOwnerName);
			
		} catch (Exception e){
			
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 发送短信接口
	 */
	public boolean sendMessage(MessageParamEntity mpe) {

		   boolean success = false;
	        logger.info("PLA.SMS.SmsController.sendSms Begin: param is " + JSON.toJSON(mpe));
	        try {
	        	
	        	String requestMsg = JSON.toJSONString(mpe);
	        	RequestParam requestParam = new RequestParam();
				requestParam.setServiceName("PLA.SMS.SmsController.sendSms"); // 服务名需在xml中consumerConfig中配置好, 否则会报错的哦
				requestParam.setData(requestMsg);
				requestParam.setMethod(RestMethod.POST);// 可以不填，不填按照服务中要求的方法去请求
				// RequestParam 还有很多属性可以设置这里就不一一列举了
				ResponseResult result = tspCommonClient.responseCaller(requestParam);
				String retStr = result.getReturnStr();// 获取返回的数据
				logger.info("PLA.SMS.SmsController.sendSms return is " + retStr);
				JSONObject jsonObj = JSON.parseObject(retStr);
				if ((Boolean) jsonObj.get("success")) {
					 success = true;
				}
	        } catch (Exception e) {
	        	
	            logger.error(e.getMessage(), e);
	        }
	        
	        return success;
	}
	
	/**
	 * 发送投诉改进报告  向OA推送生成流程
	 */
	@Override
	public String sendImproveBill(CmpImprove obj){
		String oaId = "";
		logger.info("send OA Begin: param is " + JSON.toJSON(obj));
		
		try {
			String token = getToken();//获取token取值 、发起表单，需要使用上面获得的token
			
			RequestParam requestParam2 = new RequestParam();
			requestParam2.setServiceName("SOA.SOA.Flow2ResourceController.launchCollaborationForTsp");
			
			Map<String, String> urlParam2 = new HashMap<String, String>();
			urlParam2.put("noa", "flow2/forTsp/tsgjbg001");//业务系统找OA开发获取对应表单的模板编号，投诉改进报告的模板编号：tsgjbg001
			requestParam2.setUrlParam(urlParam2);
			
			Map<String, String> token2 = new HashMap<String, String>();
			token2.put("token", token);
			requestParam2.setHeader(token2);

			Map<String, Object> info1 = new HashMap<String, Object>();
			info1.put("senderLoginName", obj.getImpPersonId().toString());//表单发起人， 业务系统提供老OA（uc）ID
			info1.put("subject", "投诉改进报告");//表单标题，业务系统提供，建议同表单名称
			info1.put("data", getParamData(obj));//业务系统根据具体表单提供json格式的字符串
			info1.put("param", "1");//OA方提供, 为控制是否流程发送。0：缺省值流程发送到下一审批人。1：流程在申请人的待办（草稿）中。投诉改进报告传"1"
			//有附件时才使用该属性，否则不出现该参数
			//1.info1.put("fileColumnName", "columnName1,columnName2");
			//2.getParamData()方法中，出现columnName1,columnName2，value的格式为  url||filename
			//例如columnName1对应的columnvalue1的格式(多个附件以,号分割)为url1||filename1,url2||filename2
			//url一般为公司公共ftp的url，OA会根据url获取文件并重命名为指定的filename
			//投诉改进报告申请单为 
			info1.put("fileColumnName", "上传附件");
			
			JSONObject json1 = new JSONObject(info1);
			requestParam2.setData(json1.toJSONString());
			logger.info("send oa dataJson" + json1.toJSONString());
			
			requestParam2.setBase64(false);//只能是false
			requestParam2.setMethod(RestMethod.POST);//接口方式
			ResponseResult result2 = tspCommonClient.responseCaller(requestParam2);
			
			String retStr2 = result2.getReturnStr();// 获取返回的数据 //表单发起异常情况下,msg字段有中文，需要进行utf-8方式解码
			JSONObject json = (JSONObject) JSON.parse(retStr2);
			logger.info("OA表单返回值：" + retStr2);
			
			boolean success = (Boolean)json.get("success");
			if(success){
				JSONObject dataArr = json.getJSONObject("data");
				oaId = JsonUtil.getStringValue(dataArr, "oaId");
			}else{
				String msg = CommonUtil.decodeUnicode(JsonUtil.getStringValue(json, "msg"));
				logger.info("推送OA失败，错误信息：" + msg);
			}
		} catch (NoServiceAvailableException e) {// 没有服务地址可用 自行log或抛出异常
			logger.error("推送改进报告失败, 没有服务地址可用", e.getMessage(), e);
		} catch (ServiceForbidenException e) {// 服务被禁止调用 自行log或抛出异常
			logger.error("推送改进报告失败, 服务被禁止调用", e.getMessage(), e);
		}catch (RestException e) {
			logger.error("推送改进报告失败", e.getMessage(), e);
		}catch(Exception e){
			logger.error("推送改进报告失败", e.getMessage(), e);
		}
		
		return oaId;
	}

	/**
	 * 新OA必填token内容的获取
	 * @return
	 * @throws RestException
	 * @throws NoServiceAvailableException
	 * @throws ServiceForbidenException
	 */
	private String getToken() throws RestException, NoServiceAvailableException, ServiceForbidenException {
		logger.info("-----token start-----");
		//获取token
		String userName = "test";
		String password = "123456";	
		RequestParam requestParam = new RequestParam();
		// 服务名需在xml中consumerConfig中配置
		requestParam.setServiceName("SOA.SOA.Flow2ResourceController.launchCollaborationForTsp");												
		Map<String, String> urlParam = new HashMap<String, String>();
		urlParam.put("noa", "token/" + userName + "/" + password);
		requestParam.setUrlParam(urlParam);
		requestParam.setBase64(false);
		//根据接口具体的情况设置
		requestParam.setMethod(RestMethod.GET);
		//RequestParam 还有很多属性可以设置这里就不一一列举了
		ResponseResult result = tspCommonClient.responseCaller(requestParam);
		String retStr = result.getReturnStr();// 获取返回的数据
		logger.info("-----retStr-----:" + retStr);
		JSONObject json = (JSONObject) JSON.parse(retStr);
		String token = (String) json.get("id");
		logger.info("-----token end-----" + token);
		return token;
	}
    
	/**
	 * 业务调用方根据具体的表单内容(样式使用testExportFlow())
	 * 组装json字符串，用于发起表单
	 * @param obj 
	 * @return 表单内容对应json字符串
	 */
	private String getParamData(CmpImprove obj) {
		//例子：导游报账dybz001
		/*String str ="<formExport version=\"2.0\">" +
		"<summary id=\"-6635242212263377250\" name=\"formmain_2088\"/>" +
		"<definitions/>" +
		"<values>" +
		"<column name=\"申请单号\"><value></value></column>" +
		"<column name=\"申请人\"><value><![CDATA[-8697266889871507138]]></value></column>" +
		"<column name=\"日期\"><value><![CDATA[2016-05-01]]></value></column>" +
		"</values>" +
		"<subForms/>" +
		"</formExport>";*/
	    String dataStr = mailTaskService.getMailText("improve.ftl", getDataMap(obj));//xml 模板
	    logger.info("xml模板转换：" + dataStr);
		try {
			//使用底层组工具类，xml转json串improvePoint
			dataStr = StaxonUtils.xml2json(dataStr);
		} catch (XMLStreamException e) {
			logger.error("json 转换失败", e.getMessage(), e);
		}
		logger.info("json转换：" + dataStr);
		return dataStr;	
	}
	
	/**
	 * 表单数据封装
	 * @param obj
	 * @return
	 */
	private Map<String, Object> getDataMap(CmpImprove obj) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("impId", obj.getId());
	    dataMap.put("cmpId", obj.getCmpId());
		dataMap.put("ordId", obj.getOrdId());
	    dataMap.put("prdId", obj.getPrdId());
	    dataMap.put("routeName", obj.getRouteName());
	    dataMap.put("agencyName", obj.getAgencyName());
	    dataMap.put("impPersonId", obj.getImpPersonId());
	    dataMap.put("cmpAffair", obj.getCmpAffair());
	    dataMap.put("improvePoint", obj.getImprovePoint());
	    dataMap.put("producter", obj.getProducter());
	    dataMap.put("productLeader", obj.getProductLeader());
	    dataMap.put("productManager", obj.getProductManager());
	    dataMap.put("customer", obj.getCustomer());
	    dataMap.put("customerLeader", obj.getCustomerLeader());
	    dataMap.put("customerManager", obj.getCustomerManager());
	    if(obj.getAttachList() != null && obj.getAttachList().size() > 0){
	    	StringBuilder str = new StringBuilder(""); 
	    	for(Map<String, Object> data : obj.getAttachList()){
	 	    	str.append(data.get("path")).append("||").append(data.get("name")).append(",");
	 	    }
	 	    dataMap.put("attach", str.deleteCharAt(str.length()-1));
	    }else{
	    	dataMap.put("attach", null);
	    }
	    
		return dataMap;
	}
	
	@Override
	public String getOrderDetailPageUrl(Integer orderId) {
		String pageUrl = "";
		
		try{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("orderId", orderId);
			dataMap.put("serviceKey", "Common-OrderDetailPage");
			
			RequestParam requestParam  = new RequestParam();
			requestParam.setServiceName("PGA.NWS.PipelineController.getAdaptedApiConf");
			requestParam.setData(JSON.toJSONString(dataMap));
			logger.info("serviceName: PGA.NWS.PipelineController.getAdaptedApiConf, orderId: " + orderId);
			
			ResponseResult result = tspCommonClient.responseCaller(requestParam);
			
			logger.info("result: " + JSON.toJSONString(result));
			if(null != result && StringUtils.isNotEmpty(result.getReturnStr())){
				JSONObject jsonObj = JSON.parseObject(result.getReturnStr());
				
				if(jsonObj.getBooleanValue("success")){
					JSONObject data = jsonObj.getJSONObject("data");
					
					if(null != data){
						pageUrl = data.getString("outerService");
					}
				}
			}
			
		}catch(Exception e){
			logger.error("查询订单详情页链接失败[{}]",JSONObject.toJSONString(orderId),e);
		}
		
		logger.info("orderUrl :" + pageUrl);
		return pageUrl;
	}
	
}
