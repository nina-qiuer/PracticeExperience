package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.AssignRecordDao;
import com.tuniu.gt.complaint.entity.AssignRecordEntity;
import com.tuniu.gt.complaint.service.IAssignRecordService;

@Service("complaint_service_complaint_impl-assign_record")
public class AssignRecordServiceImpl extends ServiceBaseImpl<AssignRecordDao> implements IAssignRecordService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-assign_record")
	public void setDao(AssignRecordDao dao) {
		this.dao = dao;
	}

	@Override
	public void addAssignRecordBatch(List<AssignRecordEntity> recordList) {
		dao.addAssignRecordBatch(recordList);
	}

}
