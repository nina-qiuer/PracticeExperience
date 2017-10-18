package com.tuniu.gt.complaint.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ISupportShareMap;
import com.tuniu.gt.complaint.entity.AgencyPayoutEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintService;

@Repository("complaint_dao_impl-support_share")
public class SupportShareDao extends DaoBase<SupportShareEntity, ISupportShareMap>  implements ISupportShareMap {
	
	public SupportShareDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "support_share";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-agency_payout")
	private AgencyPayoutDao payoutDao;
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-support_share")
	public void setMapper(ISupportShareMap mapper) {
		this.mapper = mapper;
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	@Override
	public void confirmPayoutAuto() {
		mapper.confirmPayoutAuto();
	}
	
	public void insertList(Integer complaintId, Integer shareId, List<SupportShareEntity> list) {
		for (SupportShareEntity entity : list) {
			if (null != entity) {
				entity.setComplaintId(complaintId);
				entity.setShareId(shareId);
				entity.setNumber(getTotalPayout(entity.getAgencyPayoutList()));
				if (8 != entity.getForeignCurrencyType()) {
					entity.setForeignCurrencyNumber(getFCTotalPayout(entity.getAgencyPayoutList()));
				}
				entity.setConfirmState(-1);
				super.insert(entity);
				payoutDao.insertList(entity.getId(), entity.getAgencyPayoutList());
			}
		}
	}
	
	private double getFCTotalPayout(List<AgencyPayoutEntity> list) {
		double total = 0;
		for (AgencyPayoutEntity ap : list) {
			if (null != ap && ap.getForeignCurrencyNumber() !=null) {
				total += ap.getForeignCurrencyNumber();
			}
		}
		return total;
	}
	
	private double getTotalPayout(List<AgencyPayoutEntity> list) {
		double total = 0;
		for (AgencyPayoutEntity ap : list) {
			if (null != ap) {
				total += ap.getPayoutNum();
			}
		}
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public List<SupportShareEntity> getSupportShareList(Integer shareId) {
		List<SupportShareEntity> list = new ArrayList<SupportShareEntity>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("shareId", shareId);
		list = (List<SupportShareEntity>) super.fetchList(paramMap);
		for (SupportShareEntity entity : list) {
			paramMap.clear();
			paramMap.put("supportId", entity.getId());
			entity.setAgencyPayoutList((List<AgencyPayoutEntity>) payoutDao.fetchList(paramMap));
			entity.setNbFlag(complaintService.getNbFlag(entity.getCode(), entity.getComplaintId()));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteByShareId(Integer shareId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shareId", shareId);
		List<SupportShareEntity> list = (List<SupportShareEntity>) super.fetchList(params);
		for (SupportShareEntity supportShare : list) {
			payoutDao.deleteBySupportId(supportShare.getId());
		}
		mapper.deleteByShareId(shareId);
	}
	
	public void logicDel(SupportShareEntity entity) {
		List<AgencyPayoutEntity> apList = entity.getAgencyPayoutList();
		if (null != apList) {
			for (AgencyPayoutEntity ap : apList) {
				ap.setDelFlag(0);
				payoutDao.update(ap);
			}
		}
		entity.setDelFlag(0);
		super.update(entity);
	}

	@Override
	public void updateByCodeAndCompId(Map<String, Object> params) {
		mapper.updateByCodeAndCompId(params);
	}
	
}
