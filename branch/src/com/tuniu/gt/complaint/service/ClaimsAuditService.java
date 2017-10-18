package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;

public interface ClaimsAuditService extends IServiceBase{

	List<ClaimsAuditEntity> queryClaimsAuditList(Map<String, Object> paramMap, String tab_flag);

	Integer queryClaimsAuditListCount(Map<String, Object> paramMap);
	
}
