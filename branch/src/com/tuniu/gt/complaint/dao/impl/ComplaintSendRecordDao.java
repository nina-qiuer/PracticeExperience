package com.tuniu.gt.complaint.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintSendRecordMap;
import com.tuniu.gt.complaint.entity.ComplaintSendRecordEntity;

@Repository("complaint_dao_impl-complaintSendRecord")
public class ComplaintSendRecordDao extends DaoBase<ComplaintSendRecordEntity, IComplaintSendRecordMap>  implements IComplaintSendRecordMap {
	public ComplaintSendRecordDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_schedule_send_task_record";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaintSendRecord")
	public void setMapper(IComplaintSendRecordMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public Date getLastSendTime() {
		return mapper.getLastSendTime();
	}

	@Override
	public int getCount() {
		return mapper.getCount();
	}

	@Override
	public void updateTime(Date date) {
		mapper.updateTime(date);
	}

}
