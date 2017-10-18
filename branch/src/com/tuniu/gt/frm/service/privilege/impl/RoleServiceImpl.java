package com.tuniu.gt.frm.service.privilege.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.RoleDao;
import com.tuniu.gt.frm.entity.RoleEntity;
import com.tuniu.gt.frm.service.privilege.IRoleService;
@Service("frm_service_privilege_impl-role")
public class RoleServiceImpl extends ServiceBaseImpl<RoleDao> implements IRoleService {
	@Autowired
	@Qualifier("frm_dao_impl-role")
	public void setDao(RoleDao dao) {
		this.dao = dao;
	};
	
	
	public List<RoleEntity> getSuperCanManageRoleList(int userId) {
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("superManageId", userId);
		//获取可管理的角色
		return (List<RoleEntity>)dao.fetchList(sqlParam);
	}
	
	
	public String getSuperCanManageRoles(int userId) {
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("superManageId", userId);
		//获取可管理的角色
		return fetchCol(sqlParam,"id"); 
	}
	
	
	public List<RoleEntity> getCanManageRoleList(int userId) { 
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("manageId", userId);
		//获取可管理的角色
		return (List<RoleEntity>)dao.fetchList(sqlParam);
	}
	
	public String getCanManageRoles(int userId) { 
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("manageId", userId);
		//获取可管理的角色
		return fetchCol(sqlParam,"id");
	}
}
