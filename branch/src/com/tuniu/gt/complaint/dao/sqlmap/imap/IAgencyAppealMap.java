package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.AgencyAppealEntity;

@Repository("complaint_dao_sqlmap-agency_appeal")
public interface IAgencyAppealMap extends IMapBase {
	
	AgencyAppealEntity getAppealInfo(Map<String, Object> params);

}
