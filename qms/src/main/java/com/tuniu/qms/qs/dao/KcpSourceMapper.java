package com.tuniu.qms.qs.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.KcpSourceDto;
import com.tuniu.qms.qs.model.KcpSource;

public interface KcpSourceMapper extends BaseMapper<KcpSource, KcpSourceDto> {

	
	 int getKcpSourceIsExist(KcpSourceDto dto);
}
