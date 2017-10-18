package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

public class QcPointEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer addPersonId;
	
	private String addPerson;
	
	private Integer updatePersonId;
	
	private String updatePerson;
	
	private Integer complaintId;
	
	private String qcPoint;//质检点
	
	private String evidence;//证据
	
	private Date addTime;
	
	private Date updateTime;
	
	private Integer delFlag;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(Integer addPersonId) {
		this.addPersonId = addPersonId;
	}


	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
	}


	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public String getQcPoint() {
		return qcPoint;
	}

	public void setQcPoint(String qcPoint) {
		this.qcPoint = qcPoint;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
