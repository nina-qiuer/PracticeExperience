/**
 * 
 */
package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.tuniu.gt.toolkit.lang.CollectionUtil;

public class HttpXmlClient {
    private static Logger log = Logger.getLogger(HttpXmlClient.class);

    public static String post(String url, Map<String, String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

//        log.info("create httppost:" + url);
        HttpPost post = postForm(url, params);

        body = invoke(httpclient, post);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    public static String get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;

        log.info("create httppost:" + url);
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);

        httpclient.getConnectionManager().shutdown();

        return body;
    }

    private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {

        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);

        return body;
    }

    private static String paseResponse(HttpResponse response) {
//        log.info("get response from http server..");
        HttpEntity entity = response.getEntity();

//        log.info("response status: " + response.getStatusLine());
//        log.info(JSONArray.fromObject(response.getHeaders("Cookie")));
        String charset = EntityUtils.getContentCharSet(entity);
//        log.info(charset);

        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch(ParseException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
//        log.info("execute post...");
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch(ClientProtocolException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
//            log.info("set utf-8 form entity to httppost");
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
            httpost.setHeader(new BasicHeader("Cookie", "PHPSESSID=u5af9r9ancio3i5h0rn5cc7ed4"));
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    public static void makeInsertSql(String select,String tableName) {
//        log.info(select);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("server_id", "647");
        paramMap.put("instance_id", "648");
        paramMap.put("DbId", "856");
        paramMap.put("sql_str", select);

        String xml = HttpXmlClient.post("http://sqladmin.tuniu.org/show/execute.php?action=get_result", paramMap);

        String result = xml.substring(xml.indexOf('['));
        JSONArray resultArray = JSONArray.fromObject(result);
        JSONObject resultElement = (JSONObject) resultArray.get(0);
        // 构造fields
        Iterator<String> iterator = resultElement.keys();
        List<String> fieldList = new ArrayList<String>();
        while(iterator.hasNext()) {
            fieldList.add(iterator.next());
        }

        iterator = resultElement.keys();

        String fields = StringUtils.join(fieldList, ",");

        // 构造values
        List<String> valueList = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for(Object obj : resultArray) {
            resultElement = (JSONObject) obj;
            List<String> valueTmp = new ArrayList<String>();
            while(iterator.hasNext()) {
                valueTmp.add("'" + JsonUtil.getStringValue(resultElement, iterator.next()) + "'");
            }
            iterator = resultElement.keys();
            String value = StringUtils.join(valueTmp, ",");
            values.add(value);
        }

        StringBuilder a = new StringBuilder("insert into "+tableName+"(");
        a.append(fields).append(") values");

        for(String value : values) {
            a.append("(");
            a.append(value);
            a.append("),");
        }

        a.deleteCharAt(a.length() - 1);

        log.info(a);
    }
    
    public static String makeSelect(String tableName,Integer start,Integer amount){
        String select = "";
            select = "select * from "+tableName+" limit "+start+","+amount;
        return select;
    }

    public static void main(String[] args) {
        String select = "";
        String tableName="ct_reason_type";
        for(int i = 0; i < 1; i++) {
            
            select = makeSelect(tableName,i*1000,1000);
            makeInsertSql(select,tableName);
        }
    }
}
