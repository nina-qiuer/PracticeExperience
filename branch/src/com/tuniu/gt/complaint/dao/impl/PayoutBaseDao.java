package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IPayoutBaseMap;
import com.tuniu.gt.complaint.entity.PayoutBaseEntity;

@Repository("complaint_dao_impl-payout_base")
public class PayoutBaseDao extends DaoBase<PayoutBaseEntity, IPayoutBaseMap>  implements IPayoutBaseMap {
	public PayoutBaseDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "payout_base";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-payout_base")
	public void setMapper(IPayoutBaseMap mapper) {
		this.mapper = mapper;
	}
}
