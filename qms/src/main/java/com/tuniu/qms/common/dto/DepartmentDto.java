package com.tuniu.qms.common.dto;

public class DepartmentDto {
	
	private String depName;
	
	private Integer pid;
	
	private Integer delFlag ;
	
	/** 投诉质检使用标识，0：不使用，1：使用 */
	private Integer cmpQcUseFlag;

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCmpQcUseFlag() {
		return cmpQcUseFlag;
	}

	public void setCmpQcUseFlag(Integer cmpQcUseFlag) {
		this.cmpQcUseFlag = cmpQcUseFlag;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
