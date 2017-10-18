package com.tuniu.gt.frm.action.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.entity.RoleEntity;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.privilege.IMenuPrivilegeService;
import com.tuniu.gt.frm.service.privilege.IMenuService;
import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;
import com.tuniu.gt.frm.service.privilege.IRoleService;
import com.tuniu.gt.frm.service.privilege.IRoleUserAttrService;
import com.tuniu.gt.frm.service.system.IUserService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;




@Service("frm_action_privilege-role")
@Scope("prototype")
public class RoleAction extends FrmBaseAction<IRoleService,RoleEntity> { 
	
	public RoleAction() {
		setManageUrl(manageUrl + "frm/action/privilege/role");
	}
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role")
	public void setService(IRoleService service) {
		this.service = service;
	};
	
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
	private IMenuService menuService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService; 
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu_privilege")
	private IMenuPrivilegeService menuPrivilegeService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_privilege")
	private IRolePrivilegeService rolePrivilegeService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_user_attr")
	private IRoleUserAttrService roleUserAttrService;
	
	public String execute() {
		this.setPageTitle("角色管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		String res = super.execute(paramMap);
		request.setAttribute("search",search);
		return res;
	}
	
	


	

	public void _preAdd() {
//		request.setAttribute("userList",userService.loadAllUser());  
	}
	
	
	public void _aftEdit() {
		
		Integer superManageId = entity.getSuperManageId();
		UserEntity superUser = new UserEntity();
		if(superManageId > 0) {
			superUser = (UserEntity) userService.get(superManageId);
		}
		
		request.setAttribute("superUser", superUser); 
		
		String userIds = entity.getManageIds();
		List<Map> userList = new ArrayList<Map>();
		if(!userIds.equals("")) {
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			sqlMap.put("ids", userIds);
			sqlMap.put("fields", "id,real_name"); 
			userList = (List<Map>) userService.fetchListMap(sqlMap);
		}
		request.setAttribute("userList",userList); 
	}

	
	
	/**
	 * 系统管理员初始给角色分配权限
	 * @return
	 */
	public String system(){
		this.setPageTitle("角色-权限设置");
		request.setAttribute("menuSelected", "role-system");
		List<RoleEntity> dataList = new ArrayList<RoleEntity>();
		dataList = (List<RoleEntity>)service.fetchList();
		Integer roleId = Common.intval(request.getParameter("role_id"));	
		
		if(roleId > 0) {
			Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
			rolePrivilegeMap = rolePrivilegeService.getAllocatedPrivilege(roleId, 4);  
			request.setAttribute("rolePrivilegeMap", rolePrivilegeMap);
			request.setAttribute("menuList", menuService.fetchlistAndPrivilege());
		}
		request.setAttribute("role_id", roleId); 
		request.setAttribute("dataList", dataList);
		jspTpl = "system"; 
		return jspTpl; 
	}
	
	
	/**
	 * 角角超级管理员给角色分配权限
	 * @return
	 */
	public String superAdmin() {
		request.setAttribute("menuSelected", "role-superAdmin");
		
	
		List<RoleEntity> dataList = service.getSuperCanManageRoleList(user.getId());
		request.setAttribute("dataList", dataList);
		
		Integer roleId = Common.intval(request.getParameter("role_id"));	
		
		
		
		if(roleId > 0) {
			Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
			//获取该角色已分配的权限
			rolePrivilegeMap = rolePrivilegeService.getAllocatedPrivilege(roleId, 2);
			request.setAttribute("rolePrivilegeMap", rolePrivilegeMap);
		
			//获取该角色可管理的菜单,权限id
			String menuIds = rolePrivilegeService.getManageMenuIds(roleId, 4);
			String privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleId, 4);
			
			//获取该角色可管理的菜单列表
			List<MenuEntity> menuList = menuService.fetchlistAndPrivilege(menuIds,privilegeIds); 
			request.setAttribute("menuList", menuList);
		}
		request.setAttribute("role_id", roleId); 
		jspTpl = "super_admin";  
		return jspTpl;
	}
	
	/**
	 * 普通管理员给人员分配权限
	 * @return
	 */
	public String admin() {
		request.setAttribute("menuSelected", "role-admin");
		List<RoleEntity> dataList = service.getCanManageRoleList(user.getId());
		request.setAttribute("dataList", dataList);
		
		Integer roleId = Common.intval(request.getParameter("role_id"));	
	
		if(roleId > 0) {
			//获取该角色已分配的权限
			Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
			rolePrivilegeMap = rolePrivilegeService.getAllocatedPrivilege(roleId, 1);
			request.setAttribute("rolePrivilegeMap", rolePrivilegeMap);
			
			//获取该角色可分配的权限树
			String menuIds = rolePrivilegeService.getManageMenuIds(roleId, 2); 
			String privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleId, 2);
			List<MenuEntity> menuList = menuService.fetchlistAndPrivilege(menuIds,privilegeIds);   
			request.setAttribute("menuList", menuList);
		}
		request.setAttribute("role_id", roleId); 
		jspTpl = "admin";  
		return jspTpl;
	}
	
	
	/**
	 * 给普通用户分配角色 
	 * @return
	 */
	public String user() {
		
		request.setAttribute("menuSelected", "role-user");
		
		//获取其下可管理的用户
		List<UserEntity> userList = userService.getUsersByDepartmentId(user.getDepId());
		request.setAttribute("userList", userList);
		
		
		Integer userId = Common.intval(request.getParameter("user_id"));
		if(userId > 0) {
			//获取该用户可管理的角色
			List<RoleEntity> roleList = service.getCanManageRoleList(user.getId());
			request.setAttribute("roleList", roleList);	
			
			//获取分配给该用户的角色
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("priType", "user");
			paramMap.put("priId", userId); 
			String userRoleIds =  roleUserAttrService.fetchCol(paramMap, "roleId");
			request.setAttribute("userRoleIds", userRoleIds);		
		}
		request.setAttribute("user_id", userId);
		jspTpl = "user";  
		return jspTpl;
	}
	
}