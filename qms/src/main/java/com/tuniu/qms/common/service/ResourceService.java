package com.tuniu.qms.common.service;

import java.util.List;

import com.tuniu.qms.common.dto.ResourceDto;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.service.base.BaseService;

public interface ResourceService extends BaseService<Resource, ResourceDto> {
	
	List<Resource> getResDataCache();
	
}
