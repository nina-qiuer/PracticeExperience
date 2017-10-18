package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.AssignRecordEntity;

import tuniu.frm.core.IServiceBase;

public interface IAssignRecordService extends IServiceBase {
	
	void addAssignRecordBatch(List<AssignRecordEntity> recordList);
	
}
