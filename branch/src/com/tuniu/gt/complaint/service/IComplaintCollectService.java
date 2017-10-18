package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.TimePlanEntity;
import com.tuniu.gt.complaint.vo.ComplaintCollectListVo;



public interface IComplaintCollectService {

	/**
	 * 查询投诉质检汇总表数据
	 * @param map
	 * @return
	 */
	List<ComplaintCollectListVo>   getCollectList(Map<String, Object> map);
	
	/**
	 * 查询投诉质检汇总列表个数
	 * @param map
	 * @return
	 */
	int getCollectListCount(Map<String, Object> map);
	
	/**
	 * 查询计划
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> queryCalendar(String start,String end);
	/**
	 * 保存排班计划
	 * @param map
	 */
	public String savePlan(Map<String, Object> map);
	
	
	/**
	 * 查询前三年中某月某时订单量
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryhistoryData(Map<String, Object> map);
	
	/**
	 * 插入详细排班
	 * @param plan
	 */
	public void saveDetailPlan(TimePlanEntity plan);
	
	/**
	 * 保存配置
	 * @param map
	 */
	public String saveDeploy(Map<String, Object> map);
	
	/**
	 * 查询配置
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryDeploy();
	
	/**
	 * 删除某年某月数据
	 * @param month
	 */
	public void deleteDetail(String month);
	
	/**
	 * 查询售后组负责人
	 * @return
	 */
	public List<Map<String,Object>> queryManagerList(Map<String,Object> map);
	
	/**
	 * 查询问题描述
	 * @return
	 */
	public List<ComplaintCollectListVo>  queryDescript(Map<String,Object> map);
	
	/**
	 * 获取责任人
	 * @param map
	 * @return
	 */
	public List<ComplaintCollectListVo> queryTracker(Map<String,Object> map);
}
