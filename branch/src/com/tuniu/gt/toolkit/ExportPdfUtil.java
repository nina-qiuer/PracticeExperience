package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.pdfbox.pdmodel.PDDocument;

public class ExportPdfUtil {
	
	private static Logger logger = Logger.getLogger(ExportPdfUtil.class);

	public static void download(HttpServletResponse response, String pdfUrl){
		HttpURLConnection url_con = null; 
		PDDocument document = null;
		OutputStream os = null;
		URL url = null;
		try {
			url = new URL(pdfUrl);
			url_con=(HttpURLConnection)url.openConnection();  
			url_con.setRequestMethod("GET"); 
			url_con.setDoOutput(true); 
			url_con.getOutputStream().flush(); 
			url_con.getOutputStream().close();  
			
			os = response.getOutputStream();
			document = PDDocument.load(url_con.getInputStream());
			
			String fileName = url.getFile();
			String[] fileNames = fileName.split("/");
			
			response.reset(); 
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileNames[fileNames.length-1]);
			response.setContentType("application/pdf");
			document.save(os);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(os != null)try {os.close();} catch (IOException e) {}
			if(document != null)try {document.close();} catch (IOException e) {}
		}
	}
}
