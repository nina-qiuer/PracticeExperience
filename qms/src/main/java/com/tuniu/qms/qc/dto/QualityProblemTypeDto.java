package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QualityProblemType;

public class QualityProblemTypeDto extends BaseDto<QualityProblemType> {

	private String qpTypeName;
	
	private Integer pid;

	private Integer cmpQcUse;
	
	private Integer operQcUse;
	
	private Integer devQcUse;
	
	private Integer innerQcUse;
	
	private String name;
	

	public Integer getInnerQcUse() {
		return innerQcUse;
	}

	public void setInnerQcUse(Integer innerQcUse) {
		this.innerQcUse = innerQcUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQpTypeName() {
		return qpTypeName;
	}

	public void setQpTypeName(String qpTypeName) {
		this.qpTypeName = qpTypeName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCmpQcUse() {
		return cmpQcUse;
	}

	public void setCmpQcUse(Integer cmpQcUse) {
		this.cmpQcUse = cmpQcUse;
	}

	public Integer getOperQcUse() {
		return operQcUse;
	}

	public void setOperQcUse(Integer operQcUse) {
		this.operQcUse = operQcUse;
	}

	public Integer getDevQcUse() {
		return devQcUse;
	}

	public void setDevQcUse(Integer devQcUse) {
		this.devQcUse = devQcUse;
	}
	
}
