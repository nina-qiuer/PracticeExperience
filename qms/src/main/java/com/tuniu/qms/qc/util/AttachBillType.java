package com.tuniu.qms.qc.util;

import java.util.Arrays;
import java.util.List;

/**
 * 质检点附件表(通用表  billtype代表类型  1:质检单   2：改进报告  3：产品自动下线id)
 * @author zhangsensen
 *
 */
public enum AttachBillType {
	QCPOINTTYPE(1, "qcId"), IMPROVETYPE(2, "impId"), PRDONLINETYPE(3, "punishPrdId");

	private int key;
	private String content;
	
	private AttachBillType(int key, String content){
		this.key = key;
		this.content = content;
	}
	
	public static String getContents(int key){
		AttachBillType[] list = AttachBillType.values();
		
		for(AttachBillType type : list){
			if(type.key == key){
				return type.content;
			}
		}
		
		return "";
	}
	
	public static List<AttachBillType> getSourceList(){
		return Arrays.asList(AttachBillType.values());
	}

	public int getKey() {
		return key;
	}

	public String getContent() {
		return content;
	}
}
