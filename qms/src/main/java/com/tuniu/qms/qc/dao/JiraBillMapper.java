package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.model.JiraBill;

public interface JiraBillMapper  extends BaseMapper<JiraBill, JiraBillDto> {

	void addJira(List<JiraBill> list);

	int getJiraIsRelation(String jiraId);

	void updateJiraRelation(Map<String, Object> map);

	List<JiraBill> listByQcId(JiraBillDto dto);

	void updateJiraByQcId(Map<String, Object> map);

	List<String> listDistMainReason();

	List<String> listDistEventClass();

	List<JiraBill>  listJira(Map<String, Object> map);
	
}
