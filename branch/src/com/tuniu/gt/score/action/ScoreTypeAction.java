package com.tuniu.gt.score.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.score.entity.ScoreTypeEntity;
import com.tuniu.gt.score.service.ScoreTypeService;


@Service("score_action-score_type")
@Scope("prototype")
public class ScoreTypeAction extends FrmBaseAction<ScoreTypeService, ScoreTypeEntity> {

	private static final long serialVersionUID = 1L;
	
	public ScoreTypeAction() {
		setManageUrl("score_type");
	}
	
	@Autowired
	@Qualifier("score_service_impl-score_type")
	public void setService(ScoreTypeService service) {
		this.service = service;
	};
	
	private List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> scoreTypeList;
	
	private List<ScoreTypeEntity> scoreTypes;
	
	public List<ScoreTypeEntity> getScoreTypes() {
		return scoreTypes;
	}

	public void setScoreTypes(List<ScoreTypeEntity> scoreTypes) {
		this.scoreTypes = scoreTypes;
	}

	public List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> getScoreTypeList() {
		return scoreTypeList;
	}

	public void setScoreTypeList(
			List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> scoreTypeList) {
		this.scoreTypeList = scoreTypeList;
	}

	public String execute() {
		scoreTypeList = service.getScoreTypeList();
		return "score_type_config";
	}
	
	@SuppressWarnings("unchecked")
	public String toAdd() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", 0);
		scoreTypes = (List<ScoreTypeEntity>) service.fetchList(params);
		return "score_type_form";
	}
	
	public String add() {
		service.insert(entity);
		return "score_type_form";
	}
	
	@SuppressWarnings("unchecked")
	public String toUpdate() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", 0);
		scoreTypes = (List<ScoreTypeEntity>) service.fetchList(params);
		entity = (ScoreTypeEntity) service.get(entity.getId());
		return "score_type_form";
	}
	
	public String update() {
		service.update(entity);
		return "score_type_form";
	}
	
	public String delete() {
		service.delete(entity.getId());
		return "score_type_config";
	}
	
}
