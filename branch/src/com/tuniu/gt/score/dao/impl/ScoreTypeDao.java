package com.tuniu.gt.score.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.score.dao.sqlmap.imap.ScoreTypeMap;
import com.tuniu.gt.score.entity.ScoreTypeEntity;


@Repository("score_dao_impl-score_type")
public class ScoreTypeDao extends DaoBase<ScoreTypeEntity, ScoreTypeMap> implements ScoreTypeMap {

	public ScoreTypeDao() {
		tableName = "sr_score_type";		
	}

	@Autowired
	@Qualifier("score_dao_sqlmap-score_type")
	public void setMapper(ScoreTypeMap mapper) {
		this.mapper = mapper;
	}

	
	public ScoreTypeEntity getScoreByName(Map<String,Object> map){
		
		return mapper.getScoreByName(map);
	}
}
