package com.tuniu.qms.report.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.report.dto.QcPunishReportDto;
import com.tuniu.qms.report.model.QcPunishReport;

public interface QcPunishReportService extends BaseService<QcPunishReport, QcPunishReportDto>{
	
	/**
	 * 质检类型纬度统计记分总数
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByQcType(QcPunishReportDto dto);
	
	/**
	 * 关联部门纬度统计记分总数
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByDep(QcPunishReportDto dto);
	
	/**
	 * 关联岗位纬度统计记分总数
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByJob(QcPunishReportDto dto);

	/**
	 * 被处罚人纬度统计记分总数
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByPunishPerson(QcPunishReportDto dto);
	
	/**
	 * 处罚记分趋势图
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getscoreSumTrendGrah(QcPunishReportDto dto);
	
	/**
	 * 处罚次数趋势图
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeTrendGrah(QcPunishReportDto dto);
	
	/**
	 * 质检类型记分次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByQcType(QcPunishReportDto dto);
	
	/**
	 * 部门记分次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByDep(QcPunishReportDto dto);
	
	/**
	 * 被处罚人记分次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByPunPerson(QcPunishReportDto dto);
	
	/**
	 * 根据质检id删除处罚单切片表
	 * @param qcId
	 */
	void deleteByQcId(Integer qcId);

}
