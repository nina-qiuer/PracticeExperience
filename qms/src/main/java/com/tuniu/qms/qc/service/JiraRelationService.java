package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.model.JiraBill;

public interface JiraRelationService  extends BaseService<JiraBill, JiraBillDto> {

	void addJira(List<JiraBill> list);
	
	int getJiraIsRelation(String jiraId);
	
	void updateJiraRelation(Map<String, Object> map);
	
	List<JiraBill> listByQcId(JiraBillDto dto);
	
	List<String> listDistMainReason();
	
	List<String> listDistEventClass();
	
	List<JiraBill>  listJira(Map<String, Object> map);
	
}
