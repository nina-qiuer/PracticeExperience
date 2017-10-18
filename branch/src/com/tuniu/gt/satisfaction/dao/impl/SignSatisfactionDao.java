package com.tuniu.gt.satisfaction.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.ISignSatisfactionMap;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

@Repository("satisfaction_dao_impl-signSatisfaction")
public class SignSatisfactionDao  extends DaoBase<SignSatisfactionEntity, ISignSatisfactionMap>  implements ISignSatisfactionMap {

	public SignSatisfactionDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "sign_satisfaction";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-signSatisfaction")
	public void setMapper(ISignSatisfactionMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public SignSatisfactionEntity getLastestEntityByTelNo(String telNo) {
		return mapper.getLastestEntityByTelNo(telNo);
	}

	@Override
	public int getCountByOrderId(int orderId) {
		return mapper.getCountByOrderId(orderId);
	}

	@Override
	public List<String> getDistinctBookCityList() {
		return mapper.getDistinctBookCityList();
	}

	@Override
	public List<String> getDistinctStartCityList() {
		return mapper.getDistinctStartCityList();
	}

	@Override
	public List<String> getDistinctDesCityList() {
		return mapper.getDistinctDesCityList();
	}

	
	
}
