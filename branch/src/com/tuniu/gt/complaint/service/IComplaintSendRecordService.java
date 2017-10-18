package com.tuniu.gt.complaint.service;

import java.util.Date;

import tuniu.frm.core.IServiceBase;
public interface IComplaintSendRecordService extends IServiceBase {
	
	public Date getLastSendTime();
	
	public int getCount();
	
	public void updateTime(Date date);

}
