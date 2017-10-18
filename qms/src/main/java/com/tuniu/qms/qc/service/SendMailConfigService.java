package com.tuniu.qms.qc.service;

import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.SendMailConfigDto;
import com.tuniu.qms.qc.model.SendMailConfig;

public interface SendMailConfigService extends BaseService<SendMailConfig,SendMailConfigDto> {

	int getExistConfig(Map<String, Object> map);
	
	SendMailConfig getByType(String mailType);
}