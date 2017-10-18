package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.RefundApplyDao;
import com.tuniu.gt.complaint.service.IRefundApplyService;

@Service("complaint_service_impl-refund_apply")
public class RefundApplyServiceImpl extends ServiceBaseImpl<RefundApplyDao> implements IRefundApplyService {
	@Autowired
	@Qualifier("complaint_dao_impl-refund_apply")
	public void setDao(RefundApplyDao dao) {
		this.dao = dao;
	};
	
}
