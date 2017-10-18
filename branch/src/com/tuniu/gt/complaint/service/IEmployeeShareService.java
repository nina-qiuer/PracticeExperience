package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;

import tuniu.frm.core.IServiceBase;
public interface IEmployeeShareService extends IServiceBase {
	
	public List<EmployeeShareEntity> getEmployeeListBySolutionId(String solutionId);

	/**
	 * 通过投诉id获取承担员工列表
	 * @param complaintId 投诉id
	 * @return 承担员工列表
	 */
	List<EmployeeShareEntity> getEmployeeListByComplaintId(String complaintId);
}
