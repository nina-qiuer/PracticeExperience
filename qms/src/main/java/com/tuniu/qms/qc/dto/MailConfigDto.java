package com.tuniu.qms.qc.dto;

import com.tuniu.qms.qc.model.MailConfig;
import com.tuniu.qms.common.dto.BaseDto;

public class MailConfigDto extends BaseDto<MailConfig> {
	private String cmpLevel;
	private String respType;
	private String reAddrs;
	private String ccAddrs;

	public String getCmpLevel() {
		return this.cmpLevel;
	}

	public void setCmpLevel(String cmpLevel) {
		this.cmpLevel = cmpLevel;
	}

	public String getRespType() {
		return this.respType;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	public String getReAddrs() {
		return reAddrs;
	}

	public void setReAddrs(String reAddrs) {
		this.reAddrs = reAddrs;
	}

	public String getCcAddrs() {
		return ccAddrs;
	}

	public void setCcAddrs(String ccAddrs) {
		this.ccAddrs = ccAddrs;
	}

}