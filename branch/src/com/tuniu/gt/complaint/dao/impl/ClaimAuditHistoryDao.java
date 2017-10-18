/**
 * 
 */
package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ClaimsAuditHistoryMap;
import com.tuniu.gt.complaint.dao.sqlmap.imap.ClaimsAuditMap;
import com.tuniu.gt.complaint.entity.ClaimAuditHistory;

/**
 * @author jiangye
 *
 */
@Repository("complaint_dao_impl-claim_audit_history")
public class ClaimAuditHistoryDao extends	DaoBase<ClaimAuditHistory, ClaimsAuditHistoryMap> implements 	ClaimsAuditHistoryMap {
	
	
	public ClaimAuditHistoryDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "claim_audit_history";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-claim_audit_history")
	public void setMapper(ClaimsAuditHistoryMap mapper) {
		this.mapper = mapper;
	}
}
