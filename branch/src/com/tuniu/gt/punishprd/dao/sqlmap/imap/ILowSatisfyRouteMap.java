package com.tuniu.gt.punishprd.dao.sqlmap.imap;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("pp_dao_sqlmap-lowSatisfyRoute")
public interface ILowSatisfyRouteMap  extends IMapBase{
	
	List<Long> getLowSatisfyRouteIds(int curWeek);
	
	/**
	 * 获取最大添加时间
	 * @return
	 */
	Date getMaxAddTime();
	
}
