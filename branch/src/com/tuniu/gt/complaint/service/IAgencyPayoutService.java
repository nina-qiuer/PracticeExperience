package com.tuniu.gt.complaint.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.AgencyPayoutEntity;
public interface IAgencyPayoutService extends IServiceBase {
	
	/**
	 * 根据供应商Id和投诉单ID获取供应商赔款详情
	 * 
	 * */
	public List<AgencyPayoutEntity> getEntityByAgencyIdAndComplaintId(Integer agencyId,Integer complaintId);
	
	/**
	 * 根据投诉单ID获取供应商赔款详情
	 * 
	 * */
	public List<AgencyPayoutEntity> getEntityByComplaintId(Integer complaintId);
	
}
