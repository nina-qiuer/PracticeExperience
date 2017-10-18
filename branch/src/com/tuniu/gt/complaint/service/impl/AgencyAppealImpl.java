package com.tuniu.gt.complaint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.AgencyAppealDao;
import com.tuniu.gt.complaint.dao.impl.SupportShareDao;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.IAgencyAppealService;
@Service("complaint_service_complaint_impl-agency_appeal")
public class AgencyAppealImpl extends ServiceBaseImpl<AgencyAppealDao> implements IAgencyAppealService {
	@Autowired
	@Qualifier("complaint_dao_impl-agency_appeal")
	public void setDao(AgencyAppealDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-support_share")
	private SupportShareDao supportShareDao;

	@Override
	public AgencyAppealEntity getAppealInfo(Map<String, Object> params) {
		return dao.getAppealInfo(params);
	}
	
	@Override
	public void processAppeal(AgencyAppealEntity entity, int userId) {
		dao.update(entity);
		
		if (-1 == entity.getResultFlag()) { // 如果是被驳回，则直接默认
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("complaintId", entity.getComplaintId());
			params.put("code", entity.getAgencyId());
			SupportShareEntity ss = supportShareDao.fetchOne(params);
			ss.setConfirmState(2);
			ss.setConfirmer("robot");
			ss.setConfirmTime(new Date());
			supportShareDao.update(ss);
		}
	}
	
}
