package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-cc_email")
public interface ICcEmailMap extends IMapBase {
	
	List<String> getCcEmails(Map<String, Object> params);
	
}
