package com.tuniu.gt.satisfaction.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.IPreSaleSatisfactionMap;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;

@Repository("satisfaction_dao_impl-preSaleSatisfaction")
public class PreSaleSatisfactionDao  extends DaoBase<PreSaleSatisfactionEntity, IPreSaleSatisfactionMap>  implements IPreSaleSatisfactionMap {

	public PreSaleSatisfactionDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "pre_sale_satisfaction";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-preSaleSatisfaction")
	public void setMapper(IPreSaleSatisfactionMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getCountByOrderId(int orderId) {
		return mapper.getCountByOrderId(orderId);
	}

	@Override
	public void updateOrderStatusByOrderId(Map map) {
		mapper.updateOrderStatusByOrderId(map);
	}

	@Override
	public void updateTelCountByOrderId(Map map) {
		mapper.updateTelCountByOrderId(map);
	}
	
	@Override
	public int getOrderIdById(int id) {
		return mapper.getOrderIdById(id);
	}
	
	@Override
	public List<PreSaleSatisfactionEntity> getInfoByDept(Map<String, Object> param){
		return mapper.getInfoByDept(param);
	}
}
