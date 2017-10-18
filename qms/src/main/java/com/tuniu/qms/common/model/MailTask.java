package com.tuniu.qms.common.model;

import com.tuniu.qms.common.model.BaseModel;

public class MailTask extends BaseModel {

	private static final long serialVersionUID = 1L;

	/** 收件人 */
	private String[] reAddrs;	
	/** 抄送人 */
	private String[] ccAddrs;	
	/** 邮件主题 */
	private String subject;    
	/** 邮件内容 */
	private String content;
	
	/** 发送次数，失败3次后仅支持手动重发 */
	private Integer sendTimes;
	
	/** 添加人角色，用于邮件发送控制 */
	private Integer addPersonRoleId;

	public String[] getReAddrs() {
		return reAddrs;
	}

	public void setReAddrs(String[] reAddrs) {
		this.reAddrs = reAddrs;
	}

	public String[] getCcAddrs() {
		return ccAddrs;
	}

	public void setCcAddrs(String[] ccAddrs) {
		this.ccAddrs = ccAddrs;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public Integer getAddPersonRoleId() {
		return addPersonRoleId;
	}

	public void setAddPersonRoleId(Integer addPersonRoleId) {
		this.addPersonRoleId = addPersonRoleId;
	}
}