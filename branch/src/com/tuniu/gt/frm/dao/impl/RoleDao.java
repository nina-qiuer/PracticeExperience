package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.RoleEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IRoleMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-role")
public class RoleDao extends DaoBase<RoleEntity, IRoleMap>  implements IRoleMap {
	public RoleDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "role";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-role")
	public void setMapper(IRoleMap mapper) {
		this.mapper = mapper;
	}
}
