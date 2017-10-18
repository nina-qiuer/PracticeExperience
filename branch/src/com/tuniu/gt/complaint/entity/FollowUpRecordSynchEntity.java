package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class FollowUpRecordSynchEntity implements Serializable{

	private static final long serialVersionUID = -5911446141620448354L;
	
	private String record_id;//跟进记录id

	private String complaint_id;//关联投诉id
	
	private String add_user;//跟进人id
	
	private String add_time;//添加时间
	
	private String note;//跟进信息

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(String complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getAdd_user() {
		return add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
