package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.AssignConfigDevDto;
import com.tuniu.qms.qc.model.AssignConfigDev;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.model.QcPersonProjectDev;

public interface AssignConfigDevMapper extends BaseMapper<AssignConfigDev, AssignConfigDevDto> {
	
	int getUserIsExist(String qcPersonName);
	
	List<QcPersonProjectDev> getProjectDeployList();
	
	void deleteProDeploy(Integer qcPersonId);
	
	void addProDeploy(List<Map<String, Object>> dataList);

	QcPersonDockDepCmp  getDockDepCmp(Map<String, Object> map);
}
