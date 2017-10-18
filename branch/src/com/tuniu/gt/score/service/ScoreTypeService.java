package com.tuniu.gt.score.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.score.entity.ScoreTypeEntity;

public interface ScoreTypeService extends IServiceBase {
	
	public List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> getScoreTypeList();
	
	ScoreTypeEntity getScoreByName(Map<String,Object> map);
}
