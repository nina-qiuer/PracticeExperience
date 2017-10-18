package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class MailConfig extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**投诉级别*/
	private String cmpLevel;
	/**质量问题等级*/
	private String respType;
	/**收件人*/
	private String reAddrs;
	/**抄送人*/
	private String ccAddrs;

	public String getCmpLevel() {
		return cmpLevel;
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