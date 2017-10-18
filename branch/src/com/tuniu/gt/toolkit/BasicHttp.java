package com.tuniu.gt.toolkit;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.tuniu.gt.toolkit.lang.CollectionUtil;

/**
 * 此类描述的是：http接口的基础类
 * 
 * @author xujinfei
 */

public class BasicHttp {
	private static Log LOG = LogFactory.getLog(BasicHttp.class);
	/**
	 * 
	 * 此方法描述的是：执行http接口
	 * 
	 * @param map
	 *            url参数key/value
	 * @param UrlScheme
	 *            协议类型
	 * @param UrlHost
	 *            主机域名
	 * @param port
	 *            主机端口
	 * @param UrlPath
	 *            url路径
	 * @return String 服务器返回的字符串
	 */
	@SuppressWarnings("unchecked")
	public static String execute(Map<String, String> map, String UrlScheme, String UrlHost, int port, String UrlPath) {
		String responseBody = null;
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		List<BasicNameValuePair> qparams = new LinkedList<BasicNameValuePair>();
		for (String key : map.keySet()) {
			qparams.add(new BasicNameValuePair(key, map.get(key)));
		}
		try {
			URI uri = URIUtils.createURI(UrlScheme, UrlHost, port, UrlPath, BasicHttp.format(qparams), null);
			HttpPost httpPost = new HttpPost(uri);
			ResponseHandler responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httpPost, responseHandler).toString();
			responseBody = new String(responseBody.getBytes("iso-8859-1"), "UTF-8");
		} catch (Exception e) {
			responseBody = "fail";
			LOG.error(e);
		}
		return responseBody;
	}
	/**
	 * 
	 * 此方法描述的是：post请求
	 */
	public static String executePost(String url,Map<String,String> map) {
	      // 将表单的值放入postMethod中
	      // 执行postMethod
	     String  returnBody="";
	      PostMethod postMethod=null;
	      HttpClient httpClient=new HttpClient();
	   try {
		   postMethod=new PostMethod(url);
		   List<NameValuePair> list=new LinkedList<NameValuePair>();
		   for (String key:map.keySet()){
			   NameValuePair nameValuePair = new NameValuePair( key , map.get(key));
			   list.add(nameValuePair);
		   }
		   NameValuePair[] data =null;
		   if (CollectionUtil.isNotEmpty(list)){
			   data=new NameValuePair[list.size()];
			   for (int i = 0; i < data.length; i++) {
				   data[i]=list.get(i);
			   }
		   }
		   postMethod.setRequestBody(data);
		   httpClient.executeMethod(postMethod);
		   returnBody= postMethod.getResponseBodyAsString();
	   } catch (Exception e) {
		   returnBody="fail";
		   LOG.error(e);
	   }finally{
		   if (postMethod!=null) {
			   postMethod.releaseConnection();
		   }
	   } 
	   return returnBody;
	}
	/**
	 * 
	 * 此方法描述的是：格式化参数
	 * 
	 */
	public  static String format(List<BasicNameValuePair> list) {
		String formatStr = "";
		if (CollectionUtil.isNotEmpty(list)) {
			int i = 1;
			for (BasicNameValuePair basicNameValuePair : list) {
				if (i == 1) {
					try {
						formatStr = formatStr + basicNameValuePair.getName() + "="
								+ URLEncoder.encode(basicNameValuePair.getValue(),"utf-8");
					} catch (UnsupportedEncodingException e) {
						LOG.error(e);
					}
				} else {
					try {
						formatStr = formatStr + "&" + basicNameValuePair.getName()
								+ "=" + URLEncoder.encode(basicNameValuePair.getValue(),"utf-8");
					} catch (UnsupportedEncodingException e) {
						LOG.error(e);
					}
				}
				i++;
			}
			return formatStr;
		}
		return null;
	}
}
