package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.ComplaintOrderStatusEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintOrderStatusMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-complaint_order_status")
public class ComplaintOrderStatusDao extends DaoBase<ComplaintOrderStatusEntity, IComplaintOrderStatusMap> {
	public ComplaintOrderStatusDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_order_status";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_order_status")
	public void setMapper(IComplaintOrderStatusMap mapper) {
		this.mapper = mapper;
	}
}
