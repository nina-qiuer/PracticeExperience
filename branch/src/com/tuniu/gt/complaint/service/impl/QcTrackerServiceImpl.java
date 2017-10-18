package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.QcTrackerDao;
import com.tuniu.gt.complaint.service.IQcTrackerService;
@Service("complaint_service_complaint_impl-qc_tracker")
public class QcTrackerServiceImpl extends ServiceBaseImpl<QcTrackerDao> implements IQcTrackerService {
	@Autowired
	@Qualifier("complaint_dao_impl-qc_tracker")
	public void setDao(QcTrackerDao dao) {
		this.dao = dao;
	};
}
