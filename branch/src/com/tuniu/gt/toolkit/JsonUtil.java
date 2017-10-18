package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.vo.JsonResult;

import net.sf.json.JSONObject;

public class JsonUtil {

	public static String getStringValue(JSONObject jo, String key) {
		String str = "";
		if (jo.has(key)) {
			str = jo.getString(key);
			if (null == str || "null".equals(str)) {
				str = "";
			}
		}
		return str;
	}

	public static int getIntValue(JSONObject jo, String key) {
		int value = 0;
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = Integer.valueOf(str);
			}
		}
		return value;
	}

	public static double getDoubleValue(JSONObject jo, String key) {
		double value = 0;
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = Double.valueOf(str);
			}
		}
		return value;
	}

	public static long getLongValue(JSONObject jo, String key) {
		long value = 0;
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = Long.valueOf(str);
			}
		}
		return value;
	}

	public static float getFloatValue(JSONObject jo, String key) {
		float value = 0;
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = Float.valueOf(str);
			}
		}
		return value;
	}
	
	public static BigDecimal getBigDecimalValue(JSONObject jo, String key) {
		BigDecimal value = new BigDecimal(0);
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				value = new BigDecimal(str);
			}
		}
		return value;
	}

	public static Date getDateValue(JSONObject jo, String key, String pattern) {
		Date date = DateUtil.parseDate("0000-00-00");
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				date = DateUtil.parseDate(str, pattern);
			}
		}
		return date;
	}

	public static java.sql.Date getSqlDateValue(JSONObject jo, String key, String pattern) {
		java.sql.Date date = DateUtil.parseSqlDate("0000-00-00", "yyyy-MM-dd");
		if (jo.has(key)) {
			String str = jo.getString(key);
			if (null != str && !"".equals(str) && !"null".equals(str)) {
				date = DateUtil.parseSqlDate(str, pattern);
			}
		}
		return date;
	}

	public static boolean getBooleanValue(JSONObject jo, String key) {
		boolean value = false;
		if (jo.has(key)) {
			value = jo.getBoolean(key);

		}
		return value;
	}

	public static void writeResponse(Object obj) {
		HandlerResult result = new HandlerResult();
		result.setRetObj(obj);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(JSONObject.fromObject(result));
		} catch (IOException e) {

		} finally {
			out.flush();
			out.close();
		}
	}

	public static void writeListResponse(Object resultList) {
		JsonResult result = new JsonResult();
		result.setRetCode("success");
		result.setResultList(resultList);
		wirteResponse(result);
	}

	public static void writeListResponse(Object resultList, Integer resultCount, Integer pageNum) {
		JsonResult result = new JsonResult();
		result.setRetCode("success");
		result.setResultList(resultList);
		result.setResultCount(resultCount);
		result.setPageNum(pageNum);
		wirteResponse(result);
	}

	public static void writeErrorResponse() {
		JsonResult result = new JsonResult();
		result.setRetCode("error");
		wirteResponse(result);
	}
	
	private static void wirteResponse(JsonResult result) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(JSONObject.fromObject(result));
		} catch (IOException e) {

		} finally {
			out.flush();
			out.close();
		}
	}
}
