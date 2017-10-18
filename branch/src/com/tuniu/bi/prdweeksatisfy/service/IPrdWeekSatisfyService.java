/**
 * 
 */
package com.tuniu.bi.prdweeksatisfy.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

/**
 * @author jiangye
 * 
 */
public interface IPrdWeekSatisfyService extends IServiceBase {
	
	
	List<Long> listLowRouteIds(Map<String, Object> paramMap);

	Double getSatisfactionByRouteIdAndWeek(Map<String, Object> paramMap);

	String getNiuFlagByRouteId(Long routeId);
}
