package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.UserRoleEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IUserRoleMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-user_role")
public class UserRoleDao extends DaoBase<UserRoleEntity, IUserRoleMap>  implements IUserRoleMap {
	public UserRoleDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "user_role";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-user_role")
	public void setMapper(IUserRoleMap mapper) {
		this.mapper = mapper;
	}
}
