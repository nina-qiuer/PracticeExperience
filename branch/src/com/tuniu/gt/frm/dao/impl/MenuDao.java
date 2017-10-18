package com.tuniu.gt.frm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IMenuMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-menu")
public class MenuDao extends DaoBase<MenuEntity, IMenuMap>  implements IMenuMap {
	public MenuDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "menu";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-menu")
	public void setMapper(IMenuMap mapper) {
		this.mapper = mapper;
	}
	
	


	@Override
	public List<MenuEntity> fetchlistAndPrivilege(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
		return mapper.fetchlistAndPrivilege(paramMap); 
	}

	@Override
	public void updateChildsTreeFatherId(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Map<String, Object>> fetchListOrg() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("table", tableName);
		return mapper.fetchListOrg(paramMap);  
	}

	@Override
	public List<Map<String, Object>> fetchListOrg(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return fetchListOrg();
	}
}
