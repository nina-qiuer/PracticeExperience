package com.tuniu.gt.frm.service.privilege.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Common;

import com.tuniu.gt.frm.dao.impl.RolePrivilegeDao;
import com.tuniu.gt.frm.entity.RolePrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;
@Service("frm_service_privilege_impl-role_privilege")
public class RolePrivilegeServiceImpl extends ServiceBaseImpl<RolePrivilegeDao> implements IRolePrivilegeService {
	@Autowired
	@Qualifier("frm_dao_impl-role_privilege")
	public void setDao(RolePrivilegeDao dao) {
		this.dao = dao;
	};
	
	@Override
	public void deleteByRoleId(Integer roleId) {
		dao.deleteByRoleId(roleId);
		
	}

	/**
	 * 系统分配角色权限(新增)
	 */
	@Override
	public void allocate(Integer roleId, Map<String, String[]> requestParamMap,Integer manageLevel) {
		
		RolePrivilegeEntity entity = new RolePrivilegeEntity();
		String menuArr[] = requestParamMap.get("menu_id");
		entity.setRoleId(roleId);
		entity.setManageLevel(manageLevel);
		String privilegArr[] = null;
		
		
		//清除原来的权限
		dao.updateDelFlagByRoleId(roleId);
		
		//分配新权限
		if(menuArr != null) {
			for(int i=0;i<menuArr.length;i++) {
				entity.setMenuId(Integer.parseInt(menuArr[i]));
				entity.setPrivilegeId(0);
				dao.insert(entity);
				privilegArr = requestParamMap.get("privilege_id_"+menuArr[i]);
				if(privilegArr != null) {
					for(int j=0;j<privilegArr.length;j++) {
						entity.setId(null);
						entity.setPrivilegeId(Integer.parseInt(privilegArr[j]));
						dao.insert(entity); 
					}  	 
				}
			}
		}
		dao.deleteByRoleId(roleId);		
	}
	
	
	
	public void adminAllocate(Integer roleId, Map<String, String[]> requestParamMap,Integer manageLevel) {
			
			RolePrivilegeEntity entity = new RolePrivilegeEntity();
			String menuArr[] = requestParamMap.get("menu_id");
			entity.setRoleId(roleId);
			entity.setManageLevel(manageLevel);
			String privilegArr[] = null;
			//清除原来的权限
			cleanManageLevel(roleId, manageLevel);
			//分配新权限
			if(menuArr != null) {
				for(int i=0;i<menuArr.length;i++) {
					entity.setMenuId(Integer.parseInt(menuArr[i]));
					entity.setPrivilegeId(0);
					addManageLevel(entity);
					privilegArr = requestParamMap.get("privilege_id_"+menuArr[i]);
					if(privilegArr != null) {
						for(int j=0;j<privilegArr.length;j++) {
							entity.setPrivilegeId(Common.intval(privilegArr[i]));
							addManageLevel(entity);
						}  	 
					}
					
				}
			}
		}
	
	

	
	/**
	 * 根据角色id和用户级别id 获取已分配的权限
	 */
	public Map<String, Object> getAllocatedPrivilege(Integer roleId,Integer ManageLevel) {
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("manageLevel", ManageLevel);
		paramMap.put("groupBy", "menu_id");
		paramMap.put("fields","menu_id,cast(group_concat(privilege_id) as char) as privilegeIds");  
		rolePrivilegeMap = dao.fetchMapMap(paramMap,"menuId");
		return rolePrivilegeMap;
	}
	
	
	public Map<String, Object> getAllocatedPrivilege(String roleIds,Integer ManageLevel) { 
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleIds", roleIds);
		paramMap.put("manageLevel", ManageLevel);
		paramMap.put("groupBy", "menu_id");
		paramMap.put("fields","menu_id,cast(group_concat(privilege_id) as char) as privilegeIds");  
		rolePrivilegeMap = dao.fetchMapMap(paramMap,"menuId");
		return rolePrivilegeMap;
	}
	
	
	
	

	/**
	 * 获取用户可管理的菜单id
	 */
	@Override
	public String getManageMenuIds(Integer roleId, Integer ManageLevel) {
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fields", "menu_id");
		paramMap.put("roleId", roleId);
		paramMap.put("manageLevel", ManageLevel);
		paramMap.put("groupBy", "menu_id");
	
		String  menuIds =  fetchCol(paramMap, "menuId");
		if(menuIds.equals("")) {
			menuIds = "0";
		}
		return menuIds;
	}
	
	
	public String getManageMenuIds(String roleIds, Integer ManageLevel) {
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fields", "menu_id");
		paramMap.put("roleIds", roleIds);
		paramMap.put("manageLevel", ManageLevel);
		paramMap.put("groupBy", "menu_id");
		String  menuIds =  fetchCol(paramMap, "menuId");
		if(menuIds.equals("")) {
			menuIds = "0";
		}
		return menuIds;
	}
	

	/**
	 * 获取用户可管理的权限id
	 */
	@Override
	public String getManagePrivilegeIds(Integer roleId, Integer ManageLevel) {
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("manageLevel", ManageLevel); 
		paramMap.put("fields", "privilege_id");
		paramMap.put("privilegeId", 0);
		
		
		String  privilegeIds =  fetchCol(paramMap, "menuId");
		if(privilegeIds.equals("")) {
			privilegeIds = "0";
		}
		
		return privilegeIds;
	}
	
	
	
	public String getManagePrivilegeIds(String roleIds, Integer ManageLevel) {
		Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleIds", roleIds);
		paramMap.put("manageLevel", ManageLevel); 
		paramMap.put("fields", "privilege_id");
		paramMap.put("privilegeId", 0);
		String  privilegeIds =  fetchCol(paramMap, "menuId");
		if(privilegeIds.equals("")) {
			privilegeIds = "0";
		}
		return privilegeIds;
	}
	
	
	/**
	 * 清除权限
	 * @param roleId
	 * @param manageLevelId
	 */
	protected void cleanManageLevel(Integer roleId,Integer manageLevel) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("manageLevel", manageLevel);
		dao.cleanManageLevel(paramMap);
	}


	/**
	 * 给指定的实体新增权限
	 * @param e
	 */
	protected void addManageLevel(RolePrivilegeEntity e) {
		dao.addManageLevel(e);
	}

	@Override
	public void updateDelFlagByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		dao.updateDelFlagByRoleId(roleId);
		
	}

	
}
