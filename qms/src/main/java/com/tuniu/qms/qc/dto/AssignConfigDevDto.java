package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.AssignConfigDev;

public class AssignConfigDevDto extends BaseDto<AssignConfigDev> {
	
	/** 质检员ID */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPersonName;
	
	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}

}
