package com.tuniu.infobird.vdnlog.entity;

import java.util.Date;

public class VDNLogEntity {
	
	private Integer id;
	private String vdnId;
	private Date statisticDate;
	private String callId;
	private String callType;
	private String callingId;
	private String calledId;
	private String extDevice;
	private Float answerTime;
	private Float talkTime;
	private String cause;
	private Float queuedTime;
	private String golbalCallId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVdnId() {
		return vdnId;
	}
	public void setVdnId(String vdnId) {
		this.vdnId = vdnId;
	}
	public Date getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getCallingId() {
		return callingId;
	}
	public void setCallingId(String callingId) {
		this.callingId = callingId;
	}
	public String getCalledId() {
		return calledId;
	}
	public void setCalledId(String calledId) {
		this.calledId = calledId;
	}
	public String getExtDevice() {
		return extDevice;
	}
	public void setExtDevice(String extDevice) {
		this.extDevice = extDevice;
	}
	public Float getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Float answerTime) {
		this.answerTime = answerTime;
	}
	public Float getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(Float talkTime) {
		this.talkTime = talkTime;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public Float getQueuedTime() {
		return queuedTime;
	}
	public void setQueuedTime(Float queuedTime) {
		this.queuedTime = queuedTime;
	}
	public String getGolbalCallId() {
		return golbalCallId;
	}
	public void setGolbalCallId(String golbalCallId) {
		this.golbalCallId = golbalCallId;
	}
	
	
}
