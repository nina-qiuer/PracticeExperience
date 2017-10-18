package com.tuniu.gt.frm.action.privilege;

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
import com.tuniu.gt.frm.entity.UserPrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IMenuPrivilegeService;
import com.tuniu.gt.frm.service.privilege.IMenuService;
import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;
import com.tuniu.gt.frm.service.privilege.IRoleService;
import com.tuniu.gt.frm.service.privilege.IUserPrivilegeService;
import com.tuniu.gt.frm.service.system.IUserService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_privilege-user_privilege")
@Scope("prototype")
public class UserPrivilegeAction extends FrmBaseAction<IUserPrivilegeService,UserPrivilegeEntity> {  
	
	public UserPrivilegeAction() {
		setManageUrl(manageUrl + "frm/action/privilege/user_privilege");
	}
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-user_privilege")
	public void setService(IUserPrivilegeService service) {
		this.service = service;
	};
	
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu_privilege")
	private IMenuPrivilegeService menuPrivilegeService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_privilege")
	private IRolePrivilegeService rolePrivilegeService;
	
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
	private IMenuService menuService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService; 

	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role")
	private IRoleService roleService;
	
	public String execute() {
		
		
		request.setAttribute("menuSelected", "user_privilege");
		
		//获取其下可管理的用户
		List<UserEntity> userList = userService.getUsersByDepartmentId(user.getDepId());
		request.setAttribute("userList", userList);
		
		
		Integer userId = Common.intval(request.getParameter("user_id"));
		request.setAttribute("user_id", userId); 
		if(userId > 0) {

			//获取该用户可管理的角色
			List<RoleEntity> roleList = roleService.getCanManageRoleList(user.getId());
			request.setAttribute("roleList", roleList);	
			
			String roleIds = "0";
			for(int i=0;i<roleList.size();i++) {
				roleIds +=","+roleList.get(i).getId();
			}
			//获取该角色已分配的权限
			Map<String, Object> rolePrivilegeMap = new HashMap<String, Object>();
			rolePrivilegeMap = rolePrivilegeService.getAllocatedPrivilege(roleIds, 1);
			request.setAttribute("rolePrivilegeMap", rolePrivilegeMap);
			
			//获取该角色可分配的权限树
			Map<String, Object> canAdminPrivilege = new HashMap<String, Object>();
			String menuIds = rolePrivilegeService.getManageMenuIds(roleIds, 2);
			String privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleIds, 2);
			List<MenuEntity> menuList = menuService.fetchlistAndPrivilege(menuIds,privilegeIds);   
			request.setAttribute("menuList", menuList);
			request.setAttribute("privilegeIds", privilegeIds);
		}
		
		jspTpl = "list";
		return jspTpl;
	}
}
