package com.tuniu.bi.prdweeksatisfy.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.bi.prdweeksatisfy.dao.sqlmap.imap.IPrdWeekSatisfyMap;
import com.tuniu.bi.prdweeksatisfy.entity.PrdWeekSatisfyEntity;
import com.tuniu.gt.punishprd.entity.LowSatisfyRouteEntity;

@Repository("bi_dao_impl-prdWeekSatisfy")
public class PrdWeekSatisfyDao extends DaoBase<PrdWeekSatisfyEntity, IPrdWeekSatisfyMap> implements IPrdWeekSatisfyMap{
	
	@Autowired
	@Qualifier("bi_dao_sqlmap-prdWeekSatisfy")
	public void setMapper(IPrdWeekSatisfyMap mapper) {
		this.mapper = mapper;
	}
	
	public List<Long> listLowRouteIds(Map<String, Object> paramMap) {
		return mapper.listLowRouteIds(paramMap);
	}
	
	public Double getSatisfactionByRouteIdAndWeek(Map<String,Object> paramMap){
		return mapper.getSatisfactionByRouteIdAndWeek(paramMap);
	}

	@Override
	public String getNiuFlagByRouteId(Long routeId) {
		return mapper.getNiuFlagByRouteId(routeId);
	}
	
}
