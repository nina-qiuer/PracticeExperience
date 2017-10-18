package com.tuniu.bi.prdweeksatisfy.dao.sqlmap.imap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.punishprd.entity.LowSatisfyRouteEntity;

@Repository("bi_dao_sqlmap-prdWeekSatisfy")
public interface IPrdWeekSatisfyMap {
	
	List<Long> listLowRouteIds(Map<String, Object> paramMap);
	
	Double getSatisfactionByRouteIdAndWeek(Map<String,Object> paramMap);
	
	String getNiuFlagByRouteId(Long routeId);
}
