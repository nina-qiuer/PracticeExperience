package com.tuniu.gt.complaint.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAgencyAppealMap;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;

@Repository("complaint_dao_impl-agency_appeal")
public class AgencyAppealDao extends DaoBase<AgencyAppealEntity, IAgencyAppealMap>  implements IAgencyAppealMap {
	public AgencyAppealDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "agency_appeal";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-agency_appeal")
	public void setMapper(IAgencyAppealMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public AgencyAppealEntity getAppealInfo(Map<String, Object> params) {
		return mapper.getAppealInfo(params);
	}
	
}
