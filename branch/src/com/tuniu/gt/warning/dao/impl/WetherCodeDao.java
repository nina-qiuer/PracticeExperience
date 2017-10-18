package com.tuniu.gt.warning.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.warning.dao.sqlmap.imap.WetherCodeMap;
import com.tuniu.gt.warning.entity.EarlyWarningEntity;

@Repository("warning_dao_impl-wether_code")
public class WetherCodeDao extends DaoBase<EarlyWarningEntity, WetherCodeMap> implements WetherCodeMap {
	
	public WetherCodeDao() {
		tableName = "ew_wether_code";		
	}

	@Autowired
	@Qualifier("warning_dao_sqlmap-wether_code")
	public void setMapper(WetherCodeMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void keywordSet(Map<String, Object> params) {
		mapper.keywordSet(params);
	}

}
