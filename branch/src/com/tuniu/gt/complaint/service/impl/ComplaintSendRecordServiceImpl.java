package com.tuniu.gt.complaint.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintSendRecordDao;
import com.tuniu.gt.complaint.service.IComplaintSendRecordService;

/**
* @ClassName: ComplaintServiceImpl
* @Description:complaint接口实现
* @author Ocean Zhong
* @date 2012-1-20 下午5:04:51
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-complaintSendRecord")
public class ComplaintSendRecordServiceImpl extends ServiceBaseImpl<ComplaintSendRecordDao> implements IComplaintSendRecordService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaintSendRecord")
	public void setDao(ComplaintSendRecordDao dao) {
		this.dao = dao;
	}

	@Override
	public Date getLastSendTime() {
		return this.dao.getLastSendTime();
	}

	@Override
	public int getCount() {
		return this.dao.getCount();
	}

	@Override
	public void updateTime(Date date) {
		this.dao.updateTime(date);
	}
	
}
