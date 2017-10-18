package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.infobird.vdnlog.entity.CriticalReportEntity;

@Repository
public interface CenterReportMapper {

	
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
	   * 插入更新
	   * @param lis
	   */
	  void insertUpCriticalReport(CriticalReportEntity entity);
}
