package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.GiftTypeEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IGiftTypeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-gift_type")
public class GiftTypeDao extends DaoBase<GiftTypeEntity, IGiftTypeMap>  implements IGiftTypeMap {
	public GiftTypeDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "gift_type";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-gift_type")
	public void setMapper(IGiftTypeMap mapper) {
		this.mapper = mapper;
	}
}
