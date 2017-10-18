package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IMenuPrivilegeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-menu_privilege")
public class MenuPrivilegeDao extends DaoBase<MenuPrivilegeEntity, IMenuPrivilegeMap>  implements IMenuPrivilegeMap {
	public MenuPrivilegeDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "menu_privilege";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-menu_privilege")
	public void setMapper(IMenuPrivilegeMap mapper) {
		this.mapper = mapper;
	}

	
}
