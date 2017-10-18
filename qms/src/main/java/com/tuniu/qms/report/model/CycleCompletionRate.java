package com.tuniu.qms.report.model;

import com.tuniu.qms.common.model.BaseModel;

public class CycleCompletionRate extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	/** 质检员id */
	private Integer qcPersonId;
	
	/** 质检员姓名 */
	private String qcPerson;
	
	/** 质检期开始已完成单数 */
	private Integer qcPeriodBgnDoneNum;
	
	/** 质检期开始总单数 */
	private Integer qcPeriodBgnNum;
	
	/** 完成率 */
	private Double doneRate;

	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public Integer getQcPeriodBgnDoneNum() {
		return qcPeriodBgnDoneNum;
	}

	public void setQcPeriodBgnDoneNum(Integer qcPeriodBgnDoneNum) {
		this.qcPeriodBgnDoneNum = qcPeriodBgnDoneNum;
	}

	public Integer getQcPeriodBgnNum() {
		return qcPeriodBgnNum;
	}

	public void setQcPeriodBgnNum(Integer qcPeriodBgnNum) {
		this.qcPeriodBgnNum = qcPeriodBgnNum;
	}

	public Double getDoneRate() {
		return doneRate;
	}

	public void setDoneRate(Double doneRate) {
		this.doneRate = doneRate;
	}
	
	
}
