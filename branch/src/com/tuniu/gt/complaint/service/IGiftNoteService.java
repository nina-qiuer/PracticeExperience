package com.tuniu.gt.complaint.service;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
public interface IGiftNoteService extends IServiceBase {
	
	void addGiftApplyToOa(ComplaintSolutionEntity csEnt);

}
