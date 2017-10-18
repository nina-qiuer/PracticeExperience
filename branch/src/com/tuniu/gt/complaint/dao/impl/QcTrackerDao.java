package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.QcTrackerEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcTrackerMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-qc_tracker")
public class QcTrackerDao extends DaoBase<QcTrackerEntity, IQcTrackerMap> {
	public QcTrackerDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "qc_tracker";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc_tracker")
	public void setMapper(IQcTrackerMap mapper) {
		this.mapper = mapper;
	}
	
	public void deleteTrackersByQuestioinId(int qid) {
		mapper.deleteTrackersByQuestioinId(qid);
	}
}
