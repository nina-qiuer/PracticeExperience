package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ICcEmailMap;
import com.tuniu.gt.complaint.entity.CcEmailEntity;

@Repository("complaint_dao_impl-cc_email")
public class CcEmailDao extends DaoBase<CcEmailEntity, ICcEmailMap>  implements ICcEmailMap {
	public CcEmailDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "cc_email";
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-cc_email")
	public void setMapper(ICcEmailMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<String> getCcEmails(Map<String, Object> params) {
		return mapper.getCcEmails(params);
	}
	
}
