package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class SendMailConfig extends BaseModel {
	
	/**
	 * 配置发送人
	 */
	private static final long serialVersionUID = 1L;
	/**类型*/
	private String mailType;
	/**发送人*/
	private String sendAddrs;
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getSendAddrs() {
		return sendAddrs;
	}
	public void setSendAddrs(String sendAddrs) {
		this.sendAddrs = sendAddrs;
	}

	


}