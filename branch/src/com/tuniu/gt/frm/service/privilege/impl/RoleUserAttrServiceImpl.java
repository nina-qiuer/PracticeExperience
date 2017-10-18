package com.tuniu.gt.frm.service.privilege.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.RoleUserAttrDao;
import com.tuniu.gt.frm.service.privilege.IRoleUserAttrService;
@Service("frm_service_privilege_impl-role_user_attr")
public class RoleUserAttrServiceImpl extends ServiceBaseImpl<RoleUserAttrDao> implements IRoleUserAttrService {
	@Autowired
	@Qualifier("frm_dao_impl-role_user_attr")
	public void setDao(RoleUserAttrDao dao) {
		this.dao = dao;
	}

	@Override
	public String getUserRoles(int uid) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);
		return fetchCol(paramMap, "roleId");
	
	};
}
