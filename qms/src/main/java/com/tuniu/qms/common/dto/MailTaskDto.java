package com.tuniu.qms.common.dto;

import java.util.Map;

import com.tuniu.qms.common.model.MailTask;

public class MailTaskDto extends BaseDto<MailTask> {
	
	/** 收件人 */
	private String[] reAddrs;
	
	/** 抄送人 */
	private String[] ccAddrs;
	
	/** 邮件主题 */
	private String subject;
	
	/** 邮件模板名称 */
	private String templateName;
	
	/** 邮件模板数据 */
	private Map<String, Object> dataMap;
	
	/** 添加人角色，用于邮件发送控制 */
	private Integer addPersonRoleId;
	
	public Integer getAddPersonRoleId() {
		return addPersonRoleId;
	}

	public void setAddPersonRoleId(Integer addPersonRoleId) {
		this.addPersonRoleId = addPersonRoleId;
	}

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

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

}
