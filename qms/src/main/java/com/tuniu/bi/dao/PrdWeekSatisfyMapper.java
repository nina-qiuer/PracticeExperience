package com.tuniu.bi.dao;
import java.util.List;
import java.util.Map;

public interface PrdWeekSatisfyMapper {

	 List<Long> listLowRouteIds(Map<String, Object> paramMap);
	 
	 List<Long> listLastWeekLowRoute(Map<String, Object> paramMap);

	 Double getSatisfactionByRouteIdAndWeek(Map<String,Object> paramMap);

	 String getNiuFlagByRouteId(Integer routeId);
}
