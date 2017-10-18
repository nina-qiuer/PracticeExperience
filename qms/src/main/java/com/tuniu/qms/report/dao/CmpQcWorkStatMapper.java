package com.tuniu.qms.report.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.report.dto.CmpQcWorkStatDto;
import com.tuniu.qms.report.dto.CmpQcWorkStatReportDto;
import com.tuniu.qms.report.model.CmpQcWorkStat;

public interface CmpQcWorkStatMapper extends BaseMapper<CmpQcWorkStat, CmpQcWorkStatDto> {
	
	
	/**
	 * 获得分组汇总数据
	 * @param dto
	 * @return
	 */
	List<CmpQcWorkStat> getGroupTotal(CmpQcWorkStatDto dto);
	/**
	 * 获得个人数据
	 * @param dto
	 * @return
	 */
	List<CmpQcWorkStatReportDto> getPersonDataList(CmpQcWorkStatDto dto);
	
	List<String> getGroupList();
	
}
