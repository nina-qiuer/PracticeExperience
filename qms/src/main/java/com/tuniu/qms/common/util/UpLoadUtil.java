package com.tuniu.qms.common.util;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.common.init.Config;

public class UpLoadUtil {
	
	private static String serverURL =   Config.get("filebroker.url");
			
	
	/**
	 * MultipartFile 转File
	 * @param mf
	 * @return
	 * @throws Exception
	 */
	public static File mfToFile(MultipartFile mf) throws Exception {

		 CommonsMultipartFile cf= (CommonsMultipartFile)mf; 
	     DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	     File file = fi.getStoreLocation();
	     fi.write(file);
		 return file;

		}
	
	/**
	 * 上传附件
	 */
	public static String uploadFile(File file, String fileName) {
		String url = "";
		try {
			//将文件上传到远程服务器
			String result = uploadFileTo(serverURL, file, fileName);
			if(!"FAIL".equals(result)){
				JSONObject fromObject = JSONObject.parseObject(result);
				    boolean success = fromObject.getBoolean("success");   
				    if(success){
				    	Object object = fromObject.get("data");
				        JSONArray dataList = JSON.parseArray(object+"");
				        if(null != dataList && !dataList.isEmpty()){
				            Object object2 = dataList.get(0);
				            JSONObject fromObject2 = JSONObject.parseObject(object2+"");
				            url = fromObject2.get("url").toString();
				        }
				    }
				
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	
    public static String uploadFileTo(String uploadURL, File file, String fileName)
    {
        String result = "FAIL";
        try
        {
            String fileNameEncode = Base64.encodeBase64URLSafeString(fileName.getBytes("utf8"));
            String url = uploadURL
					+ "?name="
					+ fileNameEncode
					+ "&folder=sliding&sync_quick=2&sub_system=vnd&sync_place=0";
            PostMethod filePost = new PostMethod(url);
            FilePart fp = new FilePart("file", file);
            fp.setContentType("Content-Type");
            Part[] parts = {fp};
            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, filePost.getParams());
            filePost.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);// 设置连接时间
            client.getHttpConnectionManager().getParams().setSoTimeout(10000);//设置返回时间
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK)
            {
                result = filePost.getResponseBodyAsString();
            }else{
            	
            	result ="FAIL";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
	
	
}
