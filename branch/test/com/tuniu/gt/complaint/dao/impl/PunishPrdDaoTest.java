package com.tuniu.gt.complaint.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.punishprd.dao.impl.PunishPrdDao;
import com.tuniu.gt.toolkit.DateUtil;

public class PunishPrdDaoTest extends TestCaseExtend {

	PunishPrdDao dao = (PunishPrdDao)Bean.get("pp_dao_impl-punish_prd");
	
	/**
	 * 获取需要排除的线路id
	 */
	@Test
	public void testQueryExcludeRouteIds() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int year = DateUtil.getField(Calendar.YEAR);
		int lastWeek = 1; 
//				DateUtil.getField(Calendar.WEEK_OF_YEAR)-1;
		paramMap.put("lastWeekBgn", DateUtil.getFirstDayOfWeek(year, lastWeek));
		dao.queryExcludeRouteIds(paramMap);
	}
	
	/**
	 * 获取同线路同下线类型最大下线次数
	 * @throws Exception
	 */
	@Test
	public void testGetMaxOfflineCount() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", 28147029);
		paramMap.put("offlineType", 2);

		Integer maxOfflineCountRecord = dao.getMaxOfflineCount(paramMap);
		System.out.println(maxOfflineCountRecord == null);
	}
	
	/**
	 * 测试产品是否已经处于永久下线状态
	 * @throws Exception
	 */
	@Test
	public void testIsAllwaysOffline() throws Exception {
		boolean isAllwarysOffLine = false;
		int count = dao.getAllwaysOfflineCount(123l);
		if (count > 0) {
			isAllwarysOffLine = true;
		}
		
		System.out.println(isAllwarysOffLine);
	}
	
	/**
	 * 获取最大上线日期
	 * @throws Exception
	 */
	@Test
	public void testGetMaxOnlineTime() throws Exception {
		Date recordMaxDate = dao.getMaxOnlineTime(28147029L);
	}
	
	/**
	 * 将产品线路设为永久下线
	 * @throws Exception
	 */
	@Test
	public void testOfflineByRouteId() throws Exception {
		dao.offlineByRouteId(28147029L);
	}
	
	/**
	 * 更新线路上线时间
	 * @throws Exception
	 */
	@Test
	public void testUpdateOnlineTimeByRouteId() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", 28147029L);
		paramMap.put("onlineTime", new Date());
		dao.updateOnlineTimeByRouteId(paramMap);
	}
	
	/**
	 * 获取上个月符合低质量的触红和低满意度线路列表
	 * @throws Exception
	 */
	@Test
	public void testGetPreMonthData() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offlineType", 1);
		dao.getPreMonthOfflineIds(paramMap);
		paramMap.put("offlineType", 2);
		dao.getPreMonthOfflineIds(paramMap);
	}

}
