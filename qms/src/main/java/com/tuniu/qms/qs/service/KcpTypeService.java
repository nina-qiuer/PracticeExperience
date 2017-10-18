package com.tuniu.qms.qs.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.KcpTypeDto;
import com.tuniu.qms.qs.model.KcpType;

public interface KcpTypeService extends BaseService<KcpType,KcpTypeDto >{

	int getKcpTypeIsExist(KcpTypeDto dto);
	
}
