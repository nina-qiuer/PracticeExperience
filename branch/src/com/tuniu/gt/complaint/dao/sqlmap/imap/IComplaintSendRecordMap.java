package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Date;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-complaintSendRecord")
public interface IComplaintSendRecordMap extends IMapBase { 
	
	public Date getLastSendTime();
	
	public int getCount();
	
	public void updateTime(Date date);
}
