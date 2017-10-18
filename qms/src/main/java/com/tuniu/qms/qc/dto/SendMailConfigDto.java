package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.SendMailConfig;

public class SendMailConfigDto extends BaseDto<SendMailConfig> {
	
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