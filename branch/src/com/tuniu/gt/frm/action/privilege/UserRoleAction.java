package com.tuniu.gt.frm.action.privilege;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.UserRoleEntity;
import com.tuniu.gt.frm.service.privilege.IUserRoleService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_privilege-user_role")
@Scope("prototype")
public class UserRoleAction extends FrmBaseAction<IUserRoleService,UserRoleEntity> { 
	
	public UserRoleAction() {
		setManageUrl(manageUrl + "frm/action/privilege/user_role");
	}
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-user_role")
	public void setService(IUserRoleService service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		String res = super.execute(paramMap);
		request.setAttribute("search",search);
		return res;
	}
}
