package com.tuniu.gt.satisfaction.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;

public interface IPreSaleSatisfactionService extends IServiceBase {
	
	public int getCountByOrderId(int orderId);
	
	public void updateOrderStatusByOrderId(Map map);
	
	public void updateTelCountByOrderId(Map map);
	
	public int getOrderIdById(int id);
	
	public List<PreSaleSatisfactionEntity> getInfoByDept(Map<String, Object> param);
	
}
