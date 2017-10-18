package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.ComplaintThirdPartDao;
import com.tuniu.gt.complaint.entity.ComplaintThirdPartEntity;
import com.tuniu.gt.complaint.service.IComplaintThirdPartService;

import tuniu.frm.core.ServiceBaseImpl;

@Service("complaint_service_complaint_impl-complaint_third_part")
public class ComplaintThirdPartServiceImpl extends ServiceBaseImpl<ComplaintThirdPartDao>
		implements IComplaintThirdPartService {
	private static Logger logger = Logger.getLogger(ComplaintThirdPartServiceImpl.class);

	@Autowired
	@Qualifier("complaint_dao_impl-complaint_third_part")
	public void setDao(ComplaintThirdPartDao dao) {
		this.dao = dao;
	}
	
	public Integer getComplaintThirdPartCount(Map<String,Object> paramMap){
		return dao.getComplaintThirdPartCount(paramMap);
	}
	
	public List<ComplaintThirdPartEntity> getComplaintThirdPartLists(Map<String, Object> paramMap){
		return dao.getComplaintThirdPartLists(paramMap);
	}
}
