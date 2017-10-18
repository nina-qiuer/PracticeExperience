package com.tuniu.qms.report.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.report.dto.CmpQcBUStatDto;
import com.tuniu.qms.report.model.CmpQcBUStat;

public interface CmpQcBUStatService extends BaseService<CmpQcBUStat, CmpQcBUStatDto> {
	
	/**
	 * 部门合计汇总
	 * @param dataList
	 * @return
	 */
	CmpQcBUStat departmentTotal(List<CmpQcBUStat> dataList);
	
}
