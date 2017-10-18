package com.tuniu.gt.callloss.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class CallLossTetailEntity extends EntityBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7520184939147780566L;

	private String callLossCallingId;
	
	private Integer salerId;
	
	private String salerName;
	
	private Date createTime;
	
	private Integer delFlag;
	
	private String content;
	
	private Integer success;

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCallLossCallingId() {
		return callLossCallingId;
	}

	public void setCallLossCallingId(String callLossCallingId) {
		this.callLossCallingId = callLossCallingId;
	}

	public Integer getSalerId() {
		return salerId;
	}

	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	
}
