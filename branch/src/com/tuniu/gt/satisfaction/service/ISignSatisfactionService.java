package com.tuniu.gt.satisfaction.service;

import java.util.List;

import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

import tuniu.frm.core.IServiceBase;

public interface ISignSatisfactionService extends IServiceBase {
	
	public SignSatisfactionEntity getLastestEntityByTelNo(String telNo) ;
	
	public int getCountByOrderId(int orderId);
	
	public List<String> getDistinctBookCityList();
	
	public List<String> getDistinctStartCityList();
	
	public List<String> getDistinctDesCityList();
}
