package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.sqlmap.imap.CenterReportMapper;
import com.tuniu.gt.complaint.service.ICenterReportService;
import com.tuniu.infobird.vdnlog.entity.CriticalReportEntity;

/**
 * 
 * @author chenhaitao
 *
 */
@Service("centerReportService")
public class CenterReportServiceImpl implements ICenterReportService{

	private static Logger logger = Logger.getLogger(CenterReportServiceImpl.class);
	@Autowired
	private CenterReportMapper centerReportMapper;
	
	/**
	 * 查询某个周统计数据
	 */
	public List<CriticalReportEntity> queryCriticalReport(Map<String, Object> map) {
		
		try {
			
			return centerReportMapper.queryCriticalReport(map);
			
		} catch (Exception e) {
			
			logger.error("查询某个周统计数据失败："+e);
			return null;
		}
		
	}

	/**
	 * 查询总数
	 */
	public int queryCriticalReportCount(Map<String, Object> map) {
		
		try {
			
			return centerReportMapper.queryCriticalReportCount(map);
			
		} catch (Exception e) {

			logger.error("查询某个周统计总数："+e);
			return 0;
		}
		
	}

	
	/**
	 * 插入统计表
	 */
	public void insertCriticalReport(List<CriticalReportEntity> list) {
		
		centerReportMapper.insertCriticalReport(list);
		
	}

	/**
	 * 插入更新统计表
	 */
	public void insertUpCriticalReport(CriticalReportEntity entity) {
		
		centerReportMapper.insertUpCriticalReport(entity);
		
	}
	
}
