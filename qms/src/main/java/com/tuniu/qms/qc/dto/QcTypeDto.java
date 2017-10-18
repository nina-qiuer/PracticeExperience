package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcType;

public class QcTypeDto extends BaseDto<QcType> {
	
	private String qtName;
	
	private Integer pid;

	public synchronized String getQtName() {
		return qtName;
	}

	public synchronized void setQtName(String qtName) {
		this.qtName = qtName;
	}

	public synchronized Integer getPid() {
		return pid;
	}

	public synchronized void setPid(Integer pid) {
		this.pid = pid;
	}
	
	
}
