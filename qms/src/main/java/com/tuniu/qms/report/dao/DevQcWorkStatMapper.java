package com.tuniu.qms.report.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.report.dto.DevQcWorkStatDto;
import com.tuniu.qms.report.model.DevQcWorkStat;

public interface DevQcWorkStatMapper extends BaseMapper<DevQcWorkStat, DevQcWorkStatDto> {
	
	DevQcWorkStat getCombine(DevQcWorkStatDto dto);
	
}
