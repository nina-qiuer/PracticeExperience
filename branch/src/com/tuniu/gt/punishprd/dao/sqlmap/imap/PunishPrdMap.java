package com.tuniu.gt.punishprd.dao.sqlmap.imap;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.punishprd.entity.PunishPrdEntity;


@Repository("pp_dao_sqlmap-punishPrd")
public interface PunishPrdMap extends IMapBase {
	/**获取同产品处罚列表中最大上线日期*/
	Date getMaxOnlineTime(Long routeId);
	
	Integer getAllwaysOfflineCount(Long routeId);
	
	/**
	 * 根据线路编号设置上线日期为空（永久下线使用）
	 * @param routeId
	 */
	void offlineByRouteId(Long routeId);
	
	/**
	 * 根据线路编号设置上线日期（计算值大于记录值时使用）
	 * @param paramMap
	 * routeId：线路编号
	 * onlineTime：上线日期
	 */
	void updateOnlineTimeByRouteId(Map<String,Object> paramMap);
	
	/**
	 * 低满意度详情中的历史记录列表
	 * @param paramMap
	 * 参数：
	 * @return
	 */
	List<PunishPrdEntity> getHistoryOfflineListOper(Map<String,Object> paramMap);
	
	/**
	 * 获得最大下线次数
	 * @param paramMap
	 * 【必选】
	 * routeId：线路编号
	 * offlineType：下线类型
	 * 【可选】
	 * triggerTime：触发时间--小于该触发时间
	 * @return
	 */
	Integer getMaxOfflineCount(Map<String,Object> paramMap);
	
	/**
	 * 获取历史下线日期列表
	 * @param paramMap
	 * routeId:线路编号
	 * offlineType:下线类型
	 * triggerTime：触发时间（查询该日期之前的历史记录）
	 * @return
	 */
	List<Date> getOfflineHistoryDateList(Map<String,Object> paramMap);
	
	/**
	 * 获取上个月满足低质量条件的线路列表
	 * 条件1：上个月两次触红下线
	 * 条件2：上个月一次低满意度下线
	 * @param paramMap
	 * 必选参数
	 * offlineType:下线类型   1-触红 2-低满意度
	 * @return
	 */
	List<Long> getPreMonthOfflineIds(Map<String,Object> paramMap);
	
	/**
	 * 获取低质量下线原因信息
	 * 1.触红原因：低质量下线触发时间的上个自然月两次执行过下线操作的线路
	 * @param paramMap
	 * @return
	 */
	List<PunishPrdEntity> getOfflineCauseInfo(Map<String,Object> paramMap);
	
	/**
	 * 触发下线条件时需要排除的线路列表，不同下线类型不同
	 * 低满意：线路在整改中的或者上周执行过上线操作的，本次不计入列表
	 * @param paramMap
	 * @return
	 */
	List<Long> queryExcludeRouteIds(Map<String,Object> paramMap);
	
	Integer getListCount(Map<String,Object> paramMap);

}
