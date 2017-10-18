package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.BusinessLog;

public class BusinessLogDto extends BaseDto<BusinessLog> {
	
	private String operatorName;
	private String resName;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	
}
