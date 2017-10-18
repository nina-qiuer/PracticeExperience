package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IReceiverEmailMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-receiver_email")
public class ReceiverEmailDao extends DaoBase<ReceiverEmailEntity, IReceiverEmailMap>  implements IReceiverEmailMap {
	public ReceiverEmailDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "receiver_email";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-receiver_email")
	public void setMapper(IReceiverEmailMap mapper) {
		this.mapper = mapper;
	}
}
