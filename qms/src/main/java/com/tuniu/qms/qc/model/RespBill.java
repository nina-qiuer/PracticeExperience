package com.tuniu.qms.qc.model;

import java.io.Serializable;

public class RespBill  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  Integer id;
	
	private Integer qpId;//质量问题id
	
	private Integer  respPersonId;//责任人ID
	
	private Integer depId;//责任部门ID
	
	private Integer  jobId;//责任岗位ID
	
	private String addPerson;//添加人
	
	private String addTime;//添加时间
	
	private String updatePerson;//修改人
	
	private String updateTime;//修改时间
	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQpId() {
		return qpId;
	}

	public void setQpId(Integer qpId) {
		this.qpId = qpId;
	}

	public Integer getRespPersonId() {
		return respPersonId;
	}

	public void setRespPersonId(Integer respPersonId) {
		this.respPersonId = respPersonId;
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



	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



	
	
}
