package com.tuniu.gt.complaint.entity;

public class AutoAssignCfgQcEntity {
	
	private int id;
	
	private int assignId;
	
	private String depName;
	
	private String depIds;
	
	private String spDepIds;
	
	private String depNames;
	
	public String getSpDepIds() {
		return spDepIds;
	}

	public void setSpDepIds(String spDepIds) {
		this.spDepIds = spDepIds;
	}

	public String getDepNames() {
		return depNames;
	}

	public void setDepNames(String depNames) {
		this.depNames = depNames;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssignId() {
		return assignId;
	}

	public void setAssignId(int assignId) {
		this.assignId = assignId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepIds() {
		return depIds;
	}

	public void setDepIds(String depIds) {
		this.depIds = depIds;
	}

}
