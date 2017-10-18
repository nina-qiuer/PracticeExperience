package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcPersonDockDepCmp extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer qcPersonId;//质检员id
	
	private String qcPersonName;//质检员姓名
	
	private Integer dockdepId;//对接部门id

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

	public Integer getDockdepId() {
		return dockdepId;
	}

	public void setDockdepId(Integer dockdepId) {
		this.dockdepId = dockdepId;
	}
	
	
	
}
