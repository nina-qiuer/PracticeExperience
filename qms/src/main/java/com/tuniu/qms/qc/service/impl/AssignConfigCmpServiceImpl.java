package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.AssignConfigCmpMapper;
import com.tuniu.qms.qc.dto.AssignConfigCmpDto;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.service.AssignConfigCmpService;

@Service
public class AssignConfigCmpServiceImpl implements AssignConfigCmpService {

	@Autowired
	private AssignConfigCmpMapper mapper;
	
	@Override
	public void add(AssignConfigCmp obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		AssignConfigCmp ac = mapper.get(id);
		mapper.deleteDockdeps(ac.getQcPersonId()); // 删除质检员对接的所有部门
		mapper.delete(id);
	}

	@Override
	public void update(AssignConfigCmp obj) {
		mapper.update(obj);
	}

	@Override
	public AssignConfigCmp get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<AssignConfigCmp> list(AssignConfigCmpDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(AssignConfigCmpDto dto) {
		
	}

	@Override
	public List<Map<String, Object>> getDockdepList() {
		return mapper.getDockdepList();
	}

	@Override
	public void addDockdeps(Integer qcPersonId, String qcPersonName, String[] dockdepIds) {
		mapper.deleteDockdeps(qcPersonId); // 先删除质检员对接的所有部门
		
		if (null != dockdepIds && dockdepIds.length > 0) {
			List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
			for (String dockdepId : dockdepIds) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("qcPersonId", qcPersonId);
				map.put("qcPersonName", qcPersonName);
				map.put("dockdepId", dockdepId);
				dataList.add(map);
			}
			mapper.addDockdeps(dataList); // 再添加质检员对接的所有部门
		}
	}

	@Override
	public AssignConfigCmp getDepDockQcPerson(Integer prdTeamId) {
		return mapper.getDepDockQcPerson(prdTeamId);
	}

	@Override
	public AssignConfigCmp getNoOrderQcPerson() {
		return mapper.getNoOrderQcPerson();
	}

	@Override
	public QcPersonDockDepCmp getDock(Integer id) {
		
		return mapper.getDock(id);
	}

	@Override
	public AssignConfigCmp getQcPersonByName(String qcPersonName) {
		return mapper.getQcPersonByName(qcPersonName);
	}

}
