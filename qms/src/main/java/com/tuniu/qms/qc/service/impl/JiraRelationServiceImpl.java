package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.JiraBillMapper;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.service.JiraRelationService;

@Service
public class JiraRelationServiceImpl implements JiraRelationService{

	@Autowired
	private JiraBillMapper mapper;
	
	@Override
	public void add(JiraBill obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(JiraBill obj) {
		mapper.update(obj);
	}

	@Override
	public JiraBill get(Integer id) {
		return null;
	}

	@Override
	public List<JiraBill> list(JiraBillDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(JiraBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	/**
	 * 批量插入jira单数据
	 */
	public void addJira(List<JiraBill> list) {
		
		mapper.addJira(list);
		
	}

	/**
	 * 校验该jira单是否被关联
	 */
	public int getJiraIsRelation(String jiraId) {
		
		return mapper.getJiraIsRelation(jiraId);
	}

	@Override
	public void updateJiraRelation(Map<String, Object> map) {
		
		mapper.updateJiraRelation(map);
	}

	@Override
	public List<JiraBill> listByQcId(JiraBillDto dto) {
		
		return mapper.listByQcId(dto);
	}

	@Override
	public List<String> listDistMainReason() {
		return mapper.listDistMainReason();
	}

	@Override
	public List<String> listDistEventClass() {
		return mapper.listDistEventClass();
	}

	@Override
	public List<JiraBill> listJira(Map<String, Object> map) {
		
		return mapper.listJira(map);
	}

	
	
}
