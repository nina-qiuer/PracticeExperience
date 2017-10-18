package com.tuniu.gt.complaint.service;

import java.util.Map;

import com.tuniu.gt.complaint.entity.AgencyAppealEntity;

import tuniu.frm.core.IServiceBase;
public interface IAgencyAppealService extends IServiceBase {
	
	AgencyAppealEntity getAppealInfo(Map<String, Object> params);
	
	void processAppeal(AgencyAppealEntity entity, int userId);

}
