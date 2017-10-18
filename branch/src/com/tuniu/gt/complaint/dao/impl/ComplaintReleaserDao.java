package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintReleaserMap;
import com.tuniu.gt.complaint.entity.ComplaintReleaserEntity;

@Repository("complaint_dao_impl-complaint_releaser")
public class ComplaintReleaserDao extends DaoBase<ComplaintReleaserEntity, IComplaintReleaserMap> implements IComplaintReleaserMap {
	public ComplaintReleaserDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_releaser";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_releaser")
	public void setMapper(IComplaintReleaserMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public ComplaintReleaserEntity getByUserId(Map<String, Object> params) {
		return mapper.getByUserId(params);
	}

	@Override
	public List<String> getDistinctCitys() {
		return mapper.getDistinctCitys();
	}
	
}
