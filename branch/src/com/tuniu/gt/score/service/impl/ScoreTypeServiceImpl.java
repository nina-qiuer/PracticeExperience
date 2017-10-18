package com.tuniu.gt.score.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.score.dao.impl.ScoreTypeDao;
import com.tuniu.gt.score.entity.ScoreTypeEntity;
import com.tuniu.gt.score.service.ScoreTypeService;


@Service("score_service_impl-score_type")
public class ScoreTypeServiceImpl extends ServiceBaseImpl<ScoreTypeDao> implements ScoreTypeService {
	
	@Autowired
	@Qualifier("score_dao_impl-score_type")
	public void setDao(ScoreTypeDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> getScoreTypeList() {
		List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> list = new ArrayList<Map<ScoreTypeEntity,List<ScoreTypeEntity>>>();
		List<ScoreTypeEntity> scoreTypeList = (List<ScoreTypeEntity>) dao.fetchList();
		for (ScoreTypeEntity type : scoreTypeList) {
			if (0 == type.getParentId()) {
				Map<ScoreTypeEntity, List<ScoreTypeEntity>> map = new HashMap<ScoreTypeEntity, List<ScoreTypeEntity>>();
				List<ScoreTypeEntity> type2List = new ArrayList<ScoreTypeEntity>();
				for (ScoreTypeEntity type2 : scoreTypeList) {
					if (type.getId() == type2.getParentId()) {
						type2List.add(type2);
					}
				}
				map.put(type, type2List);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public ScoreTypeEntity getScoreByName(Map<String,Object> map) {
	
		
		return dao.getScoreByName(map);
	}

}
