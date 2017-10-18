package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-complaint_email_config")
public interface IComplaintEmailConfigMap extends IMapBase {
	
	Integer getEmailConfigCount(Map<String, Object> paramMap);
}
