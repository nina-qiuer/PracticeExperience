package com.tuniu.gt.complaint.schedule.task;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tuniu.frm.service.Constant;

import com.tuniu.gt.datafix.service.IDataFixService;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionService;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;



/**
 * 定时任务 
 * ct_sign_satisfaction表中增加了新的字段，预订城市，出发城市及目的地，需要修补历史数据
 * @author hanxuliang 2014-5-16
 * 
 */
public class SignSatisfactionDFScheduleSendTask {
	
	private static Logger logger = Logger.getLogger("SignSatisfactionDFScheduleSendTask.class");
	
	@Autowired
	@Qualifier("datafix_service_datafix_impl-datafix")
	private IDataFixService  dataFixService;
	
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfaction")
	private ISignSatisfactionService  signSatisfactionService;
	
	public void startTask() {
		logger.info("------------------------------SignSatisfactionDFScheduleSendTask.startTask() BEGIN----------------------------------------------");
		Integer maxId = dataFixService.getSignSatisfactionDataFixMaxId();
		Map paramMap = new HashMap();
		paramMap.put("maxId", maxId);
		SignSatisfactionEntity entity = dataFixService.getNextSignSatisfactionDataFixId(paramMap);
		if(entity!=null){
			dataFixService.updateMaxId(entity.getId());
			String orderType = entity.getOrderType();
			Integer orderId = entity.getOrderId();
			JSONObject jsonObj = null;
			if("0".equals(orderType)){
				jsonObj = getOrderInfoByOrderIdFromGroup(String.valueOf(orderId));
			}else if("1".equals(orderType)){
				jsonObj = getOrderInfoByOrderIdFromDIY(String.valueOf(orderId));
			}else if("2".equals(orderType)){
				jsonObj = getOrderInfoByOrderIdFromTeam(String.valueOf(orderId));
			}
			if(jsonObj!=null){
				paramMap = new HashMap();
				paramMap = getSignSatisfactionCityInfoMap(jsonObj,orderType);
				paramMap.put("id", entity.getId());
				logger.info("------------------------------SignSatisfactionDFScheduleSendTask.updateCityInfo("+entity.getId()+") ----------------------------------------------");
				dataFixService.updateCityInfo(paramMap);
			}
		}
		logger.info("------------------------------SignSatisfactionDFScheduleSendTask.startTask() END----------------------------------------------");
	}
	
	private Map getSignSatisfactionCityInfoMap(JSONObject obj, String orderType){
		Map map = new HashMap();
		if("1".equals(orderType)){
			JSONObject requirement = JSONObject.fromObject(obj.getString("requirement"));
			//封装城市信息
			map = setCityInfo(map,requirement);
		}else if("0".equals(orderType)){
			map = setCityInfo(map,obj);
		}else if("2".equals(orderType)){
			map = setCityInfo(map,obj);
		}
		return map;
	}
	
	private Map setCityInfo(Map map, JSONObject obj){
		if(obj.get("bookCityCode")!=null){
			if(obj.get("bookCityCode") instanceof Integer){
				map.put("bookCityCode",obj.getInt("bookCityCode"));
			}else{
				String bookCityCode = obj.getString("bookCityCode").trim();
				if(!bookCityCode.equals("null")){
					try{
						map.put("bookCityCode",Integer.parseInt(bookCityCode));
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		if(obj.get("bookCity")!=null){
			if(obj.get("bookCity") instanceof String){
				map.put("bookCity",obj.getString("bookCity").trim());
			}
		}
		if(obj.get("startCityCode")!=null){
			if(obj.get("startCityCode") instanceof Integer){
				map.put("startCityCode",obj.getInt("startCityCode"));
			}else{
				String startCityCode = obj.getString("startCityCode").trim();
				if(!startCityCode.equals("null")){
					try{
						map.put("startCityCode",Integer.parseInt(startCityCode));
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		if(obj.get("startCity")!=null){
			if(obj.get("startCity") instanceof String){
				map.put("startCity",obj.getString("startCity").trim());
			}
		}
		if(obj.get("desCityCode")!=null){
			if(obj.get("desCityCode") instanceof Integer){
				map.put("desCityCode",obj.getInt("desCityCode"));
			}else{
				String desCityCode = obj.getString("desCityCode").trim();
				if(!desCityCode.equals("null")){
					try{
						map.put("desCityCode",Integer.parseInt(desCityCode));
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		if(obj.get("desCity")!=null){
			if(obj.get("desCity") instanceof String){
				map.put("desCity",obj.getString("desCity").trim());
			}
		}
		return map;
	}
	
	/**
	 * 通过订单id获取跟团接口订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public JSONObject getOrderInfoByOrderIdFromGroup(String orderId) {
		JSONObject result = null;
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
			JSONObject jsonObject = JSONObject.fromObject(execute);
			Boolean isSuccess = jsonObject.getBoolean("success");
			if(isSuccess){
				result = jsonObject.getJSONObject("data");
			}else{
				logger.info("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromGroup() failed !----"+execute+"---------------");
			}
			
		} catch (TRestException e) {
			logger.error("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromGroup() TRestException !---------------",e);
		}
		return result;
	}
	
	/**
	 * 通过订单id获取自助游接口订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public JSONObject getOrderInfoByOrderIdFromDIY(String orderId) {
		JSONObject result = null;
		String url = Constant.CONFIG.getProperty("ORDER_INFO_DIY");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		Map<String, String> routeMp = new HashMap<String, String>();
		routeMp.put("orderId", orderId);
		logger.info(JSONObject.fromObject(routeMp).toString());
		trestClient.setData(JSONObject.fromObject(routeMp).toString());

		try {
			String execute = trestClient.execute();
			JSONObject jsonObject = JSONObject.fromObject(execute);
			Boolean isSuccess = jsonObject.getBoolean("success");
			if(isSuccess){
				result = jsonObject.getJSONObject("data");
			}else{
				logger.info("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromDIY() failed !----"+execute+"---------------");
			}
		} catch (TRestException e) {
			logger.error("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromDIY() TRestException !---------------",e);
		}
		return result;
	}
	
	/**
	 * 通过订单id获取团队接口订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public JSONObject getOrderInfoByOrderIdFromTeam(String orderId) {
		JSONObject result = null;
		String url = Constant.CONFIG.getProperty("ORDER_INFO_TEAM");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		Map<String, String> routeMp = new HashMap<String, String>();
		routeMp.put("orderId", orderId);
		logger.info(JSONObject.fromObject(routeMp).toString());
		trestClient.setData(JSONObject.fromObject(routeMp).toString());

		try {
			String execute = trestClient.execute();
			JSONObject jsonObject = JSONObject.fromObject(execute);
			Boolean isSuccess = jsonObject.getBoolean("success");
			if(isSuccess){
				result = jsonObject.getJSONObject("data");
			}else{
				logger.info("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromTeam() failed !----"+execute+"---------------");
			}
		} catch (TRestException e) {
			logger.error("---------------SignSatisfactionDFScheduleSendTask.getOrderInfoByOrderIdFromTeam() TRestException !---------------",e);
		}
		return result;
	}
}
