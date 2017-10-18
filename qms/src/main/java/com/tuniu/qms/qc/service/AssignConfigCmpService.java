package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.AssignConfigCmpDto;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;

public interface AssignConfigCmpService extends BaseService<AssignConfigCmp, AssignConfigCmpDto>{
	
	List<Map<String, Object>> getDockdepList();
	
	void addDockdeps(Integer qcPersonId, String qcPersonName, String[] dockdepIds);

	AssignConfigCmp getDepDockQcPerson(Integer prdTeamId);
	
	AssignConfigCmp getNoOrderQcPerson();
	
	QcPersonDockDepCmp getDock(Integer id);
	
	/**
	 * 根据质检员姓名查询质检员
	 * @param stringCellValue
	 * @return
	 */
	AssignConfigCmp getQcPersonByName(String qcPersonName);
}
