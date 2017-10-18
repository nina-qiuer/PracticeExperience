package com.tuniu.gt.frm.service.privilege;

import java.util.List;

import com.tuniu.gt.frm.entity.RoleEntity;

import tuniu.frm.core.IServiceBase;
public interface IRoleService extends IServiceBase {
	//超级管理员获取可管理角色
	public List<RoleEntity> getSuperCanManageRoleList(int userId);
	public String getSuperCanManageRoles(int userId);
	//普通管理员获取可管理的角色
	public List<RoleEntity> getCanManageRoleList(int userId);
	public String getCanManageRoles(int userId);
}
