package com.tuniu.gt.complaint.restful;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class RestDataParse {
	private static final Log LOG = LogFactory.getLog(RestDataParse.class);

	/**
	 * 通过客户端传入数据，获取要调用的接口
	 * 
	 * @param data
	 *            客户端数据
	 * @return 接口唯一标示
	 */
	public static String getMethodByClientData(String data) {
		if (null != data && !"".equals(data)) {
			try {
				JSONObject fromObject = JSONObject.fromObject(data);
				Object methodObj = fromObject.get("method");
				if (null != methodObj) {
					return methodObj.toString();
				}
			} catch (Exception e) {
				LOG.error("JSONObject.fromObject(data) error", e);
			}
		}
		return null;
	}

	/**
	 * 获取客户端传入的参数，封装成map
	 * 
	 * @param data
	 *            客户端传入参数
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParamsMpByClientData(String data) {
		if (null != data && !"".equals(data)) {
			try {
				JSONObject fromObject = JSONObject.fromObject(data);
				Object paramsObj = fromObject.get("params");
				if (null != paramsObj) {
					return JSONObject.fromObject(paramsObj);
				}
			} catch (Exception e) {
				LOG.error("JSONObject.fromObject(data) error", e);
			}
		}
		return null;
	}
}
