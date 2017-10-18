package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.RoleUserAttrEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IRoleUserAttrMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-role_user_attr")
public class RoleUserAttrDao extends DaoBase<RoleUserAttrEntity, IRoleUserAttrMap> {
	public RoleUserAttrDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "role_user_attr";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-role_user_attr")
	public void setMapper(IRoleUserAttrMap mapper) {
		this.mapper = mapper;
	}
}
