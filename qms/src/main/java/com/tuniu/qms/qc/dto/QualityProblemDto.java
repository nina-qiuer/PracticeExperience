package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QualityProblem;

public class QualityProblemDto extends BaseDto<QualityProblem> {

	/**质检单号*/
	private Integer qcId;
	
	private Integer flag;
	
	/** 人员姓名*/
	private String personName;
	
	/** 详情等级*/
	private Integer level;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	
	private Integer qptLv1Id;

	public Integer getQptLv1Id() {
		return qptLv1Id;
	}

	public void setQptLv1Id(Integer qptLv1Id) {
		this.qptLv1Id = qptLv1Id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
