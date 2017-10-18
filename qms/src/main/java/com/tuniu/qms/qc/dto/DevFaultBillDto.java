package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.DevFaultBill;

public class DevFaultBillDto extends BaseDto<DevFaultBill> {

	/** 质检单号*/
	private Integer qcId;

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	
	
	
	
}
