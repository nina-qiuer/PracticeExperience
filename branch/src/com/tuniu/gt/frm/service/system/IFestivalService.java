package com.tuniu.gt.frm.service.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.frm.entity.FestivalEntity;
public interface IFestivalService extends IServiceBase  {
	
	List<String> getFYearList();
	
	List<FestivalEntity> getFesNameList();
	
	void batchInsert(List<FestivalEntity> entList);
	
	void delFes(Map<String, Object> params);
	
	/**
	 * 查询当前时间开始N个工作日后的结束时间点
	 * @param dateNum
	 * @return
	 */
	Date getWorkDaysEndTime(int dateNum);
	
	/**
	 * 查询从某个时间点开始N个工作日后的结束时间点
	 * @param dateNum
	 * @return
	 */
	Date getWorkDaysEndTime(int dateNum, Date bgnTime);
	
}
