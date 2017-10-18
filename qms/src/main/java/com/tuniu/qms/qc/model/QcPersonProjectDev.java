package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcPersonProjectDev extends BaseModel {

	
	/**
	 * 人员配置系统
	 */
	private static final long serialVersionUID = 1L;

	private Integer qcPersonId;//质检员id
	
	private String qcPersonName;//质检员姓名
	
	private Integer projectId;//对接系统id

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

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	
}
