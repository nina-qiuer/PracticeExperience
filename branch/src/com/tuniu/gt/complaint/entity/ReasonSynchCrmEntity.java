package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class ReasonSynchCrmEntity implements Serializable{

	private static final long serialVersionUID = -5224905108389585955L;
	
	private String reason_id;//投诉事宜id

	private String complaint_id;//投诉单号
	
	private String type;//一级分类
	
	private String second_type;//二级分类
	
	private String add_time;//新增时间
	
	private String update_time;//更新时间
	
	private String type_descript;//详细情况
	
	private String descript;//其他情况描述

	public String getReason_id() {
		return reason_id;
	}

	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}

	public String getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(String complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecond_type() {
		return second_type;
	}

	public void setSecond_type(String second_type) {
		this.second_type = second_type;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	
	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getType_descript() {
		return type_descript;
	}

	public void setType_descript(String type_descript) {
		this.type_descript = type_descript;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
}
