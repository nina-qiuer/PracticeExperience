package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class AssignConfigDev extends BaseModel {
	
	private static final long serialVersionUID = 1L;

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
