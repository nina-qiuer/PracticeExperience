package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.User;

public class UserExpandDto extends User{

	/** 报告人ID */
	private Integer impPersonId;
	
	/** 报告人姓名 */
	private String impPersonName;
	
	/** 报告人部门名称 */
	private String impDepName;
	
	/** 报告人岗位*/
	private String impPersonJob;
	
	/** 二级报告人ID */
	private Integer secondImpPersonId;
	
	/** 二级报告人姓名 */
	private String secondImpPersonName;
	
	/** 二级报告人岗位名称*/
	private String secondImpPersonJob;

	public Integer getImpPersonId() {
		return impPersonId;
	}

	public void setImpPersonId(Integer impPersonId) {
		this.impPersonId = impPersonId;
	}

	public String getImpPersonName() {
		return impPersonName;
	}

	public void setImpPersonName(String impPersonName) {
		this.impPersonName = impPersonName;
	}

	public String getImpDepName() {
		return impDepName;
	}

	public void setImpDepName(String impDepName) {
		this.impDepName = impDepName;
	}

	public String getImpPersonJob() {
		return impPersonJob;
	}

	public void setImpPersonJob(String impPersonJob) {
		this.impPersonJob = impPersonJob;
	}

	public Integer getSecondImpPersonId() {
		return secondImpPersonId;
	}

	public void setSecondImpPersonId(Integer secondImpPersonId) {
		this.secondImpPersonId = secondImpPersonId;
	}

	public String getSecondImpPersonName() {
		return secondImpPersonName;
	}

	public void setSecondImpPersonName(String secondImpPersonName) {
		this.secondImpPersonName = secondImpPersonName;
	}

	public String getSecondImpPersonJob() {
		return secondImpPersonJob;
	}

	public void setSecondImpPersonJob(String secondImpPersonJob) {
		this.secondImpPersonJob = secondImpPersonJob;
	}
	
}
