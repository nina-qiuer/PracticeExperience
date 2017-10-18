package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.TargetConfigDto;
import com.tuniu.qms.qs.model.TargetConfig;

public interface TargetConfigService extends BaseService<TargetConfig, TargetConfigDto>{
	
	void updateBatchTarget(TargetConfig target);
	
	List<String> getAllOldDep();
	
	List<String> getAllDep();
	
	void addBatch(List<TargetConfig> list);
}
