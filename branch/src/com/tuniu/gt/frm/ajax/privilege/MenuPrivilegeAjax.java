package com.tuniu.gt.frm.ajax.privilege;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IMenuPrivilegeService;


import tuniu.frm.core.FrmBaseAjax;



@Service("frm_ajax_privilege-menu_privilege")
@Scope("prototype")
public class MenuPrivilegeAjax extends FrmBaseAjax {

	@Autowired
	@Qualifier("frm_service_privilege_impl-menu_privilege")
	private IMenuPrivilegeService service;
	

	private transient MenuPrivilegeEntity ajaxEntity;		
	public MenuPrivilegeEntity getAjaxEntity() {
		return ajaxEntity;
	}
	public void setAjaxEntity(MenuPrivilegeEntity ajaxEntity) {
		this.ajaxEntity = ajaxEntity;
	}


	public MenuPrivilegeAjax() {
		
	}
	
	public String edit() {
		Integer id = Integer.parseInt(request.getParameter("id"));
		ajaxEntity = (MenuPrivilegeEntity) service.get(id);
		setData(ajaxEntity);
		return "ajax";
	}
	

	
	public String getPrivilege() { 
		Integer menuId = Integer.parseInt(request.getParameter("menu_id")); 
		List<MenuPrivilegeEntity> list = service.getListByMenuId(menuId);
		setData(list);
		return "ajax";
	}
	
	
	
	public String doDel() {
		Integer id = Integer.parseInt(request.getParameter("id"));
		service.delete(id);
		setData(id);
		return "ajax";
	}
	
	public String doAdd() {
	
		service.doAdd(ajaxEntity);
		if(ajaxEntity.getId() > 0) {
			setData(ajaxEntity);
		} else {
			setErrorData("error");
		}
		return "ajax";
	}
}
