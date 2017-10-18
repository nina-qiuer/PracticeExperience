package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.QcReportDao;
import com.tuniu.gt.complaint.entity.QcReportEntity;
import com.tuniu.gt.complaint.service.IQcReportService;
@Service("complaint_service_impl-qc_report")
public class QcReportServiceImpl extends ServiceBaseImpl<QcReportDao> implements IQcReportService {
	@Autowired
	@Qualifier("complaint_dao_impl-qc_report")
	public void setDao(QcReportDao dao) {
		this.dao = dao;
	}

	@Override
	public List<QcReportEntity> getReportListByQid(int qid) {
		return this.dao.getReportListByQid(qid);
	};
}
