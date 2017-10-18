package com.tuniu.gt.score.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.score.dao.sqlmap.imap.SrQcTeamMap;
import com.tuniu.gt.score.entity.SrQcTeamEntity;


@Repository("score_dao_impl-sr_qc_team")
public class SrQcTeamDao extends DaoBase<SrQcTeamEntity, SrQcTeamMap> implements SrQcTeamMap {

	public SrQcTeamDao() {
		tableName = "sr_qc_team";		
	}

	@Autowired
	@Qualifier("score_dao_sqlmap-sr_qc_team")
	public void setMapper(SrQcTeamMap mapper) {
		this.mapper = mapper;
	}

}
