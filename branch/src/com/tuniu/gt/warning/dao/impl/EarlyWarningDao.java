package com.tuniu.gt.warning.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.warning.dao.sqlmap.imap.EarlyWarningMap;
import com.tuniu.gt.warning.entity.EarlyWarningEntity;

@Repository("warning_dao_impl-early_warning")
public class EarlyWarningDao extends DaoBase<EarlyWarningEntity, EarlyWarningMap> implements EarlyWarningMap {
	
	public EarlyWarningDao() {
		tableName = "ew_early_warning";		
	}

	@Autowired
	@Qualifier("warning_dao_sqlmap-early_warning")
	public void setMapper(EarlyWarningMap mapper) {
		this.mapper = mapper;
	}

}
