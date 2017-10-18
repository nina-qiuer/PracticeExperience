package com.tuniu.gt.complaint.dao.sqlmap.imap;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-qc_tracker")
public interface IQcTrackerMap extends IMapBase { 
	void deleteTrackersByQuestioinId(int questionId);
}
