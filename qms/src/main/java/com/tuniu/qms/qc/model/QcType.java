package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcType extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	/** ParentID */
	private Integer pid;
	
	/** 质检类型名称 */
	private String name;
	/** 问题全名 */
	private String fullName;
	
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
