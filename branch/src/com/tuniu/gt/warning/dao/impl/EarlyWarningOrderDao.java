package com.tuniu.gt.warning.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.dao.sqlmap.imap.EarlyWarningOrderMap;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;

@Repository("warning_dao_impl-early_warning_order")
public class EarlyWarningOrderDao extends DaoBase<EarlyWarningOrderEntity, EarlyWarningOrderMap> implements EarlyWarningOrderMap {
	
	public EarlyWarningOrderDao() {
		tableName = "ew_order";		
	}

	@Autowired
	@Qualifier("warning_dao_sqlmap-early_warning_order")
	public void setMapper(EarlyWarningOrderMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getEwoNum(EarlyWarningOrderPage ewoPage) {
		String agencyName = ewoPage.getAgencyName();
		if (null != agencyName && !"".equals(agencyName)) {
			ewoPage.setAgencyName2("'%" + agencyName + "%'");
		}
		return mapper.getEwoNum(ewoPage);
	}

	@Override
	public List<EarlyWarningOrderEntity> getEwoList(EarlyWarningOrderPage ewoPage) {
		String agencyName = ewoPage.getAgencyName();
		if (null != agencyName && !"".equals(agencyName)) {
			ewoPage.setAgencyName2("'%" + agencyName + "%'");
		}
		return mapper.getEwoList(ewoPage);
	}

	@Override
	public void toggleEwo(Map<String, Object> params) {
		mapper.toggleEwo(params);
	}

	@Override
	public void insertFlight(EwOrderFlightEntity flight) {
		mapper.insertFlight(flight);
	}

	@Override
	public void insertAgency(EwOrderAgencyEntity agency) {
		mapper.insertAgency(agency);
	}

	@Override
	public List<EwOrderFlightEntity> getFlightList(Integer ewOrderId) {
		return mapper.getFlightList(ewOrderId);
	}

	@Override
	public List<EwOrderAgencyEntity> getAgencyList(Integer ewOrderId) {
		return mapper.getAgencyList(ewOrderId);
	}

}
