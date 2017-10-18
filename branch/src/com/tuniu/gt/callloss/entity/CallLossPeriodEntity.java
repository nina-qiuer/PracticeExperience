package com.tuniu.gt.callloss.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class CallLossPeriodEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -3939912658813499579L;

	private String startTime;
	
	private String endTime;
	
	private Date createTime;
	
	private int creatorId;
	
	private String creatorName;
	
	private Date cancelTime;
	
	private int cancelorId;
	
	private String cancelorName;
	
	private int delFlag;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public int getCancelorId() {
		return cancelorId;
	}

	public void setCancelorId(int cancelorId) {
		this.cancelorId = cancelorId;
	}

	public String getCancelorName() {
		return cancelorName;
	}

	public void setCancelorName(String cancelorName) {
		this.cancelorName = cancelorName;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
}
