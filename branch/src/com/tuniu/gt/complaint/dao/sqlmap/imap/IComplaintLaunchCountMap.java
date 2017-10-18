package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintLaunchCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintLaunchEntity;

@Repository("complaint_dao_sqlmap-complaint_launch_count")
public interface IComplaintLaunchCountMap extends IMapBase {

	/**
	 * 根据条件获取发起投诉列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<ComplaintLaunchCountEntity> getComplaintLaunchCountList(
			Map<String, Object> paramMap);

	/**
	 * 根据条件获取发起投诉总数
	 * 
	 * @param paramMap
	 * @return
	 */
	Integer getComplaintLaunchCountNum(Map<String, Object> paramMap);

	/**
	 * 同步每天的发起投诉数据
	 * 
	 * @param paramMap
	 */
	void insertComplaintLaunchByDay(Map<String, Object> paramMap);

	/**
	 * 跟新切片中的部门方法1
	 */
	void updateDepartmentToReportOne();

	/**
	 * 跟新切片中的部门方法2
	 */
	void updateDepartmentToReportTwo();

	/**
	 * 跟新切片中的部门方法3
	 */
	void updateDepartmentToReportThree();

	/**
	 * 获取发起投诉列表
	 * @param paraMap
	 * @return
	 */
	List<ComplaintLaunchEntity> getComplaintLaunchList(
			Map<String, Object> paraMap);
	
	/**
	 * 获取发起投诉列表
	 * @param paraMap
	 * @return
	 */
	Integer getComplaintLaunchListCount(Map<String, Object> paraMap);
}
