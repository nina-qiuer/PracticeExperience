package com.tuniu.qms.access.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.util.CommonUtil;

public class RestClient {
	
	private RequestConfig defaultRequestConfig;
	
    private HttpRequestBase request;
    
    private final static Logger logger = LoggerFactory.getLogger(RestClient.class);
    
    public RestClient(String serverURL, String httpMethod, Object requestData) {
    	defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();
    	request = getRequest(serverURL, httpMethod, requestData);
	}
    
    private HttpRequestBase getRequest(String serverURL, String httpMethod, Object requestData) {
		String base64Str = getBase64Str(requestData);
    	try {
    		if ("GET".equalsIgnoreCase(httpMethod)) {
    			HttpGet httpGet = new HttpGet(getURLWithData(serverURL, base64Str));
    			RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).build();
    			httpGet.setConfig(requestConfig);
    			return httpGet;
    		} else if ("POST".equalsIgnoreCase(httpMethod)) {
    			HttpPost httpPost = new HttpPost(serverURL);
    			if (StringUtils.isNotEmpty(base64Str)) {
    				httpPost.setEntity(new StringEntity(base64Str));
    			}
    			return httpPost;
    		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    private String getBase64Str(Object requestData) {
    	String base64Str = null;
    	if (null != requestData) {
    		String jsonStr = JSON.toJSONString(requestData);
    		base64Str = CommonUtil.encodeBase64Str(jsonStr);
    	}
    	return base64Str;
    }
    
    private String getURLWithData(String serverURL, String base64Str) {
		if (StringUtils.isNotEmpty(base64Str)) {
			if (serverURL.endsWith("?")) {
				return (new StringBuilder(String.valueOf(serverURL))).append(base64Str).toString();
			} else {
				return (new StringBuilder(String.valueOf(serverURL))).append("?").append(base64Str).toString();
			}
		} else {
			return serverURL;
		}
	}
    
    public String execute() {
    	String result = null;
    	CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
			CloseableHttpResponse response = httpClient.execute(request);
			String base64Str = EntityUtils.toString(response.getEntity());
			if(StringUtils.isNotEmpty(base64Str)) {
				logger.info("RestClient result: " + base64Str);
				result = CommonUtil.decodeBase64Str(base64Str);
			}
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("RestClient result: " + result);
    	return result;
    }
    
    public ResponseDto execute2() {
    	return JSON.parseObject(execute(), ResponseDto.class);
    }
    
}
