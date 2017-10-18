package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcSameGroup;

public class QcSameGroupDto   extends BaseDto<QcSameGroup> {

	private Integer qcBillId;

	public Integer getQcBillId() {
		return qcBillId;
	}

	public void setQcBillId(Integer qcBillId) {
		this.qcBillId = qcBillId;
	}
	
	
}
