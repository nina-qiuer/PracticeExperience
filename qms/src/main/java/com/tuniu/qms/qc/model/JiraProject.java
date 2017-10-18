package com.tuniu.qms.qc.model;

import java.io.Serializable;

public class JiraProject implements Serializable{

	/**
	 * from project_key 系统表
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer  id;
	/** 系统编号*/
	private Integer projectId;
	/**系统名称*/
	private String projectName;

	public Integer getProjectId() {
		return projectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	

}
