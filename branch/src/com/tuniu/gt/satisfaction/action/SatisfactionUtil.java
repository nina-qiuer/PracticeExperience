package com.tuniu.gt.satisfaction.action;

public class SatisfactionUtil {
	
	public static String isNull(String value){
		return value == null? "": "null".equals(value)? "": value; 
	}
}
