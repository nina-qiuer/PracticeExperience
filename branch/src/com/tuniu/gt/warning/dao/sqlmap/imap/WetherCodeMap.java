package com.tuniu.gt.warning.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("warning_dao_sqlmap-wether_code")
public interface WetherCodeMap extends IMapBase {
	
	void keywordSet(Map<String, Object> params);
	
}
