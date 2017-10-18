package com.tuniu.gt.toolkit;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.log4j.Logger;
/**
 * 
 * @author weiweiqin
 *
 */
public class RemoteFileUploadUtil
{
	
	private static Logger logger = Logger.getLogger(RemoteFileUploadUtil.class);

    /**
     * 通过ftp上传文件
     * @param file 文件内容
     * @param fileName 文件名称
     * @return
     */
    public static String uploadFile(String uploadURL, File file, String fileName)
    {
        String result = "";
        if (!file.exists())
        {
            return "文件不存在!";
        }
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
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
            int status = client.executeMethod(filePost);
            if (status != HttpStatus.SC_OK)
            {
                result = "FAIL";
            }
            else if (status == HttpStatus.SC_OK)
            {
                result = filePost.getResponseBodyAsString();
                if (result.startsWith("上传文件已经存在"))
                {
                    result = result.replace("上传文件已经存在<br/>", "");
                }
            }

        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
