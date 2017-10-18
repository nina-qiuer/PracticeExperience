package com.tuniu.gt.complaint.dao.sqlmap.imap;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-employee_share")
public interface IEmployeeShareMap extends IMapBase {
	
	void deleteByShareId(Integer shareId);
	
}
