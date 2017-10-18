package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.CheckEmailEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.ICheckEmailMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-check_email")
public class CheckEmailDao extends DaoBase<CheckEmailEntity, ICheckEmailMap>  implements ICheckEmailMap {
	public CheckEmailDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "check_email";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-check_email")
	public void setMapper(ICheckEmailMap mapper) {
		this.mapper = mapper;
	}
}
