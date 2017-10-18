package com.tuniu.qms.qs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qs.dao.TargetConfigMapper;
import com.tuniu.qms.qs.dto.TargetConfigDto;
import com.tuniu.qms.qs.model.TargetConfig;
import com.tuniu.qms.qs.service.TargetConfigService;

@Service
public class TargetConfigServiceImpl implements TargetConfigService {

	
	@Autowired 
	private TargetConfigMapper mapper;
	@Override
	public void add(TargetConfig obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(TargetConfig obj) {
		
		mapper.update(obj);
	}

	@Override
	public TargetConfig get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<TargetConfig> list(TargetConfigDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(TargetConfigDto dto) {
		
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	
	/**
	 * 批量更新组织架构目标值
	 */
	public void updateBatchTarget(TargetConfig target) {

		mapper.updateBatchTarget(target);
	}

	@Override
	public List<String> getAllOldDep() {

		return mapper.getAllOldDep();
	}

	@Override
	public void addBatch(List<TargetConfig> list) {
		
		mapper.addBatch(list);
	}

	@Override
	public List<String> getAllDep() {
	
		return mapper.getAllDep();
	}

	
}
