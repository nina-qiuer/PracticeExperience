package com.tuniu.gt.toolkit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Charsets;
import com.tuniu.gt.tsp.entity.FilebrokerVo;
import com.tuniu.operation.platform.base.http.ClientFactory;
import com.tuniu.operation.platform.base.http.ResponseResult;
import com.tuniu.operation.platform.tsg.base.core.utils.JsonUtil;

public abstract class FileBrokerUtils {
	
    private static final Logger logger = LoggerFactory.getLogger(FileBrokerUtils.class);
	
    /***
     * 上传文件到filebroker
     * @param fileName 上传的文件名称
     * @param in 文件流
     * @param uploadUrl 上传文件路径
     * @param isCDN 是否外网可见
     * @return 上传文件的路径
     */
	public static String uploadFile(String fileName, InputStream in, String uploadUrl, boolean isCDN) {
		
		String fileUrl = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uploadUrl);
			builder.queryParam("name", Base64.encodeBase64URLSafeString(fileName.getBytes(Charsets.UTF_8)));
			if (isCDN) {
	        	builder.queryParam("folder", "cdn");
	        } else {
	        	builder.queryParam("folder", "sliding");
	        }
			builder.queryParam("sync_quick", "2");
			builder.queryParam("sub_system", "vnd");
			builder.queryParam("sync_place", "0");
	
	        String fileUploadUrl = builder.build().toUriString();
			ResponseResult result = ClientFactory.getClient().postInputStreamASFile(fileUploadUrl, in, fileName);
			FilebrokerVo filebrokerVo = JsonUtil.toBean(result.getContent(), FilebrokerVo.class);
            fileUrl = getUrlFromFilebroker(filebrokerVo, isCDN);
			
		} catch (Exception e) {
			logger.warn("上传文件失败! Exception info:" + e.getMessage());
		}

		return fileUrl;
	}
	
	/**
     * 获取FB返回结果制定类型的URL 注意：支持返回类型为 FB入参中style=1
     * @param filebrokerVo
     * @param isCDNUrl true:返回CDN URL, false:返回local URL
     * @return
     */
    private static String getUrlFromFilebroker(FilebrokerVo filebrokerVo, boolean isCDNUrl) {
        Iterator<Map<String, String>> urlItemIterator = filebrokerVo.getData().iterator();

        String specialUrlType = "url";
        while (urlItemIterator.hasNext()) {
            // { "cdn":"...", "local":"..." }
            Map<String, String> urlItem = urlItemIterator.next();

            String specialUrl = urlItem.get(specialUrlType);
            if (StringUtils.isNotEmpty(specialUrl)) {
                return specialUrl;
            }
        }
        
        return null;
    }
	
	public static void main(String[] args) throws IOException {
		String fileName = "a.bmp";
		String uploadUrl = "http://10.10.32.160/filebroker";
		FileInputStream fis = new FileInputStream(new File("c:\\a.bmp"));
		byte[] bytes = new byte[480949];
		int len;
		ByteArrayOutputStream  baos = new ByteArrayOutputStream ();
		while ((len = fis.read(bytes)) != -1) {
			baos.write(bytes, 0, len);
		}
		
		byte[] contents = baos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(contents);
		System.out.println(FileBrokerUtils.uploadFile(fileName, bis, uploadUrl, true));
	}
}
