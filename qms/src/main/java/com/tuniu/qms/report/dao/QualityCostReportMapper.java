package com.tuniu.qms.report.dao;
import java.util.List;
import java.util.Map;
import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.report.dto.QualityCostReportDto;
import com.tuniu.qms.report.model.QualityCostReport;

public interface QualityCostReportMapper extends BaseMapper<QualityCostReport, QualityCostReportDto> {

	/**
	 * 批量插入质量问题切片
	 * @param list
	 */
	void addBatch (List<QualityCostReport> list);
	
	
    List<Map<String, Object>> getGraphByQpType(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByDep(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByResp(QualityCostReportDto dto);
    
    List<Map<String, Object>> getGraphByJob(QualityCostReportDto dto);  
    
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