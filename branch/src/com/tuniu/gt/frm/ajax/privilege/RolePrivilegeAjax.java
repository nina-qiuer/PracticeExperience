package com.tuniu.gt.frm.ajax.privilege;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;


import tuniu.frm.core.FrmBaseAjax;

@Service("frm_ajax_privilege-role_privilege")
@Scope("prototype")
public class RolePrivilegeAjax extends FrmBaseAjax {

	@Autowired
	@Qualifier("frm_service_privilege_impl-role_privilege")
	private IRolePrivilegeService service;
	public String doSystemAllocate() { 
		Integer manageLevel = 4;
		Map<String, String[]> paramMap = request.getParameterMap();
		Integer roleId = Integer.parseInt(paramMap.get("role_id")[0]);
		if(roleId > 0) { 
			service.allocate(roleId, paramMap, manageLevel);
		}
		setData("操作成功");
		return "ajax";
	}
	
	
	public String doSuperAdminAllocate() {
		
		Integer manageLevel = 2;
		Map<String, String[]> paramMap = request.getParameterMap();
		Integer roleId = Integer.parseInt(paramMap.get("role_id")[0]);
		//检测用户有无这个角色操作权限
		if(roleId > 0) { 
			service.adminAllocate(roleId, paramMap, manageLevel);
		}
		setData("操作成功");
		return "ajax";
	}
	
	
	public String doAdminAllocate() {
		Integer manageLevel = 1;
		Map<String, String[]> paramMap = request.getParameterMap();
		Integer roleId = Integer.parseInt(paramMap.get("role_id")[0]);
		//检测用户有无这个角色操作权限
		if(roleId > 0) { 
			service.adminAllocate(roleId, paramMap, manageLevel);
		}
		setData("操作成功");
		return "ajax";
	}  
}
