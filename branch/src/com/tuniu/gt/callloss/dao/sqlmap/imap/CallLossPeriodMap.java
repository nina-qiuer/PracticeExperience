package com.tuniu.gt.callloss.dao.sqlmap.imap;


import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;


@Repository("callloss_dao_sqlmap-calllossPeriod")
public interface CallLossPeriodMap extends IMapBase { 

	/**
	 * 获取数据同步开始时间
	 * 表中id=1的记录，记录的是整个同步的开始时间，如2014-04-17 19:30:00表示同步这个时间之后的数据
	 * @return 
	 */
	public String getRecordBeginTime();
	
	/**
	 * 根据id设置记录的del_flag为1
	 * @return 
	 */
	public void deletePeriodById(Map map);
}
