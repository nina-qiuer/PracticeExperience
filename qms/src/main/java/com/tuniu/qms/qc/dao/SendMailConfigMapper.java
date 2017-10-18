package com.tuniu.qms.qc.dao;

import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.SendMailConfigDto;
import com.tuniu.qms.qc.model.SendMailConfig;

public interface SendMailConfigMapper extends BaseMapper<SendMailConfig,SendMailConfigDto> {
	
	int getExistConfig(Map<String, Object> map);

	SendMailConfig getByType(String mailType);
}