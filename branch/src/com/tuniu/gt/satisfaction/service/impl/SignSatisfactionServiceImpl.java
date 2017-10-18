package com.tuniu.gt.satisfaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.SignSatisfactionDao;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionService;

@Service("satisfaction_service_satisfaction_impl-signSatisfaction")
public class SignSatisfactionServiceImpl extends ServiceBaseImpl<SignSatisfactionDao> implements ISignSatisfactionService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-signSatisfaction")
	public void setDao(SignSatisfactionDao dao) {
		this.dao = dao;
	}

	@Override
	public SignSatisfactionEntity getLastestEntityByTelNo(String telNo) {
		return this.dao.getLastestEntityByTelNo(telNo);
	}

	@Override
	public int getCountByOrderId(int orderId) {
		return this.dao.getCountByOrderId(orderId);
	}

	@Override
	public List<String> getDistinctBookCityList() {
		return this.dao.getDistinctBookCityList();
	}

	@Override
	public List<String> getDistinctStartCityList() {
		return this.dao.getDistinctStartCityList();
	}

	@Override
	public List<String> getDistinctDesCityList() {
		return this.dao.getDistinctDesCityList();
	}

	

}
