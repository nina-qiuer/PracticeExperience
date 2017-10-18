package com.tuniu.gt.complaint.service;

import com.tuniu.gt.complaint.entity.ComplaintReleaserEntity;

import tuniu.frm.core.IServiceBase;

public interface IComplaintReleaserService extends IServiceBase {
	
	ComplaintReleaserEntity getByUserId(Integer userId);
	
	String getDistinctCitys();
	
}
