package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class ComplaintCompleteCountEntity implements Serializable{
	private static final long serialVersionUID = 7541984925017139577L;
	
	private String worknum;//工号
	
	private String count_type;//计件类型
	
	private Integer count;//计件数

	public String getWorknum() {
		return worknum;
	}

	public void setWorknum(String worknum) {
		this.worknum = worknum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCount_type() {
		return count_type;
	}

	public void setCount_type(String count_type) {
		this.count_type = count_type;
	}
}
