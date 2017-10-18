package com.tuniu.gt;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import tuniu.frm.service.Constant;
import junit.framework.TestCase;

public class TestCaseExtend extends TestCase {
    
    public TestCaseExtend(){
	}
	
	 private static ApplicationContext ac;

	    static {
	        ac = new FileSystemXmlApplicationContext((new StringBuilder()).append("file:").append("WebContent/")
	                .append("WEB-INF/applicationContext.xml").toString());
	    }
	    
	    public static <T> T get(String s) {
	        try {
	            return (T) ac.getBean(s);
	        } catch(Exception exception) {
	            exception.printStackTrace();
	        }
	        return null;
	    }
}
