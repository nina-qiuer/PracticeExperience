package com.tuniu.gt.uc.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.dao.sqlmap.imap.IDepuserMapMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("uc_dao_impl-depuser_map")
public class DepuserMapDao extends DaoBase<DepuserMapEntity, IDepuserMapMap> {
	public DepuserMapDao() {  
		tableName = Constant.appTblPreMap.get("uc") + "depuser_map";		
	}
	
	@Autowired
	@Qualifier("uc_dao_sqlmap-depuser_map")
	public void setMapper(IDepuserMapMap mapper) {
		this.mapper = mapper;
	}
	
	
	public void deleteByUserId(int userId) {
		Map<String, Object> sqlParaMap = new HashMap<String, Object>();
		sqlParaMap.put("userId", userId);
		sqlParaMap.put("table", tableName);
		mapper.deleteByUserId(sqlParaMap);
	}
}
