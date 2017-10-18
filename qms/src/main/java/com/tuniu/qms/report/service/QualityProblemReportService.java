package com.tuniu.qms.report.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.report.dto.QualityProblemReportDto;
import com.tuniu.qms.report.model.QualityProblemReport;

public interface QualityProblemReportService extends BaseService<QualityProblemReport,QualityProblemReportDto>{

	
    void deleteByQcId(Integer qcId);
    
    List<Map<String, Object>> getGraphByQpType(QualityProblemReportDto dto);
    
    List<Map<String, Object>> getGraphByDep(QualityProblemReportDto dto);
    
    List<Map<String, Object>> getGraphByJob(QualityProblemReportDto dto);
    
    List<Map<String, Object>> getGraphByAgency(QualityProblemReportDto dto);
    
    List<Map<String, Object>> getGraphByResp(QualityProblemReportDto dto);
    
    List<Map<String, Object>> getTrendGraph(QualityProblemReportDto dto);
   
    /**
     * 执行问题
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphImplementQp(QualityProblemReportDto dto);
    /**
     * 供应商问题
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphAgencyQp(QualityProblemReportDto dto);
    
    /**
     * 责任部门
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphDep(QualityProblemReportDto dto);
    
    /**
     * 供应商
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphAgency(QualityProblemReportDto dto);
    /**
     * 内部责任人
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphInnerResp(QualityProblemReportDto dto);
    /**
     * 外部责任人
     * @param dto
     * @return
     */
    List<Map<String, Object>> getRankGraphOutResp(QualityProblemReportDto dto); 
}
