package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.infobird.vdnlog.entity.CriticalReportEntity;


/**
 * 
 * @author chenhaitao
 *
 */
public interface ICenterReportService {

	
	/**
	 * 查询统计数据
	 * @param map
	 * @return
	 */
	List<CriticalReportEntity> queryCriticalReport(Map<String, Object> map);
	/**
	 * 查询总数
	 * @param map
	 * @return
	 */
	int queryCriticalReportCount(Map<String, Object> map);
	
	
	/**
   	 * 插入统计表
   	 * @param entity
   	 */
   	void insertCriticalReport(List<CriticalReportEntity> list);
   	/**
   	 * 插入更新统计表
   	 * @param list
   	 */
   	void insertUpCriticalReport(CriticalReportEntity entity);
}
