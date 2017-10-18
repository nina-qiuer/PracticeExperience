package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ClaimsAuditMap;
import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;

@Repository("complaint_dao_impl-claims_audit")
public class ClaimsAuditDao extends DaoBase<ClaimsAuditEntity, ClaimsAuditMap>  implements ClaimsAuditMap {
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-claims_audit")
	public void setMapper(ClaimsAuditMap mapper) {
		this.mapper = mapper;
	}

	public List<ClaimsAuditEntity> queryClaimsAuditList(
			Map<String, Object> paramMap) {
		return mapper.queryClaimsAuditList(paramMap);
	}

	public Integer queryClaimsAuditListCount(Map<String, Object> paramMap) {
		return mapper.queryClaimsAuditListCount(paramMap);
	}
}
