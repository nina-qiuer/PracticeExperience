package com.tuniu.qms.access.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.util.CommonUtil;

public class RestServer {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public RestServer(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		this.request = request;
		this.response = response;
	}

	public <T> T getRestData(Class<T> clazz) {
		T obj = null;
		String restDataStr = getRestDataStr();
		if (StringUtils.isNotBlank(restDataStr)) {
			obj = JSON.parseObject(restDataStr, clazz);
		}
		return obj;
	}
	
	public String getRestDataStr() {
		String restDataStr = null;
		if (null != request) {
			String method = request.getMethod();
			String base64Str = null;
			if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
				base64Str = request.getQueryString();
			} else {
				base64Str = getBodyData();
			}
			restDataStr = CommonUtil.decodeBase64Str(base64Str);
		}
		return restDataStr;
	}

	private String getBodyData() {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while (null != (line = reader.readLine())) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public void writeResponseDefault() {
		ResponseDto dto = new ResponseDto();
		writeResponse(dto);
	}
	
	public void writeResponse(ResponseDto dto) {
		if (null != response) {
			String jsonStr = JSON.toJSONString(dto);
			String base64Str = CommonUtil.encodeBase64Str(jsonStr);
			try {
				PrintWriter writer = response.getWriter();
	            writer.print(base64Str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeCustomResponse(Map<String, Object> map)  {
		if (null != response) {
			String jsonStr = JSON.toJSONString(map);
			String base64Str = CommonUtil.encodeBase64Str(jsonStr);
			try {
				PrintWriter writer = response.getWriter();
	            writer.print(base64Str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
