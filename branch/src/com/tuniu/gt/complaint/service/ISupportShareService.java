package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.SupportShareEntity;
public interface ISupportShareService extends IServiceBase {
	
	public List<SupportShareEntity> getSupportListBySolutionId(String solutionId);
	
	/**
	 * 通过投诉id获取分担供应商
	 * @param complaintId 投诉id
	 * @return 分担供应商列表
	 */
	List<SupportShareEntity> getSupportListByComplaintId(String complaintId); 

	/**
	 * 根据供应商名获取share_id
	 * @param String agencyName
	 * @return
	 */
	public String getShareIdsByAgencyName(String agencyName);
	
	/**
	 * 供应商赔付确认到期自动确认
	 */
	void confirmPayoutAuto();
	
	void updateByCodeAndCompId(Map<String, Object> params);
	
}
