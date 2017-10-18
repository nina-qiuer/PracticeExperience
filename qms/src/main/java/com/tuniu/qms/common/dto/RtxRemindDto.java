package com.tuniu.qms.common.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.tuniu.qms.common.model.RtxRemind;
/**
 * 备用
 * @author jiangye
 *
 */
public class RtxRemindDto extends BaseDto<RtxRemind> {
	
	private Integer uid;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
	
	private Integer qcId; 
	
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

}
