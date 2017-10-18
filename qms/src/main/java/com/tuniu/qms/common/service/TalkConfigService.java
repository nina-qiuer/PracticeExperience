package com.tuniu.qms.common.service;

import com.tuniu.qms.common.dto.TalkConfigDto;
import com.tuniu.qms.common.model.TalkConfig;
import com.tuniu.qms.common.service.base.BaseService;

public interface TalkConfigService extends BaseService<TalkConfig,TalkConfigDto> {

	TalkConfig getByType(TalkConfigDto dto);
}