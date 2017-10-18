package com.tuniu.qms.common.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RtxRemind extends BaseModel{

	private static final long serialVersionUID = 1L;

	private String uid;
	
	private String title;
	
	private String content;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
	
	private Integer failTimes;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}
}
