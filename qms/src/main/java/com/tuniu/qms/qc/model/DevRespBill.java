package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class DevRespBill extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**故障单id */
	private Integer devId;

	/** 责任人ID */
	private Integer respPersonId;
	
	/** 责任人Name */
	private String respPersonName;
	
	/** 责任部门ID */
	private Integer depId;
	
	/** 责任部门 */
	private String depName;
	
	/** 责任岗位ID */
	private Integer jobId;
	
	/** 责任岗位 */
	private String jobName;
	
	/** 责任事由 */
	private String respReason;
	
	/** 改进人ID */
	private Integer impPersonId;
	
	/** 改进人Name */
	private String impPersonName;

	

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public Integer getRespPersonId() {
		return respPersonId;
	}

	public void setRespPersonId(Integer respPersonId) {
		this.respPersonId = respPersonId;
	}

	public String getRespPersonName() {
		return respPersonName;
	}

	public void setRespPersonName(String respPersonName) {
		this.respPersonName = respPersonName;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getRespReason() {
		return respReason;
	}

	public void setRespReason(String respReason) {
		this.respReason = respReason;
	}

	public Integer getImpPersonId() {
		return impPersonId;
	}

	public void setImpPersonId(Integer impPersonId) {
		this.impPersonId = impPersonId;
	}

	public String getImpPersonName() {
		return impPersonName;
	}

	public void setImpPersonName(String impPersonName) {
		this.impPersonName = impPersonName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


		
}
