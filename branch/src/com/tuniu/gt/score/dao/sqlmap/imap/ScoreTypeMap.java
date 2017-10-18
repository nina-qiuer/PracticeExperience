package com.tuniu.gt.score.dao.sqlmap.imap;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.score.entity.ScoreTypeEntity;

import tuniu.frm.core.IMapBase;


@Repository("score_dao_sqlmap-score_type")
public interface ScoreTypeMap extends IMapBase {
	
	
	ScoreTypeEntity getScoreByName(Map<String,Object> map);
}
