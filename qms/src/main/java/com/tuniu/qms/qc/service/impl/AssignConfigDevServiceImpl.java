package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.jira.dao.JiraIssueBillMapper;
import com.tuniu.qms.qc.dao.AssignConfigDevMapper;
import com.tuniu.qms.qc.dto.AssignConfigDevDto;
import com.tuniu.qms.qc.model.AssignConfigDev;
import com.tuniu.qms.qc.model.JiraProject;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.model.QcPersonProjectDev;
import com.tuniu.qms.qc.service.AssignConfigDevService;

@Service
public class AssignConfigDevServiceImpl implements AssignConfigDevService {

	@Autowired
	private AssignConfigDevMapper mapper;
	
	@Autowired
	private JiraIssueBillMapper jiraMapper;
	@Override
	public void add(AssignConfigDev obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		AssignConfigDev ac = mapper.get(id);
		mapper.deleteProDeploy(ac.getQcPersonId()); // 删除质检员对接的所有系统
		mapper.delete(id);
	}

	@Override
	public void update(AssignConfigDev obj) {
		mapper.update(obj);
	}

	@Override
	public AssignConfigDev get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<AssignConfigDev> list(AssignConfigDevDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(AssignConfigDevDto dto) {
		
	}

	@Override
	public List<QcPersonProjectDev> getProjectDeployList() {
		return mapper.getProjectDeployList();
	}

	@Override
	public void addProjectDeploy(Integer qcPersonId, String qcPersonName, String projectIds) {
		
		  mapper.deleteProDeploy(qcPersonId); // 先删除质检员对接的所有系统
		
		  String[] projectId = projectIds.split(",");
		  List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		  for(int i = 0;i<projectId.length;i++){
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("qcPersonId", qcPersonId);
				map.put("qcPersonName", qcPersonName);
			    map.put("projectId", projectId[i]);
				dataList.add(map);
			}	
			mapper.addProDeploy(dataList); // 再添加质检员对接的所有系统
	}

	@Override
	public QcPersonDockDepCmp getDockDepCmp(Map<String, Object> map) {
		
		
		return mapper.getDockDepCmp(map);
	}

	@Override
	public int getUserIsExist(String qcPersonName) {
		
		
		return mapper.getUserIsExist(qcPersonName);
	}

	@Override
	public List<JiraProject> getProjectFromJira() {
		
		return jiraMapper.getProjectFromJira();
	}

}
