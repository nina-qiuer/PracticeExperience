package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.CompSatisRateMonitorDto;
import com.tuniu.qms.qs.model.CompSatisRateMonitor;

public interface CompSatisRateMonitorService extends BaseService<CompSatisRateMonitor, CompSatisRateMonitorDto>{
	
	/** 综合满意度达成率监控快照* */
	void createCompStaisRateSnapshot();
	
	void createCompSatisfyAll();
	
	void  sendEmail(String reEmails,String ccEmails, String subject,User user, CompSatisRateMonitorDto dto);
	
	List<String> getAllDep();
	
	/**
	 * 同比
	 * @param dto
	 * @return
	 */
    List<Map<String, Object>> getTrendGraph(CompSatisRateMonitorDto dto);
    
    List<Map<String, Object>> getNationalGraph(CompSatisRateMonitorDto dto);
 
    List<String> getBusinessUnit(); 
}
