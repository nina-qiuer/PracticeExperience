package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintOrderStatusDao;
import com.tuniu.gt.complaint.service.IComplaintOrderStatusService;
@Service("complaint_service_complaint_impl-complaint_order_status")
public class ComplaintOrderStatusServiceImpl extends ServiceBaseImpl<ComplaintOrderStatusDao> implements IComplaintOrderStatusService {
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_order_status")
	public void setDao(ComplaintOrderStatusDao dao) {
		this.dao = dao;
	};
}
