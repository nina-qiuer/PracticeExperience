package com.tuniu.qms.qs.util;

import java.util.Arrays;
import java.util.List;

public enum SaleTypeEnum {
	SELFSUPPORT(1, "自营"), RETAIL(2, "零售"), DISTRIBUTION(3, "分销"), APSP(4, "APSP(自营-增值推荐)"),
	BIGCUSTOMER(5, "大客户"), KA(6, "KA");
	
	private int key;
	private String content;
	
	private SaleTypeEnum(int key, String content){
		this.key = key;
		this.content = content;
	}
	
	public static String getContents(int key){
		SaleTypeEnum[] list = SaleTypeEnum.values();
		
		for(SaleTypeEnum type : list){
			if(type.key == key){
				return type.content;
			}
		}
		
		return "";
	}
	
	public static List<SaleTypeEnum> getSourceList(){
		return Arrays.asList(SaleTypeEnum.values());
	}

	public int getKey() {
		return key;
	}

	public String getContent() {
		return content;
	}
}
