package com.tuniu.gt.frm.action.privilege;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IMenuPrivilegeService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_privilege-menu_privilege")
@Scope("prototype")
public class MenuPrivilegeAction extends FrmBaseAction<IMenuPrivilegeService,MenuPrivilegeEntity> { 
	
	public MenuPrivilegeAction() {
		setManageUrl(manageUrl + "frm/action/privilege/menu_privilege");
	}
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu_privilege")
	public void setService(IMenuPrivilegeService service) {
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
