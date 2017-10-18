package com.tuniu.qms.qc.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;

public class AssignConfigCmp extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	/** 质检员ID */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPersonName;
	
	/** 是否处理无订单质检，0：否，1：是 */
	private Integer noOrderFlag;
	
	/** 无订单最后分单时间 */
	private Date nooAssignTime;

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

	public Integer getNoOrderFlag() {
		return noOrderFlag;
	}

	public void setNoOrderFlag(Integer noOrderFlag) {
		this.noOrderFlag = noOrderFlag;
	}

	public Date getNooAssignTime() {
		return nooAssignTime;
	}

	public void setNooAssignTime(Date nooAssignTime) {
		this.nooAssignTime = nooAssignTime;
	}
	
}
