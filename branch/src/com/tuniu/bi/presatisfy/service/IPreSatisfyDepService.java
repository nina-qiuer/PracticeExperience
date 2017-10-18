package com.tuniu.bi.presatisfy.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.bi.presatisfy.entity.PresatisfyEntity;

public interface IPreSatisfyDepService extends IServiceBase{
	
	/**
	 * 
	 * @return 
	 */
	void addPresatisfyDep(PresatisfyEntity presatisfy);
	
	public List<PresatisfyEntity> getPresatisfyDep(Map<String, Object> paramMap);
	
	Integer getPresatisfyDepCount();
	
	public List<Integer> getPresatisfyContactId();
	
	public int queryPresatisfyListCount(Map<String, Object> paramMap);
	
	void updatePresatisfyDep(PresatisfyEntity presatisfy);
}
