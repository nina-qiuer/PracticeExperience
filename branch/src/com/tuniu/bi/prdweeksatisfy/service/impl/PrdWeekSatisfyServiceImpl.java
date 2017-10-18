/**
 * 
 */
package com.tuniu.bi.prdweeksatisfy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.bi.gmvrank.dao.impl.GMVRankDao;
import com.tuniu.bi.gmvrank.service.IGMVRankService;
import com.tuniu.bi.prdweeksatisfy.dao.impl.PrdWeekSatisfyDao;
import com.tuniu.bi.prdweeksatisfy.service.IPrdWeekSatisfyService;

/**
 * @author jiangye
 */
@Service("bi_service_impl-prdWeekSatisfy")
public class PrdWeekSatisfyServiceImpl extends ServiceBaseImpl<PrdWeekSatisfyDao> implements IPrdWeekSatisfyService {

	@Autowired
	@Qualifier("bi_dao_impl-prdWeekSatisfy")
	public void setDao(PrdWeekSatisfyDao dao) {
		this.dao = dao;
	}


	@Override
	public List<Long> listLowRouteIds(Map<String, Object> paramMap) {
		return dao.listLowRouteIds(paramMap);
	}


	@Override
	public Double getSatisfactionByRouteIdAndWeek(Map<String, Object> paramMap) {
		return dao.getSatisfactionByRouteIdAndWeek(paramMap);
	}


	@Override
	public String getNiuFlagByRouteId(Long routeId) {
		return dao.getNiuFlagByRouteId(routeId);
	}
}
