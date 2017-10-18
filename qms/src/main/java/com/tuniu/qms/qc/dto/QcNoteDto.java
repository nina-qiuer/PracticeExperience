package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcNote;

public class QcNoteDto extends BaseDto<QcNote> {
	private Integer qcBillId;
	
	private String newContent;
	
	private String dockingPeople;
	
	private String dockingDep;

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public Integer getQcBillId() {
		return qcBillId;
	}

	public void setQcBillId(Integer qcBillId) {
		this.qcBillId = qcBillId;
	}

	public String getDockingPeople() {
		return dockingPeople;
	}

	public void setDockingPeople(String dockingPeople) {
		this.dockingPeople = dockingPeople;
	}

	public String getDockingDep() {
		return dockingDep;
	}

	public void setDockingDep(String dockingDep) {
		this.dockingDep = dockingDep;
	}
	
}
