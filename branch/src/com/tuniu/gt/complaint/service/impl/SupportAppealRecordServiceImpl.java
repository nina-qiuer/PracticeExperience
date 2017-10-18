package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.SupportAppealRecordDao;
import com.tuniu.gt.complaint.service.ISupportAppealRecordService;

import tuniu.frm.core.ServiceBaseImpl;
@Service("complaint_service_complaint_impl-support_appeal_record")
public class SupportAppealRecordServiceImpl extends ServiceBaseImpl<SupportAppealRecordDao> implements ISupportAppealRecordService {
	@Autowired
	@Qualifier("complaint_dao_impl-support_appeal_record")
	public void setDao(SupportAppealRecordDao dao) {
		this.dao = dao;
	}
}
