package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.AssignConfigCmpDto;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;

public interface AssignConfigCmpMapper extends BaseMapper<AssignConfigCmp, AssignConfigCmpDto> {
	
	List<Map<String, Object>> getDockdepList();
	
	void deleteDockdeps(Integer qcPersonId);
	
	void addDockdeps(List<Map<String, Object>> dataList);

	AssignConfigCmp getDepDockQcPerson(Integer prdTeamId);
	
	AssignConfigCmp getNoOrderQcPerson();
	
	QcPersonDockDepCmp getDock(Integer id);
	
	/**
	 * 根据质检员姓名获取质检员
	 * @param qcPersonName
	 * @return
	 */
	AssignConfigCmp getQcPersonByName(String qcPersonName);
}
