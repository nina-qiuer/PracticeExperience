package com.tuniu.qms.qs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.PunishPrdDto;
import com.tuniu.qms.qs.model.PunishPrd;

public interface PunishPrdMapper extends BaseMapper<PunishPrd, PunishPrdDto> {

	/**
	 * 触发下线条件时需要排除的线路列表，不同下线类型不同
	 * 低满意：线路在整改中的或者上周执行过上线操作的，本次不计入列表
	 * @param paramMap
	 * @return
	 */
	 List<Long> queryExcludeRouteIds(Map<String, Object> paramMap);
	 
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
	Map<String, Object> getMaxOfflineCount(Map<String,Object> paramMap);
	
    Integer getAllwaysOfflineCount(Integer routeId);
    
    /**获取同产品处罚列表中最大上线日期*/
    Date getMaxOnlineTime(Integer routeId);
    
    /**
	 * 根据线路编号设置上线日期为空（永久下线使用）
	 * @param routeId
	 */
    void offlineByRouteId(Integer routeId);
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
	List<PunishPrd> getHistoryOfflineListOper(Map<String,Object> paramMap);
	
	List<PunishPrd> listByTouchRed(Map<String,Object> paramMap);
	
	int exportCount(PunishPrdDto dto);
	
	List<PunishPrd> exportList(PunishPrdDto dto);
	
	/**
	 * 获得未下线历史记录
	 * @param paramMap
	 * @return
	 */
	List<PunishPrd> getHistoryPasslineListOper(Map<String, Object> paramMap);
}
