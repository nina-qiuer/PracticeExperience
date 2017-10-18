package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.OperationLog;

public class OperationLogDto extends BaseDto<OperationLog> {

	private Integer qcId;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	
	
}
