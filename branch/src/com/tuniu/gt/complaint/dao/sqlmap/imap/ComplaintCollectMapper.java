package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.vo.ComplaintCollectListVo;

@Repository
public interface ComplaintCollectMapper {

	/**
	 * 查询投诉质检汇总表数据
	 * @param map
	 * @return
	 */
	List<ComplaintCollectListVo> getCollectList(Map<String, Object> paramMap);
	
	/**
	 * 查询投诉质检汇总表个数
	 * @param map
	 * @return
	 */
	int getCollectListCount(Map<String, Object> paramMap);
	
	/**
	 * 查询日期计划
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>>  queryCalendar(Map<String, Object> paramMap);
	
	
	/**
	 * 保存排班人员
	 * @param paramMap
	 */
	void savePlan(Map<String, Object> paramMap);
	
	
	/**
	 *  查询前三年中某月某日某时订单量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>>  queryhistoryData(Map<String, Object> paramMap);
	
	
	/**
	 * 插入排班详情
	 * @param map
	 */
	void saveDetailPlan(Map<String, Object> map);
	/**
	 * 保存配置
	 * @param map
	 */
	public void saveDeploy(Map<String, Object> map);
	
	/**
	 * 查询配置
	 * @return
	 */
	public Map<String, Object> queryDeploy();
	
	/**
	 * 删除某年某月数据
	 * @param month
	 */
	public void deleteDetail(String month);
	/**
	 * 查询出游中负责人
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryManagerList(Map<String, Object> paramMap);
	
	
	/**
	 * 查询问题描述
	 */
    List<ComplaintCollectListVo> queryDescript(Map<String, Object> paramMap);
    
    
    /**
	 * 查询责任人
	 */
    List<ComplaintCollectListVo> queryTracker(Map<String, Object> paramMap);
}
