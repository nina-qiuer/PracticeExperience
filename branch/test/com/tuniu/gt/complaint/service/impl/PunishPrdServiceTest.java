package com.tuniu.gt.complaint.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.bi.prdweeksatisfy.dao.impl.PrdWeekSatisfyDao;
import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.punishprd.service.impl.PunishPrdServiceImpl;
import com.tuniu.gt.punishprd.task.ScanOfflineRouteTask;
import com.tuniu.gt.toolkit.DateUtil;



public class PunishPrdServiceTest  extends TestCaseExtend 
{
		
	IPunishPrdService punishPrdService = (IPunishPrdService)Bean.get("pp_service_impl-punishprd");
	ComplaintTSPServiceImpl complaintTSPServiceImpl = (ComplaintTSPServiceImpl)Bean.get("tspService");
	ScanOfflineRouteTask scanOfflineRouteTask = (ScanOfflineRouteTask) Bean.get("getLowSatisfyRouteTask");
	PrdWeekSatisfyDao prdWeekSatisfyDao =(PrdWeekSatisfyDao) Bean.get("bi_dao_impl-prdWeekSatisfy");
		
		@Test
		public void testTsp(){
			complaintTSPServiceImpl.getProductInfo(287094L);
		}
		
		/**
		 * 触发低满意度任务
		 */
		@Test
		public void testLowSatisfyRouteTask(){
			scanOfflineRouteTask.lowSatyRouteRule();
		}
		
		@Test
		public void testLowQualityTask() throws Exception {
			scanOfflineRouteTask.lowQuaRouteRule();
		}
		
		/**
		 * 从bi获取满足低满意度规则的线路列表
		 * 1：牛人专线连续两个周满意度低于90%
		 * 2：非牛人专线连续两个周满意度低于75%
		 * 
		 * PS:注意当前周为1或者2的情况，要特殊处理
		 */
		@Test
		public void testListLowRouteIds(){
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("curWeek", 1);
			paramMap.put("curYear", DateUtil.getField(Calendar.YEAR));
			List<Long> routeIds = prdWeekSatisfyDao.listLowRouteIds(paramMap);
			System.out.println(routeIds.size());
			
			paramMap.clear();
			paramMap.put("curWeek", 2);
			paramMap.put("curYear", DateUtil.getField(Calendar.YEAR));
			routeIds = prdWeekSatisfyDao.listLowRouteIds(paramMap);
			System.out.println(routeIds.size());
			
			paramMap.clear();
			paramMap.put("curWeek", 3);
			paramMap.put("curYear", DateUtil.getField(Calendar.YEAR));
			routeIds = prdWeekSatisfyDao.listLowRouteIds(paramMap);
			System.out.println(routeIds.size());
		}
		
		@Test
		public void testGetLowSatisfactionDetail() throws Exception {
			punishPrdService.getLowSatisfactionDetail(54);
		}
		
}
