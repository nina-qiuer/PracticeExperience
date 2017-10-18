package com.tuniu.gt.datafix.service;

import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
public interface IDataFixService extends IServiceBase {

	public Integer getSignSatisfactionDataFixMaxId();
	
	public SignSatisfactionEntity getNextSignSatisfactionDataFixId(Map map);
    
	public void updateCityInfo(Map map);
	
	public void updateMaxId(Integer id);
}
