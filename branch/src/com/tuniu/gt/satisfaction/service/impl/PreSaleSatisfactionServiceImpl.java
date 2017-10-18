package com.tuniu.gt.satisfaction.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.PreSaleSatisfactionDao;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionService;

@Service("satisfaction_service_satisfaction_impl-preSaleSatisfaction")
public class PreSaleSatisfactionServiceImpl extends ServiceBaseImpl<PreSaleSatisfactionDao> implements IPreSaleSatisfactionService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-preSaleSatisfaction")
	public void setDao(PreSaleSatisfactionDao dao) {
		this.dao = dao;
	}

	@Override
	public int getCountByOrderId(int orderId) {
		return this.dao.getCountByOrderId(orderId);
	}

	@Override
	public void updateOrderStatusByOrderId(Map map) {
		this.dao.updateOrderStatusByOrderId(map);
	}

	@Override
	public void updateTelCountByOrderId(Map map) {
		this.dao.updateTelCountByOrderId(map);
	}
	
	@Override
	public int getOrderIdById(int id) {
		return this.dao.getOrderIdById(id);
	}
	
	@Override
	public List<PreSaleSatisfactionEntity> getInfoByDept(Map<String, Object> param){
		return this.dao.getInfoByDept(param);
	}
}
