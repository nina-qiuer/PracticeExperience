package com.tuniu.gt.frm.service.privilege;

import java.util.Map;

import tuniu.frm.core.IServiceBase;
public interface IRolePrivilegeService extends IServiceBase {
	public void deleteByRoleId(Integer roleId);
	public void allocate(Integer roleId, Map<String, String[]> requestParamMap,Integer manageLevel);
	public void adminAllocate(Integer roleId, Map<String, String[]> requestParamMap,Integer manageLevel);
	public void updateDelFlagByRoleId(Integer roleId);
	
	/**
	 * 获取角色已分配的权限
	 * @param roleId
	 * @param ManageLevel
	 * @return
	 */
	public Map<String, Object> getAllocatedPrivilege(Integer roleId,Integer ManageLevel);
	public Map<String, Object> getAllocatedPrivilege(String roleIds,Integer ManageLevel);
	
	
	public String getManageMenuIds(Integer roleId,Integer ManageLevel);
	public String getManageMenuIds(String roleIds,Integer ManageLevel);
	
	public String getManagePrivilegeIds(Integer roleId,Integer ManageLevel);
	public String getManagePrivilegeIds(String roleIds,Integer ManageLevel);


}
