package com.tuniu.infobird.vdnlog.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.AgentStatusLog;

/**
 * 
 * @author chenhaitao
 *
 */
@Repository("infobird_dao_sqlmap-agentStatusLog")
public interface CriticalReportMapper extends IMapBase{

	/**
	 * 查询是否在班
	 * @param map
	 * @return
	 */
	int queryIfWork(Map<String, Object> map);
	/**
	 * 查询在根据提醒30分钟之内是否打过电话
	 * @param map
	 * @return
	 */
	int queryIfCommit(Map<String, Object> map);
	/**
	 * 查询不在班第二天最早登入时间
	 * @param map
	 * @return
	 */
	int queryNextDayTime(Map<String, Object> map);
	
	/**
	 * 出游中最近时间点
	 * @param map
	 * @return
	 */
	int queryLastTime(Map<String, Object> map);
	
}
