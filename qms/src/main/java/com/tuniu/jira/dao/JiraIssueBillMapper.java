package com.tuniu.jira.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.model.JiraProject;

public interface JiraIssueBillMapper {
	
	List<JiraBill> getJiraSource(Map<String, Object> map);
	
	//获取问题主要原因','问题原因明细','解决方案','严重等级'
	String getJiraMessage(Map<String, Object> map);
	
	//获取研发处理人 需求提出人
	String getJiraPeople(Map<String, Object> map);
	/**
	 * 获取公司所有系统
	 * @return
	 */
	List<JiraProject> getProjectFromJira();
}
