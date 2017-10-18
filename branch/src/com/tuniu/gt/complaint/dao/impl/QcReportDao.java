package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcReportEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcReportMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-qc_report")
public class QcReportDao extends DaoBase<QcReportEntity, IQcReportMap> {
	public QcReportDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "qc_report";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc_report")
	public void setMapper(IQcReportMap mapper) {
		this.mapper = mapper;
	}
	
	public List<QcReportEntity> getReportListByQid(int qid) {
		List<QcReportEntity> reports = mapper.getReportListByQid(qid);
		
		return reports;
	}
}
