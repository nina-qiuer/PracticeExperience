package com.tuniu.gt.frm.service.privilege.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.MenuPrivilegeDao;
import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IMenuPrivilegeService;
@Service("frm_service_privilege_impl-menu_privilege")
public class MenuPrivilegeServiceImpl extends ServiceBaseImpl<MenuPrivilegeDao> implements IMenuPrivilegeService {
	@Autowired
	@Qualifier("frm_dao_impl-menu_privilege")
	public void setDao(MenuPrivilegeDao dao) {
		this.dao = dao;
	};
	
	public List<MenuPrivilegeEntity> getListByMenuId(Integer menuId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menu_id", menuId);
		return  (List<MenuPrivilegeEntity>)dao.fetchList(paramMap);
	}
		
	public Integer doAdd(Object e) {
		return dao.insert(e);
	}
	
}
