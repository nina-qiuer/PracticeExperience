package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintCompleteCountEntity;

public interface IComplaintReportService extends IServiceBase {

	Map<String, Map<String, Integer>> getComplaintCompleteCount(
			Map<String, Object> paramMap);

	List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap);

	void updateDepartmentByUserId();
	
	void insertComplaintIdByOrderId();
	
	List<ComplaintCompleteCountEntity> getCompleteCountByWorknum(Map<String, Object> paramMap);
	
	void insertServicesByOrderId();

	void insertServicesOld();
	
	void insertLaunchCountByComplaintId();

	void updateLaunchCountDepartment();
}
