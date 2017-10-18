package com.tuniu.gt.complaint.dao.sqlmap.imap;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-solution_tourticket")
public interface ISolutionTourticketMap extends IMapBase {
	
	void deleteBySolutionId(Integer solutionId);

}
