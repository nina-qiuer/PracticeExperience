package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.SupportShareDao;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.ISupportShareService;
@Service("complaint_service_complaint_impl-support_share")
public class SupportShareServiceImpl extends ServiceBaseImpl<SupportShareDao> implements ISupportShareService {
	@Autowired
	@Qualifier("complaint_dao_impl-support_share")
	public void setDao(SupportShareDao dao) {
		this.dao = dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SupportShareEntity> getSupportListBySolutionId(String solutionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(solutionId!=null){
			paramMap.put("solutionId", solutionId);
			return (List<SupportShareEntity>) dao.fetchList(paramMap);
		}else{
			return null;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SupportShareEntity> getSupportListByComplaintId(String complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(complaintId!=null){
			paramMap.put("complaintId", complaintId);
			return (List<SupportShareEntity>) dao.fetchList(paramMap);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据供应商名获取share_id
	 * @param String agencyName
	 * @return String
	 */
	public String getShareIdsByAgencyName(String agencyName){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer idStr = new StringBuffer();
		paramMap.put("agencyName", agencyName);
		@SuppressWarnings("unchecked")
		List<SupportShareEntity> supportShare = (List<SupportShareEntity>) dao.fetchList(paramMap);
		for(SupportShareEntity supportShares : supportShare){
			idStr.append(supportShares.getShareId());
			idStr.append(",");
		}
		String ids = idStr.toString();
		ids = ids.substring(0, ids.length()-1);
		return ids;
	}

	@Override
	public void confirmPayoutAuto() {
		dao.confirmPayoutAuto();
	}

	@Override
	public void updateByCodeAndCompId(Map<String, Object> params) {
		dao.updateByCodeAndCompId(params);
	}
	
}
