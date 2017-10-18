package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;

@Repository("satisfaction_dao_sqlmap-preSaleSatisfaction")
public interface IPreSaleSatisfactionMap extends IMapBase  {
	
	public int getCountByOrderId(int orderId);
	
	public void updateOrderStatusByOrderId(Map map);
	
	public void updateTelCountByOrderId(Map map);
	
	public int getOrderIdById(int id);

	public List<PreSaleSatisfactionEntity> getInfoByDept(Map<String, Object> param);
}
