package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.AgencyAppealDao;
import com.tuniu.gt.complaint.dao.impl.ClaimsAuditDao;
import com.tuniu.gt.complaint.dao.impl.SupportShareDao;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.ClaimsAuditService;
@Service("complaint_service_complaint_impl-claims_audit")
public class ClaimsAuditServiceImpl extends ServiceBaseImpl<ClaimsAuditDao> implements ClaimsAuditService {
	@Autowired
	@Qualifier("complaint_dao_impl-claims_audit")
	public void setDao(ClaimsAuditDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-support_share")
	private SupportShareDao supportShareDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-agency_appeal")
	private AgencyAppealDao appealDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<ClaimsAuditEntity> queryClaimsAuditList(Map<String, Object> paramMap, String tab_flag) {
		List<ClaimsAuditEntity> list = dao.queryClaimsAuditList(paramMap);
		if ("menu_1".equals(tab_flag)) {
			for (ClaimsAuditEntity cae : list) {
				ShareSolutionEntity ssEnt = cae.getShareSolutionEntity();
				if (null != ssEnt) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("shareId", ssEnt.getId());
					List<SupportShareEntity> supportList = (List<SupportShareEntity>) supportShareDao.fetchList(params);
					for (SupportShareEntity support : supportList) {
						if (3 == support.getConfirmState()) {
							Map<String, Object> requestMap = new HashMap<String, Object>();
							requestMap.put("agencyId", support.getCode());
							requestMap.put("complaintId", support.getComplaintId());
							AgencyAppealEntity appealInfo = appealDao.getAppealInfo(requestMap);
							if (null != appealInfo) { // 已申诉，且申诉已受理
								if (appealInfo.getResultFlag() > 0) {
									cae.setAdjustFlag(1);
									break;
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public Integer queryClaimsAuditListCount(Map<String, Object> paramMap) {
		return dao.queryClaimsAuditListCount(paramMap);
	}

	
	
}
