package com.tuniu.gt.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImportUtil {
	
	private static Logger logger = Logger.getLogger(ExcelImportUtil.class);
	
	public static Workbook createWorkBook(File excelFile, String excelFileFileName) {
    	InputStream is = null;
    	Workbook workBook = null;
    	try {
    		is = new FileInputStream(excelFile);
    		if(excelFileFileName.toLowerCase().endsWith("xls")){  
    			workBook = new HSSFWorkbook(is);  
            }  
            if(excelFileFileName.toLowerCase().endsWith("xlsx")){  
            	workBook = new XSSFWorkbook(is);  
            }  
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
        return workBook;  
    }  

}
