package com.tuniu.qms.qs.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.CompSatisRateMonitorDto;
import com.tuniu.qms.qs.model.CompSatisRateMonitor;

public interface CompSatisRateMonitorMapper extends BaseMapper<CompSatisRateMonitor, CompSatisRateMonitorDto> {

	void deleteByAddDate(java.sql.Date addDate);
	
	void addBatch(List<CompSatisRateMonitor> compList);
	
	 CompSatisRateMonitor getDepSatisfy(CompSatisRateMonitorDto dto); 
	 
	 List<String> getAllDep() ;
	 
	 List<CompSatisRateMonitor> getTrendGraph(CompSatisRateMonitorDto dto);
	 
	 List<CompSatisRateMonitor> getNationalGraph(CompSatisRateMonitorDto dto); 
	 
	 List<String> getBusinessUnit();
}
