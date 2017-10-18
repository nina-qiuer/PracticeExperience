package com.tuniu.qms.access.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.JsonUtil;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;

public class CrmClient {


	private static String crmURL = Config.get("crm.url");
	private static String crmIvrURL = Config.get("crm_ivr.url");
	
	
	private final static Logger logger = LoggerFactory.getLogger(CrmClient.class);
	/**
	 * 获取会员星级
	 * @param custId
	 * @return
	 */
	public  static  String getCustLevel(Integer ordId){
		logger.info("getCustLevel Bgn: orderId is " + ordId);
		String custLevel = "";
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", ordId);
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("func", "queryCustLevel");
			map.put("params", params);
			String url = crmURL + "/restful/cust-level";
			RestClient rc = new RestClient(url, "GET", JSON.toJSON(map));
			logger.info("getCustLevel Ing: requestMsg is " + JSON.toJSON(map));
			ResponseDto result = rc.execute2();
			logger.info("getCustLevel Ing: responseMsg is " + JSON.toJSONString(result));
			if (null!=result && result.isSuccess()) {
				JSONObject data = (JSONObject) result.getData();
				custLevel =  JsonUtil.getStringValue(data, "custLevelName");
			}
		} catch (Exception e) {
			
			logger.error("getCustLevel Ing: responseMsg is error" + e.getMessage());
		}
		return custLevel;
		
	}
	
	/**
	 * 获得通话次数
	 * @param rv
	 * @return
	 */
	public  static  int  getIVRCount(PreSaleReturnVisit rv){
	
		int  count = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime",DateUtil.formatAsDefaultDateTime(rv.getOrderTime()));
		params.put("endTime", DateUtil.formatAsDefaultDate(rv.getSignDate())+" 23:59:59");
		params.put("salerPhone",rv.getExtension());
		
		if(null != rv.getAllPersonPhone() && !("").equals(rv.getAllPersonPhone())){
			String[] peoplePhones = rv.getAllPersonPhone().split(",");
			
			for(String phone : peoplePhones){
				params.put("tel",  phone);
				
				count += getIVRCountByCondition(params);
			}
		}else{
			params.put("tel",  rv.getTel());
			
			count = getIVRCountByCondition(params);
		}
			
		return count;
		
	}
	
	/**
	 * 执行接口
	 * @param params
	 * @return
	 */
	private static int getIVRCountByCondition(Map<String, Object> params) {
		int count = 0;
		try {
			logger.info("getIVRDetail: requestMsg is " + JSON.toJSON(params));
			
			RestClient rc = new RestClient(crmIvrURL, "POST", JSON.toJSON(params));
		    String  execute = rc.execute();
		    JSONObject result = JSONObject.parseObject(execute);
			logger.info("getIVRDetail: responseMsg is " + result);
			
			boolean isSucc = result.getBoolean("success");
			if (isSucc) {
				count = JsonUtil.getIntValue(result, "num");
			}
		} catch (Exception e) {
			
			logger.error("getIVRDetail: responseMsg is error" + e.getMessage());
		}
		
		return count;
	}

}
