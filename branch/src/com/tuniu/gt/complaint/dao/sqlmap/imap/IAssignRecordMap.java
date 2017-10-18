package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.AssignRecordEntity;

@Repository("complaint_dao_sqlmap-assign_record")
public interface IAssignRecordMap extends IMapBase {
	
	void addAssignRecordBatch(List<AssignRecordEntity> recordList);

}
