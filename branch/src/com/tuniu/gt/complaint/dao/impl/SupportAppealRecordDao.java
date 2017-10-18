package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ISupportAppealRecordMap;
import com.tuniu.gt.complaint.entity.SupportAppealRecordEntity;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

@Repository("complaint_dao_impl-support_appeal_record")
public class SupportAppealRecordDao extends DaoBase<SupportAppealRecordEntity, ISupportAppealRecordMap>  implements ISupportAppealRecordMap {
	
	public SupportAppealRecordDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "support_appeal_record";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-support_appeal_record")
	public void setMapper(ISupportAppealRecordMap mapper) {
		this.mapper = mapper;
	}
	
}
