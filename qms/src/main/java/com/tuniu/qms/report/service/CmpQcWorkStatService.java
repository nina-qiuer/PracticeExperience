package com.tuniu.qms.report.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.report.dto.CmpQcWorkStatDto;
import com.tuniu.qms.report.dto.CmpQcWorkStatReportDto;
import com.tuniu.qms.report.model.CmpQcWorkStat;

public interface CmpQcWorkStatService extends BaseService<CmpQcWorkStat, CmpQcWorkStatDto> {
	
	/**
	 * 获得报表数据
	 * @param dto
	 * @return
	 */
	Map<String, Object> getCmpQcWorkStatReport(CmpQcWorkStatDto dto);
	
}
