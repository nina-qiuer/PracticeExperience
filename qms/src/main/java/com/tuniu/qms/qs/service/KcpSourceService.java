package com.tuniu.qms.qs.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.KcpSourceDto;
import com.tuniu.qms.qs.model.KcpSource;

public interface KcpSourceService extends BaseService<KcpSource,KcpSourceDto >{

	int getKcpSourceIsExist(KcpSourceDto dto);
	
}
