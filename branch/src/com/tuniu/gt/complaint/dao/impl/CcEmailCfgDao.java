package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ICcEmailCfgMap;
import com.tuniu.gt.complaint.entity.CcEmailCfgEntity;

@Repository("complaint_dao_impl-cc_email_cfg")
public class CcEmailCfgDao extends DaoBase<CcEmailCfgEntity, ICcEmailCfgMap>  implements ICcEmailCfgMap {
	public CcEmailCfgDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "cc_email_cfg";
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-cc_email_cfg")
	public void setMapper(ICcEmailCfgMap mapper) {
		this.mapper = mapper;
	}
	
}
