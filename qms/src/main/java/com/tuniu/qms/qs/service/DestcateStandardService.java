package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.DestcateStandardDto;
import com.tuniu.qms.qs.model.DestcateStandard;

public interface DestcateStandardService extends BaseService<DestcateStandard, DestcateStandardDto>{
	
	List<DestcateStandard> getDestStdCache();
	
}
