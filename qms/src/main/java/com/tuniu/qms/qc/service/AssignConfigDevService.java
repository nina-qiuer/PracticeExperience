package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.AssignConfigDevDto;
import com.tuniu.qms.qc.model.AssignConfigDev;
import com.tuniu.qms.qc.model.JiraProject;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.model.QcPersonProjectDev;

public interface AssignConfigDevService extends BaseService<AssignConfigDev, AssignConfigDevDto>{
	
	int getUserIsExist(String  qcPersonName);
	
	/**
	 * 到jira系统获取公司所有系统列表
	 * @return
	 */
	List<JiraProject> getProjectFromJira();
	
	List<QcPersonProjectDev> getProjectDeployList();
	
	void addProjectDeploy(Integer qcPersonId, String qcPersonName, String projectIds);

	QcPersonDockDepCmp getDockDepCmp(Map<String, Object> map);
}
