package com.tuniu.qms.qc.model;

import java.io.Serializable;
import java.util.List;

public class QcPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer updatePersonId;
	
	private String updatePerson;
	
	private Integer complaintId;
	
	private String qcPoint;//质检点
	
	private String evidence;//证据
	
    private QcVo qcVo;//质检信息
	
	private List<QcPointAttach> attachList;//附件信息
	
	private boolean systemProblemFlag;

	public Integer getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(Integer updatePersonId) {
		this.updatePersonId = updatePersonId;
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

	public QcVo getQcVo() {
		return qcVo;
	}

	public void setQcVo(QcVo qcVo) {
		this.qcVo = qcVo;
	}

	public List<QcPointAttach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<QcPointAttach> attachList) {
		this.attachList = attachList;
	}

	public boolean isSystemProblemFlag() {
		return systemProblemFlag;
	}

	public void setSystemProblemFlag(boolean systemProblemFlag) {
		this.systemProblemFlag = systemProblemFlag;
	}
	
}
