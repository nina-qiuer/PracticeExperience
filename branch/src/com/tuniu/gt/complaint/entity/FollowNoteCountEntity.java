package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class FollowNoteCountEntity implements Serializable{

	private static final long serialVersionUID = 4880952999956051829L;
	
	private String people_name;
	
	private Integer countNum;
	
	private String flow_name;

	public String getPeople_name() {
		return people_name;
	}

	public void setPeople_name(String people_name) {
		this.people_name = people_name;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public String getFlow_name() {
		return flow_name;
	}

	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
}
