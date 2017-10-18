package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IRefundApplyMap;
import com.tuniu.gt.complaint.entity.RefundApplyEntity;

@Repository("complaint_dao_impl-refund_apply")
public class RefundApplyDao extends DaoBase<RefundApplyEntity, IRefundApplyMap> implements IRefundApplyMap {
	
	public RefundApplyDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "refund_apply";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-refund_apply")
	public void setMapper(IRefundApplyMap mapper) {
		this.mapper = mapper;
	}
	
}
