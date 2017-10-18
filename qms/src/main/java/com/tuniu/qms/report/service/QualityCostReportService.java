package com.tuniu.qms.report.service;

import java.util.List;
import java.util.Map;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.report.dto.QualityCostReportDto;
import com.tuniu.qms.report.model.QualityCostReport;

public interface QualityCostReportService extends BaseService<QualityCostReport,QualityCostReportDto>{

	
    
    List<Map<String, Object>> getGraphByQpType(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByDep(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByJob(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByResp(QualityCostReportDto dto);
    
    List<Map<String, Object>> getTrendGraph(QualityCostReportDto dto);
   
    /**
     * 执行问题
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphImplementQp(QualityCostReportDto dto);
    
    /**
     * 责任部门
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphDep(QualityCostReportDto dto);
    
    /**
     * 责任人
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphResp(QualityCostReportDto dto); 
}
