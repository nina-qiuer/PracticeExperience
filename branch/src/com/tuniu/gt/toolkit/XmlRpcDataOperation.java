package com.tuniu.gt.toolkit;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class XmlRpcDataOperation {
	
	private static Logger logger = Logger.getLogger(XmlRpcDataOperation.class);
	/**
	 * 
	 * 此方法描述的是：封装rpc接口参数
	 */
	public static String encodeData(Map<String, Object> paramMap)
			throws Exception {
		String data = XmlRpcDataOperation.encodeParams(paramMap);
		try {
			data = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		JSONObject srcObject = new JSONObject();
		srcObject.accumulate("client_key", "123456");
		srcObject.accumulate("client_id", 5);
		srcObject.accumulate("file", null);
		srcObject.accumulate("method", null);

		JSONObject desObject = new JSONObject();
		desObject.accumulate("client_key", null);
		desObject.accumulate("client_id", null);
		desObject.accumulate("file", null);
		desObject.accumulate("method", null);

		JSONObject jop = new JSONObject();
		jop.accumulate("type", 0);
		jop.accumulate("code", 0);
		jop.accumulate("data", data);
		jop.accumulate("src", srcObject);
		jop.accumulate("des", desObject);
		jop.accumulate("extra_data", null);
		return jop.toString();
	}

	/**
	 * 
	 * 此方法描述的是：封装rpc接口参数
	 */
	public static String encodeData2(Map<String, Object> paramMap)
			throws Exception {
		String data = XmlRpcDataOperation.encodeParams2(paramMap);
		try {
			data = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		JSONObject srcObject = new JSONObject();
		srcObject.accumulate("client_key", "123456");
		srcObject.accumulate("client_id", 5);
		srcObject.accumulate("file", null);
		srcObject.accumulate("method", null);

		JSONObject desObject = new JSONObject();
		desObject.accumulate("client_key", null);
		desObject.accumulate("client_id", null);
		desObject.accumulate("file", null);
		desObject.accumulate("method", null);

		JSONObject jop = new JSONObject();
		jop.accumulate("type", 0);
		jop.accumulate("code", 0);
		jop.accumulate("data", data);
		jop.accumulate("src", srcObject);
		jop.accumulate("des", desObject);
		jop.accumulate("extra_data", null);
		return jop.toString();
	}

	/**
	 * 
	 * 此方法描述的是：格式化map
	 */
	@SuppressWarnings("unchecked")
	private static String encodeParams(Map<String, Object> paramMap)
			throws Exception {
		int paramMapSize = paramMap.size();
		StringBuffer sbf = new StringBuffer();
		try {
			if (paramMapSize != 0) {
				sbf.append("a:" + paramMapSize + ":{");
				Set<String> keySet = new TreeSet<String>();
				keySet = paramMap.keySet();
				for (String key : keySet) {
					Object value = paramMap.get(key);
					sbf.append("s:" + key.length() + ":\"" + key + "\";");
					if (value instanceof Integer) {
						sbf.append("i:" + value + ";");
					} else if (value instanceof Map<?, ?>) {
						Map<String, Object> tMap = (Map<String, Object>) value;
						sbf.append(encodeParams(tMap));
					} else if (value instanceof List<?>) {
						sbf.append("a:" + ((List<?>) value).size() + ":{");
						for (int i = 0; i < ((List<?>) value).size(); i++) {
							Object subValue = ((List<?>) value).get(i);
							sbf.append("i:" + i + ";");
							if (subValue instanceof Integer) {
								sbf.append("i:" + subValue + ";");
							} else if (subValue instanceof String) {
								if (subValue == null)
									subValue = "";
								sbf.append("s:"
										+ subValue.toString().getBytes("UTF-8").length
										+ ":\"" + subValue + "\";");
							}
						}
						sbf.append("}");
					} else {
						if (value == null) {
							value = "";
						}
						sbf.append("s:"
								+ value.toString().getBytes("UTF-8").length
								+ ":\"" + value + "\";");
					}
				}
				sbf.append("}");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sbf.toString();
	}

	/**
	 * 
	 * 此方法描述的是：格式化 复杂的map psk
	 */
	@SuppressWarnings("unchecked")
	private static String encodeParams2(Map<String, Object> paramMap)
			throws Exception {
		int paramMapSize = paramMap.size();
		StringBuffer sbf = new StringBuffer();
		try {
			if (paramMapSize != 0) {
				sbf.append("a:" + paramMapSize + ":{");
				Set<String> keySet = new TreeSet<String>();
				keySet = paramMap.keySet();
				for (String key : keySet) {
					Object value = paramMap.get(key);
					sbf.append("s:" + key.length() + ":\"" + key + "\";");
					if (value instanceof Integer) {
						sbf.append("i:" + value + ";");
					} else if (value instanceof Map<?, ?>) {
						Map<String, Object> tMap = (Map<String, Object>) value;
						sbf.append(encodeParams(tMap));
					} else if (value instanceof List<?>) {
						sbf.append("a:" + ((List<?>) value).size() + ":{");
						for (int i = 0; i < ((List<?>) value).size(); i++) {
							Object subValue = ((List<?>) value).get(i);
							sbf.append("i:" + i + ";");
							if (subValue instanceof Integer) {
								sbf.append("i:" + subValue + ";");
							} else if (subValue instanceof String) {
								if (subValue == null)
									subValue = "";
								sbf.append("s:"
										+ subValue.toString().getBytes("UTF-8").length
										+ ":\"" + subValue + "\";");
							} else if (subValue instanceof Map<?, ?>) {
								Map<String, Object> tMap = (Map<String, Object>) subValue;
								sbf.append(encodeParams(tMap));
							}
						}
						sbf.append("}");
					} else {
						if (value == null) {
							value = "";
						}
						sbf.append("s:"
								+ value.toString().getBytes("UTF-8").length
								+ ":\"" + value + "\";");
					}
				}
				sbf.append("}");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sbf.toString();
	}

	/**
	 * 解码接口返回结果
	 * 
	 * @param params
	 * @return
	 */
	public static Object decodeData(Object params) {
		JSONObject json = JSONObject.fromObject(params);
		String dataStr = json.getString("data");
		Object obj = null;
		try {
			dataStr = URLDecoder.decode(dataStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			obj = PHPSerializer.unserialize(dataStr.getBytes("utf-8"), "utf-8");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

		return obj;
	}
}