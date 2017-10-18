package com.tuniu.gt.punishprd.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.bi.gmvrank.entity.GMVRankEntity;
import com.tuniu.bi.gmvrank.service.IGMVRankService;
import com.tuniu.bi.prdweeksatisfy.dao.impl.PrdWeekSatisfyDao;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

public class ScanOfflineRouteTask {
	
	private static Logger logger = Logger.getLogger(ScanOfflineRouteTask.class);
	
	@Autowired
	@Qualifier("bi_dao_impl-prdWeekSatisfy")
	private PrdWeekSatisfyDao prdWeekSatisfyDao;
	
	@Autowired
	@Qualifier("bi_service_gmvrank_impl-GMVRank")
	private IGMVRankService gmvRankingService;
	
	@Autowired
	@Qualifier("pp_service_impl-punishprd")
	private IPunishPrdService punishPrdService;
	
	public void lowSatyRouteRule(){
		
		/*
		 * 获取符合低满意度规则的线路列表
		 * 规则：
		 * 1.非牛人专线连续两周满意度不大于75%
		 * 2.牛人专线连续两周满意度不大于90%
		 */
		
		logger.info("触发低满意度规则");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("curWeek", DateUtil.getField(Calendar.WEEK_OF_YEAR));
		paramMap.put("curYear", DateUtil.getField(Calendar.YEAR));
		List<Long> routeIds = prdWeekSatisfyDao.listLowRouteIds(paramMap);
		logger.info("触发低满意度规则,扫描线路数为："+routeIds.size()+",触发日期："+DateUtil.formatDate(new Date()));
		logger.info("触发低满意度规则,扫描线路数："+routeIds);
		if( CollectionUtil.isNotEmpty(routeIds) ) {
			punishPrdService.lowSatisfactionDeal(routeIds);
		}
	}
	
	public void lowQuaRouteRule(){
		
		List<GMVRankEntity> gmvRankList = (List<GMVRankEntity>) gmvRankingService.fetchList(); // 获取上个月gmv排名数据
		
		if(CollectionUtil.isNotEmpty(gmvRankList)) {
			punishPrdService.lowQualityDeal(gmvRankList); // 对满足低质量规则对线路进行下线处理
		}
	}
}
