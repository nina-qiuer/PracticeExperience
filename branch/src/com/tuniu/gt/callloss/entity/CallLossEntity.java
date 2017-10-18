package com.tuniu.gt.callloss.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class CallLossEntity extends EntityBase implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1894897287320512487L;

	private Integer vdnLogId;//电话记录日志ID
	
	private Integer vdnId;//技能组识别号
	
	private String callingId; //客人主叫号
	
	private String callingCity; //客人主叫号归属地
	
	private String statisticDate; //来电时间

	private Double queuedTime; //排队时间
	
	private Double answerTime; //排队振铃时间
	
	private Double talkTime; //通话时间
	
	private Integer status; //状态
	
	private Date createdTime;//添加日期
	
	private Integer lastUpdatedBy;//最后修改人
	
	private String lastUpdatedNameBy;//最后修改人
	
	private Date lastUpdatedTime;//最后修改时间
	
	private Integer delFlag;//标记
	
	private Integer callCount;//回访次数
	
	private Integer autoClose; //自动关闭标志位，0：不是自动关闭；1：自动关闭';
	
	private Integer callingCount; //'呼损电话次数';
	
	private String smsContent;//呼损短信

	
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Integer getAutoClose() {
		return autoClose;
	}

	public void setAutoClose(Integer autoClose) {
		this.autoClose = autoClose;
	}

	public Integer getCallingCount() {
		return callingCount;
	}

	public void setCallingCount(Integer callingCount) {
		this.callingCount = callingCount;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public Integer getVdnLogId() {
		return vdnLogId;
	}

	public void setVdnLogId(Integer vdnLogId) {
		this.vdnLogId = vdnLogId;
	}

	public Integer getVdnId() {
		return vdnId;
	}

	public void setVdnId(Integer vdnId) {
		this.vdnId = vdnId;
	}

	public String getCallingId() {
		return callingId;
	}

	public void setCallingId(String callingId) {
		this.callingId = callingId;
	}

	public String getCallingCity() {
		return callingCity;
	}

	public void setCallingCity(String callingCity) {
		this.callingCity = callingCity;
	}

	public String getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	}

	public Double getQueuedTime() {
		return queuedTime;
	}

	public void setQueuedTime(Double queuedTime) {
		this.queuedTime = queuedTime;
	}

	public Double getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Double answerTime) {
		this.answerTime = answerTime;
	}

	public Double getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(Double talkTime) {
		this.talkTime = talkTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getLastUpdatedNameBy() {
		return lastUpdatedNameBy;
	}

	public void setLastUpdatedNameBy(String lastUpdatedNameBy) {
		this.lastUpdatedNameBy = lastUpdatedNameBy;
	}
}
