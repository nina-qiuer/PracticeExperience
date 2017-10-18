package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.CheckEmailDao;
import com.tuniu.gt.complaint.entity.CheckEmailEntity;
import com.tuniu.gt.complaint.service.ICheckEmailService;

@Service("complaint_service_complaint_check_email_impl-check_email")
public class CheckEmailServiceImpl extends ServiceBaseImpl<CheckEmailDao> implements ICheckEmailService {
	@Autowired
	@Qualifier("complaint_dao_impl-check_email")
	public void setDao(CheckEmailDao dao) {
		this.dao = dao;
	};
	
	/**
	 * 根据complaintId获取跟进记录
	 * @param complaintId
	 * @return
	 */
	public List<CheckEmailEntity> getCheckMailListByComplaintId(String complaintId){
		List<CheckEmailEntity> dateList=new ArrayList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);	
		dateList=(List<CheckEmailEntity>) dao.fetchList(paramMap);
		return dateList;
	}
}
