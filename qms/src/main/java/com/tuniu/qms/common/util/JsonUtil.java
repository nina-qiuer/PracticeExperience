package com.tuniu.qms.common.util;


import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	
	public static String getStringValue(JSONObject jo, String key) {
		String str = "";
		if (jo.containsKey(key)) {
			str = jo.getString(key);
	    	if (null == str || "null".equals(str)) {
	    		str = "";
	    	}
		}
    	return str;
    }
    
	public static int getIntValue(JSONObject jo, String key) {
		int value = 0;
		if (jo.containsKey(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = Integer.valueOf(str);
			}
		}
		return value;
    }
	
	public static double getDoubleValue(JSONObject jo, String key) {
    	double value = 0;
    	if (jo.containsKey(key)) {
    		String str = jo.getString(key);
    		if (null != str && !"".equals(str) && !"null".equals(str)) {
    			value = Double.valueOf(str);
    		}
    	}
		return value;
    }
	
	public static float getFloatValue(JSONObject jo, String key){
		float value = 0;
    	if (jo.containsKey(key)) {
    		String str = jo.getString(key);
    		if (null != str && !"".equals(str) && !"null".equals(str)) {
    			value = Float.valueOf(str);
    		}
    	}
		return value;
	}
    
}
