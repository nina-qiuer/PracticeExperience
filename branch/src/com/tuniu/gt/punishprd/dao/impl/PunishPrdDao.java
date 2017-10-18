package com.tuniu.gt.punishprd.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.punishprd.dao.sqlmap.imap.PunishPrdMap;
import com.tuniu.gt.punishprd.entity.PunishPrdEntity;

@Repository("pp_dao_impl-punish_prd")
public class PunishPrdDao extends DaoBase<PunishPrdEntity, PunishPrdMap> implements PunishPrdMap{
	public PunishPrdDao(){
		tableName = "pp_punish_prd";
	}
	
	@Autowired
	@Qualifier("pp_dao_sqlmap-punishPrd")
	public void setMapper(PunishPrdMap mapper) {
		this.mapper = mapper;
	}
	
	public Date getMaxOnlineTime(Long routeId){
		return mapper.getMaxOnlineTime(routeId);
	}
	
	public Integer getAllwaysOfflineCount(Long routeId){
		return mapper.getAllwaysOfflineCount(routeId);
	}
	
	public void offlineByRouteId(Long routeId){
		mapper.offlineByRouteId(routeId);
	}
	
	public void updateOnlineTimeByRouteId(Map<String,Object> paramMap){
		mapper.updateOnlineTimeByRouteId(paramMap);
	}

	@Override
	public List<PunishPrdEntity> getHistoryOfflineListOper(Map<String,Object> paramMap) {
		return mapper.getHistoryOfflineListOper(paramMap);
	}

	@Override
	public Integer getMaxOfflineCount(Map<String,Object> paramMap) {
		return mapper.getMaxOfflineCount(paramMap);
	}

	@Override
	public List<Date> getOfflineHistoryDateList(Map<String, Object> paramMap) {
		return mapper.getOfflineHistoryDateList(paramMap);
	}

	@Override
	public List<Long> getPreMonthOfflineIds(Map<String, Object> paramMap) {
		return mapper.getPreMonthOfflineIds(paramMap);
	}

	@Override
	public List<PunishPrdEntity> getOfflineCauseInfo(
			Map<String, Object> paramMap) {
		return  mapper.getOfflineCauseInfo(paramMap);
	}

	@Override
	public List<Long> queryExcludeRouteIds(Map<String, Object> paramMap) {
		return mapper.queryExcludeRouteIds(paramMap);
	}

	@Override
	public Integer getListCount(Map<String, Object> paramMap) {
		return mapper.getListCount(paramMap);
	}
	
	
}
