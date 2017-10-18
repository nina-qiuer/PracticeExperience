package com.tuniu.gt.frm.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.tuniu.gt.frm.entity.RolePrivilegeEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IRolePrivilegeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-role_privilege")
public class RolePrivilegeDao extends DaoBase<RolePrivilegeEntity, IRolePrivilegeMap> {
	public RolePrivilegeDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "role_privilege";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-role_privilege")
	public void setMapper(IRolePrivilegeMap mapper) {
		this.mapper = mapper;
	}
	
	public void deleteByRoleId(Integer roleId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("table", tableName);
		mapper.delete(paramMap);
	}
	
	
	public void cleanManageLevel(Map<String, Object> paramMap) {
		paramMap.put("table", tableName);
		mapper.cleanManageLevel(paramMap);
		// TODO Auto-generated method stub
		
	}
	

	public void addManageLevel(Object e) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("table", tableName);
		paramMap.put("e", e); 
		mapper.addManageLevel(paramMap);
		// TODO Auto-generated method stub
	}

	
	
	
	public void updateDelFlagByRoleId(Integer roleId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("table", tableName);
		paramMap.put("roleId", roleId);
		mapper.updateDelFlagByRoleId(paramMap);
		
	}
}
