package com.tuniu.gt.complaint.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintEmailConfigMap;
import com.tuniu.gt.complaint.entity.ComplaintEmailConfigEntity;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

@Repository("complaint_dao_impl-complaint_email_config")
public class ComplaintEmailConfigDao extends DaoBase<ComplaintEmailConfigEntity, IComplaintEmailConfigMap>
		implements IComplaintEmailConfigMap {
	public ComplaintEmailConfigDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_email_config";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_email_config")
	public void setMapper(IComplaintEmailConfigMap mapper) {
		this.mapper = (IComplaintEmailConfigMap) mapper;
	}
	
	public Integer getEmailConfigCount(Map<String, Object> paramMap){
		return mapper.getEmailConfigCount(paramMap);
	}
}
