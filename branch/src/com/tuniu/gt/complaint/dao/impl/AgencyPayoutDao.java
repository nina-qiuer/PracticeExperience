package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAgencyPayoutMap;
import com.tuniu.gt.complaint.entity.AgencyPayoutEntity;

@Repository("complaint_dao_impl-agency_payout")
public class AgencyPayoutDao extends DaoBase<AgencyPayoutEntity, IAgencyPayoutMap>  implements IAgencyPayoutMap {
	public AgencyPayoutDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "agency_payout";
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-agency_payout")
	public void setMapper(IAgencyPayoutMap mapper) {
		this.mapper = mapper;
	}
	
	public void insertList(Integer supportId, List<AgencyPayoutEntity> list) {
		for (AgencyPayoutEntity entity : list) {
			if (null != entity) {
				entity.setSupportId(supportId);
				super.insert(entity);
			}
		}
	}

	@Override
	public void deleteBySupportId(Integer supportId) {
		mapper.deleteBySupportId(supportId);
	}

	@Override
	public void updateSupportId(Map<String, Object> params) {
		mapper.updateSupportId(params);
	}
	
}
