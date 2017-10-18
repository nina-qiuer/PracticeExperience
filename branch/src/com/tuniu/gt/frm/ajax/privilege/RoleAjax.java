package com.tuniu.gt.frm.ajax.privilege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.RoleEntity;
import com.tuniu.gt.frm.service.privilege.IRoleService;

import tuniu.frm.core.FrmBaseAjax;

@Service("frm_ajax_privilege-role")
@Scope("prototype")
public class RoleAjax extends FrmBaseAjax {
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	@Qualifier("frm_service_privilege_impl-role")
	private IRoleService service;
	
	
	public String doAddManage() {
		try {
			RoleEntity re = (RoleEntity) service.get(Integer.parseInt(request.getParameter("role_id")));
			String manageIds = re.getManageIds();
			String addId = request.getParameter("new_id"); 
			List<String> ol = Arrays.asList(manageIds.split(","));
			
			
			if(!ol.contains(addId)) { 
				if(!manageIds.equals("")) {
					manageIds +=",";
				}
				manageIds +=addId; 
				re.setManageIds(manageIds); 
				service.update(re);
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("id", addId);  
				ret.put("realName", request.getParameter("new_manage_name"));
				setData(ret);
			} else {
				setErrorData("用户已存在");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setErrorData("出错");
		}
		
		return "ajax";
	}
	
	
	public  String doDelManage() {
		try {
			RoleEntity re = (RoleEntity) service.get(Integer.parseInt(request.getParameter("role_id")));
			String manageIds = re.getManageIds();
			String delId = request.getParameter("manage_id"); 
			List ol = new ArrayList(Arrays.asList(manageIds.split(","))); 
			if(ol.contains(delId)) {
				ol.remove(delId);
				manageIds = ol.toString();
				manageIds = manageIds.substring(1,manageIds.length()-1);
				re.setManageIds(manageIds);
				service.update(re);
			}
			setData("操作成功");
		} catch (Exception e) {
			setErrorData("出错");
			logger.error(e.getMessage(), e);
		}
		
		return "ajax";
	}
}
