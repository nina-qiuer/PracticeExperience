package com.tuniu.qms.report.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.report.dto.QcPunishReportDto;
import com.tuniu.qms.report.model.QcPunishReport;

public interface QcPunishReportMapper extends BaseMapper<QcPunishReport, QcPunishReportDto>{
	
	/**
	 * 质检类型纬度统计处罚记分
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByQcType(QcPunishReportDto dto);
	
	/**
	 * 根据质检id删除处罚单
	 * @param qcId
	 */
	void deleteByQcId(Integer qcId);
	
	/**
	 * 关联部门纬度统计处罚记分
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByDep(QcPunishReportDto dto);
	
	/**
	 * 关联岗位纬度统计处罚记分
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByJob(QcPunishReportDto dto);
	
	/**
	 * 被处罚人纬度统计处罚记分
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getGraphByPunishPerson(QcPunishReportDto dto);
	
	/**
	 * 记分分数趋势图
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getscoreSumTrendGrah(QcPunishReportDto dto);
	
	/**
	 * 记分处罚次数趋势图
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeTrendGrah(QcPunishReportDto dto);
	
	/**
	 * 质检类型纬度记分处罚次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByQcType(QcPunishReportDto dto);
	
	/**
	 * 关联部门记分处罚次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByPunPerson(QcPunishReportDto dto);
	
	/**
	 * 被处罚人记分处罚次数排名
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> getPunishTimeByDep(QcPunishReportDto dto);

}
