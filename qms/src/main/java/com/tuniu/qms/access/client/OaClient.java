package com.tuniu.qms.access.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.DateUtil;

public class OaClient {
	
	private static String serverURL = Config.get("oa.url");
	private static String ucURL = Config.get("uc.url");
	private final static Logger logger = LoggerFactory.getLogger(OaClient.class);

	/** 查询汇报人 */
	public static User getReporter(String realName) {
		User user = new User();
		
		try {
			
			if (StringUtils.isNotBlank(realName)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("subSystem", "crm");
				map.put("key", "254f1f45891bc9ee");
				
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("service", "query_sales_report");
				
				List<Map<String, Object>> condList = new ArrayList<Map<String,Object>>();
				Map<String, Object> condMap = new HashMap<String, Object>();
				condMap.put("type", 2);
				condMap.put("value", realName);
				condList.add(condMap);
				dataMap.put("cond", condList);
				
				map.put("data", dataMap);
				
				RestClient rc = new RestClient(serverURL, "GET", JSON.toJSON(map));
				ResponseDto result = rc.execute2();
				if(result!=null && result.isSuccess()) {
					if (result.getData() instanceof JSONObject) {
						JSONObject data = (JSONObject) result.getData();
						JSONObject leads = data.getJSONObject("leads");
						JSONObject reporter = leads.getJSONObject(realName);
						if (null != reporter && !"null".equals(reporter.toString())) {
							user.setId(reporter.getInteger("salerId"));
							user.setRealName(reporter.getString("name"));
							user.setUserName(reporter.getString("spelling"));
							user.setDepId(reporter.getInteger("dept"));
							user.setJobId(reporter.getInteger("job"));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询报告人失败", e);
		}
		
		return user;
	}
	
	/**
	 * 根据部门id单向向上查询 <部门id,部门负责人>map
	 * 
	 * @param depId 部门id
	 * @return   Map<Integer, Object>   <部门id,部门负责人>
	 */
    public static Map<Integer, String> getUpDirDepManagerByDepId(int depId) {
    	
    	 Map<Integer, String> resultMap = new HashMap<Integer, String>(); // key:部门id，value：部门负责人
    	try {
			
    		  Map<String, Object> map = new HashMap<String, Object>();
    	        map.put("subSystem", "crm");
    	        map.put("key", "254f1f45891bc9ee");

    	        Map<String, Object> dataMap = new HashMap<String, Object>();
    	        dataMap.put("service", "dept_report");
    	        Map<String, Object> needMap = new HashMap<String, Object>();
    	        needMap.put("lead", true);

    	        Map<String, Object> condMap = new HashMap<String, Object>();
    	        condMap.put("type", 1);
    	        condMap.put("value", depId);
    	        dataMap.put("need", needMap);
    	        dataMap.put("cond", condMap);

    	        map.put("data", dataMap);

    	        RestClient rc = new RestClient(serverURL, "GET", JSON.toJSON(map));
    	        ResponseDto result = rc.execute2();
    	       
    	        if(result!=null && result.isSuccess()) {
    	            if(result.getData() instanceof JSONArray) {
    	                JSONArray data = (JSONArray) result.getData();
    	                for(Object depTmp : data) {
    	                    JSONObject depInfo = (JSONObject) depTmp;
    	                    resultMap.put(depInfo.getInteger("deptId"), depInfo.getString("leadName"));
    	                }
    	            }
    	        }
    		
		} catch (Exception e) {
			
			logger.error("根据部门id单向向上查询失败");
			e.printStackTrace();
		}
    	
        return resultMap;
    }
	
    /**
	 * 根据日期查询该天是否是工作日
	 * '0' => '正常工作日','1' => '双休日','2' => '法定节假日'
	 */
    public static int  getDayIsWork(Date date) {
    	
    	String dateTime = DateUtil.formatAsDefaultDate(date);
    	int flag = 9;
    	try {
    		
    		    Map<String, Object> map = new HashMap<String, Object>();
    	        map.put("class", "APIDate");
    	        map.put("func", "isWorkDay");
    	        Map<String, Object> dataMap = new HashMap<String, Object>();
    	        dataMap.put("date", dateTime);
    	        map.put("data", dataMap);
    	        RestClient rc = new RestClient(ucURL, "GET", JSON.toJSON(map));
    	        ResponseDto result = rc.execute2();
    	        logger.info("getDayIsWork result:"+result);
    	        if(result!=null && result.isSuccess()) {
    	        	
    	        	flag = Integer.parseInt((String) result.getData());
    	            
    	        }

		} catch (Exception e) {
			logger.error("getDayIsWork error:"+e.getMessage());
			e.printStackTrace();
		}
      
        return flag;
    }
    
    
	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(getDayIsWork(DateUtil.parseUtilDate("2015-10-04"))));
    	//System.out.println(getUpDirDepManagerByDepId(1369));
	}

}
