package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-agency_payout")
public interface IAgencyPayoutMap extends IMapBase {
	
	void deleteBySupportId(Integer supportId);
	
	void updateSupportId(Map<String, Object> params);
	
}
