package com.tuniu.gt.techsupport.dao.sqlmap.imap;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("ts_dao_sqlmap-tech_support")
public interface ITechSupportMap extends IMapBase 
{
	void update (String sqlParam);

	/**
	 * @param sqlParam
	 * @return
	 */
	int getAffectRows(String sqlParam);
}
