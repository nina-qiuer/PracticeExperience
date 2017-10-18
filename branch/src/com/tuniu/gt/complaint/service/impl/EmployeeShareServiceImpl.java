package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.EmployeeShareDao;
import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.IEmployeeShareService;
@Service("complaint_service_complaint_impl-employee_share")
public class EmployeeShareServiceImpl extends ServiceBaseImpl<EmployeeShareDao> implements IEmployeeShareService {
	@Autowired
	@Qualifier("complaint_dao_impl-employee_share")
	public void setDao(EmployeeShareDao dao) {
		this.dao = dao;
	}

	@Override
	public List<EmployeeShareEntity> getEmployeeListBySolutionId(String solutionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(solutionId!=null){
			paramMap.put("solutionId", solutionId);
			return  (List<EmployeeShareEntity>) dao.fetchList(paramMap);
		}else{
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeShareEntity> getEmployeeListByComplaintId(String complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(complaintId!=null){
			paramMap.put("complaintId", complaintId);
			return (List<EmployeeShareEntity>) dao.fetchList(paramMap);
		}else{
			return null;
		}
	}
}
