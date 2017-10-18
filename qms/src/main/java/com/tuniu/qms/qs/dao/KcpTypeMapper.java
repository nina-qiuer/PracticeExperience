package com.tuniu.qms.qs.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.KcpTypeDto;
import com.tuniu.qms.qs.model.KcpType;

public interface KcpTypeMapper extends BaseMapper<KcpType, KcpTypeDto> {

	
	 int getKcpTypeIsExist(KcpTypeDto dto);
}
