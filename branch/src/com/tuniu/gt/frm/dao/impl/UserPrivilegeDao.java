package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.UserPrivilegeEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IUserPrivilegeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-user_privilege")
public class UserPrivilegeDao extends DaoBase<UserPrivilegeEntity, IUserPrivilegeMap> {
	public UserPrivilegeDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "user_privilege";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-user_privilege")
	public void setMapper(IUserPrivilegeMap mapper) {
		this.mapper = mapper;
	}
}
