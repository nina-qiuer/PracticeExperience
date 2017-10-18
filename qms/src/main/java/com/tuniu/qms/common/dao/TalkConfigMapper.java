package com.tuniu.qms.common.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.TalkConfigDto;
import com.tuniu.qms.common.model.TalkConfig;

public interface TalkConfigMapper extends BaseMapper<TalkConfig,TalkConfigDto> {

	TalkConfig getByType(TalkConfigDto dto);
}