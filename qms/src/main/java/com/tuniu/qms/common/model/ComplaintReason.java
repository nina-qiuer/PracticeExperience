package com.tuniu.qms.common.model;


import java.io.Serializable;

public class ComplaintReason implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer cmpId;
	
	private String type;
	
	private String secondType;
	
	private String typeDescript;
	
	private String descript;

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getTypeDescript() {
		return typeDescript;
	}

	public void setTypeDescript(String typeDescript) {
		this.typeDescript = typeDescript;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}

