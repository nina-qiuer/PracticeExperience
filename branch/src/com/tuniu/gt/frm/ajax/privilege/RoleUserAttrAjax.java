package com.tuniu.gt.frm.ajax.privilege;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.RoleUserAttrEntity;
import com.tuniu.gt.frm.service.privilege.IRoleUserAttrService;

import tuniu.frm.core.FrmBaseAjax;
import tuniu.frm.service.Common;

@Service("frm_ajax_privilege-role_user_attr")
@Scope("prototype")
public class RoleUserAttrAjax extends FrmBaseAjax {

	@Autowired
	@Qualifier("frm_service_privilege_impl-role_user_attr")
	private IRoleUserAttrService service;
	
	
	private RoleUserAttrEntity ajaxEntity;
	
	
	
	public RoleUserAttrEntity getAjaxEntity() {
		return ajaxEntity;
	}



	public void setAjaxEntity(RoleUserAttrEntity ajaxEntity) {
		this.ajaxEntity = ajaxEntity;
	}



	public String doAdd() {
		Map<String, String[]> reqMap =  request.getParameterMap();
		String roleIdArr[]  = reqMap.get("role_ids");
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		
	
		sqlMap.put("priId", ajaxEntity.getPriId());
		sqlMap.put("priType", ajaxEntity.getPriType());
		if(ajaxEntity.getPriId() > 0) {
			if(roleIdArr != null) {  
				String notRoleIds = Common.join(roleIdArr); 
				sqlMap.put("notRoleIds", notRoleIds);
			}
			service.delete(sqlMap); 
			if(roleIdArr != null) {  
				for(int i=0;i<roleIdArr.length;i++) {
					ajaxEntity.setRoleId(Integer.parseInt(roleIdArr[i]));
					service.insert(ajaxEntity);
				}
			}
		}
	
		
	
		
		setData("操作成功");
		return "ajax";
	}
}
