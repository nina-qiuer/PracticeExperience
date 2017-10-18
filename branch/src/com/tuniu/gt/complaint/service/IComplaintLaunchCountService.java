package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintLaunchCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintLaunchEntity;
import com.tuniu.gt.uc.datastructure.TreeNode;

public interface IComplaintLaunchCountService extends IServiceBase {

	/**
	 * 根据条件获取发起投诉列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<TreeNode<ComplaintLaunchCountEntity>> getComplaintLaunchCountList(
			Map<String, Object> paramMap);

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
	List<ComplaintLaunchEntity> getComplaintLaunchList(Map<String, Object> paraMap);
	
	/**
	 * 获取发起投诉列表
	 * @param paraMap
	 * @return
	 */
	Integer getComplaintLaunchListCount(Map<String, Object> paraMap);
}
