package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAssignRecordMap;
import com.tuniu.gt.complaint.entity.AssignRecordEntity;

@Repository("complaint_dao_impl-assign_record")
public class AssignRecordDao extends DaoBase<AssignRecordEntity, IAssignRecordMap>  implements IAssignRecordMap {
	
	public AssignRecordDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "assign_record";
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-assign_record")
	public void setMapper(IAssignRecordMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void addAssignRecordBatch(List<AssignRecordEntity> recordList) {
		mapper.addAssignRecordBatch(recordList);
	}

}
