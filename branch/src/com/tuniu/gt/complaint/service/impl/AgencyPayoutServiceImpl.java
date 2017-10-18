package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.AgencyPayoutDao;
import com.tuniu.gt.complaint.entity.AgencyPayoutEntity;
import com.tuniu.gt.complaint.service.IAgencyPayoutService;
@Service("complaint_service_complaint_impl-agency_payout")
public class AgencyPayoutServiceImpl extends ServiceBaseImpl<AgencyPayoutDao> implements IAgencyPayoutService {
	@Autowired
	@Qualifier("complaint_dao_impl-agency_payout")
	public void setDao(AgencyPayoutDao dao) {
		this.dao = dao;
	}

	@Override
	public List<AgencyPayoutEntity> getEntityByAgencyIdAndComplaintId(
			Integer agencyId, Integer complaintId) {
	    
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("agencyId", agencyId);
		paramMap.put("complaintId", complaintId);
		
		return (List<AgencyPayoutEntity>) dao.fetchList(paramMap);
	}

	@Override
	public List<AgencyPayoutEntity> getEntityByComplaintId(Integer complaintId) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("complaintId", complaintId);
		
		return (List<AgencyPayoutEntity>) dao.fetchList(paramMap);
	}

	
}
