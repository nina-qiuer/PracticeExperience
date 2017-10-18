package com.tuniu.qms.qs.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.TargetConfigDto;
import com.tuniu.qms.qs.model.TargetConfig;

public interface TargetConfigMapper extends BaseMapper<TargetConfig, TargetConfigDto> {

	
	void updateBatchTarget(TargetConfig target);
	
	List<String> getAllOldDep();
	
	void addBatch(List<TargetConfig> list);
	
	List<String> getAllDep();
	
	List<TargetConfig> getNationalTarget();
}
