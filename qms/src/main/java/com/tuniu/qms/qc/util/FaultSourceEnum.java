package com.tuniu.qms.qc.util;

import java.util.Arrays;
import java.util.List;

/**
 * 研发质检单 -- 故障来源类型
 * @author zhangsensen
 *
 */
public enum FaultSourceEnum {
	INTERNALTRANSFER(1, "内部转单"), BOARDPROBLEM(2, "看板问题"), EMERGENCYLINE(3, "紧急上线"), RTFEEDBACK(4, "RT群反馈"),
	WECHATFEEDBACK(5, "微信群反馈"), SECURITY(7, "安全类"), JIRA(8, "jira"),  OTHER(6, "其他");
	
	private int key;
	private String content;
	
	private FaultSourceEnum(int key, String content){
		this.key = key;
		this.content = content;
	}
	
	public static String getContents(int key){
		FaultSourceEnum[] list = FaultSourceEnum.values();
		
		for(FaultSourceEnum type : list){
			if(type.key == key){
				return type.content;
			}
		}
		
		return "";
	}
	
	public static List<FaultSourceEnum> getSourceList(){
		return Arrays.asList(FaultSourceEnum.values());
	}

	public int getKey() {
		return key;
	}

	public String getContent() {
		return content;
	}
}
