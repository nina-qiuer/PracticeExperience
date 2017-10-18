package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.CheckEmailEntity;

import tuniu.frm.core.IServiceBase;
public interface ICheckEmailService extends IServiceBase {

	/**
	*
	* 根据complaintId获取跟进记录
	* @param complaintId
	* 
	* @return List<CheckEmailEntity>
	* @throws
	*/
	public List<CheckEmailEntity> getCheckMailListByComplaintId(String complaintId);

}
