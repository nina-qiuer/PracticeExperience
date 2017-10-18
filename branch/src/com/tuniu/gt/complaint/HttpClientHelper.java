package com.tuniu.gt.complaint;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

/**
 * HttpClient模板，负责设置默认超时参数，执行Http请求，断开连接
 * 
 */
public class HttpClientHelper {
	
	private Logger logger = Logger.getLogger(getClass());

    /** 默认等待连接建立超时，单位:毫秒 */
    private static final int connectionTimeout = 2000;

    /** 默认等待数据返回超时，单位:毫秒 */
    private static final  int soTimeout = 5000;

    /**
     * GET提交
     * @param url
     * @param jsonData
     * @return
     */
    public String executeGet(String url,String  jsonData) {
        
    	 String responseDetail="";
    	try {
    		
    		 HttpClient httpClient = new HttpClient();
    	   	 httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);  
    	   	// 设置读数据超时时间(单位毫秒)  
    	   	 httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);  
    	    //  String jsonData =JSONObject.fromObject(map).toString();
			String requestData = Base64.encodeBase64String(jsonData.getBytes("utf-8"));
		    String requestUrl = url+"?"+requestData;
		    GetMethod getMethod = new GetMethod(requestUrl); 
		    getMethod.addRequestHeader("platform","complaint");
		    int result = httpClient.executeMethod(getMethod); 
		    if(200 == result){
		    	
		    	 byte[] responseBody = getMethod.getResponseBody();
				 String responseData =new String(responseBody);
		    	 responseDetail = new String(Base64.decodeBase64(responseData.getBytes("utf-8")));
		    	
		    }
    	    
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
    	
    	return responseDetail;
   }

    /**
     * post提交
     * @param url
     * @param map
     * @return
     */
    public String executePost(String url,String  jsonData) {
        
   	 String responseDetail="";
   	try {
   		
   		  HttpClient httpClient = new HttpClient();
   	 	  httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);  
 	   	// 设置读数据超时时间(单位毫秒)  
 	   	  httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);  
		  String requestData =Base64.encodeBase64String(jsonData.getBytes("utf-8"));
		  PostMethod  method =new  PostMethod(url);
//		  method.addRequestHeader("platform", "complaint");
		  RequestEntity requestEntity = new StringRequestEntity(requestData);
		  method.setRequestEntity(requestEntity);
		  int result =httpClient.executeMethod( method);
		  if(result==200){
		    	
		    	 byte[] responseBody = method.getResponseBody();
				 String responseData =new String(responseBody);
		    	 responseDetail = new String(Base64.decodeBase64(responseData.getBytes("utf-8")));
		    	
		    }
   	    
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
   	return responseDetail;
  }


    
}
