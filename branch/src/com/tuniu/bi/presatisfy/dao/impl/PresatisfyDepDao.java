package com.tuniu.bi.presatisfy.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.bi.presatisfy.dao.sqlmap.imap.IPresatisfyDepMap;
import com.tuniu.bi.presatisfy.entity.PresatisfyEntity;

@Repository("bi_dao_impl-presatisfydep")
public class PresatisfyDepDao extends DaoBase<PresatisfyEntity, IPresatisfyDepMap> implements IPresatisfyDepMap {
	public PresatisfyDepDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "pre_sale_satisfaction";		
	}
	
	@Autowired
	@Qualifier("bi_dao_sqlmap-presatisfydep")
	public void setMapper(IPresatisfyDepMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void addPresatisfyDep(PresatisfyEntity presatisfy) {
		mapper.addPresatisfyDep(presatisfy);
	}

	@Override
	public List<PresatisfyEntity> getPresatisfyDep(Map<String, Object> paramMap){
		return mapper.getPresatisfyDep(paramMap);
	}
	
	@Override
	public Integer getPresatisfyDepCount() {
		return mapper.getPresatisfyDepCount();
	}
	
	@Override
	public List<Integer> getPresatisfyContactId(){
		return mapper.getPresatisfyContactId();
	}
	
	@Override
	public int queryPresatisfyListCount(Map<String, Object> paramMap){
		return mapper.queryPresatisfyListCount(paramMap);
	}
	
	@Override
	public void updatePresatisfyDep(PresatisfyEntity presatisfy){
		mapper.updatePresatisfyDep(presatisfy);
	}
}
