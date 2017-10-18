package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

import tuniu.frm.core.IMapBase;

@Repository("satisfaction_dao_sqlmap-signSatisfaction")
public interface ISignSatisfactionMap extends IMapBase  {

	public SignSatisfactionEntity getLastestEntityByTelNo(String telNo);
	
	public int getCountByOrderId(int orderId);
	
	public List<String> getDistinctBookCityList();
	
	public List<String> getDistinctStartCityList();
	
	public List<String> getDistinctDesCityList();
}
