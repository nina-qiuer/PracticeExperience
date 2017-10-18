package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-claims_audit")
public interface ClaimsAuditMap extends IMapBase {
	
	List<ClaimsAuditEntity> queryClaimsAuditList(Map<String, Object> paramMap);
	
	Integer queryClaimsAuditListCount(Map<String, Object> paramMap);
}
