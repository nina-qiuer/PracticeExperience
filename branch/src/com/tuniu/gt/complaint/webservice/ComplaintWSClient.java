package com.tuniu.gt.complaint.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import tuniu.frm.core.webservice.WebServiceClientBase;
import tuniu.frm.service.Constant;

public class ComplaintWSClient {
	
	private static Logger logger = Logger.getLogger(ComplaintWSClient.class);

	private static String supplierURL = Constant.CONFIG.getProperty("SUPPLIER_URL");
	
	
	private static WebServiceClientBase xBase = new WebServiceClientBase();

	
	/**
	 * 调用接口检查供应商是否存在，并返回id
	 */
	public static int checkAgencyInfo(String name) {
		logger.info("checkAgencyInfo Begin: name is " + name);
		int result = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("company_name", name);
		Vector<String> params = new Vector<String>();
		String jsonString = JSONObject.fromObject(paramMap).toString();
		params.add(jsonString);
		Object temp = xBase.xmlrpc(supplierURL, "TuniuBoss.getIdByCompanyName", params);
		logger.info("checkAgencyInfo Ing: ResponseMessage is " + temp);
		if (temp != null) {
			JSONObject jsonObject = JSONObject.fromObject(temp.toString());
			result = jsonObject.getInt("content");
		}
		logger.info("checkAgencyInfo End: name is " + name + ", result is " + result);
		return result;
	}
	
}
