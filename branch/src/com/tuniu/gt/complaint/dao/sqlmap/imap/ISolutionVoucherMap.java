package com.tuniu.gt.complaint.dao.sqlmap.imap;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-solution_voucher")
public interface ISolutionVoucherMap extends IMapBase {
	
	void deleteBySolutionId(Integer solutionId);

}
