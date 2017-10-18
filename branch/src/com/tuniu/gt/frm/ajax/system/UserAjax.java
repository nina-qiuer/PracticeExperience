package com.tuniu.gt.frm.ajax.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;

import tuniu.frm.core.FrmBaseAjax;

@Service("frm_ajax_system-user")
@Scope("prototype")
public class UserAjax extends FrmBaseAjax { 

	private static final long serialVersionUID = 4923385266768381401L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService service;
	
	public String getMatchUserListByName() {
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("fields", "id,user_name,real_name");
		sqlParam.put("matchq", request.getParameter("q"));
		sqlParam.put("dataLimitStart", 0);
		sqlParam.put("dataLimitEnd", Integer.parseInt(request.getParameter("limit"))); 
		try {
			List<Map> userList = (List<Map>) service.fetchListMap(sqlParam);
			setData(userList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "ajax";
	}
	
	public String getUserListByDepartmentId() {

		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
				
		try {
			@SuppressWarnings("unchecked")
			List<UserEntity> userList = (List<UserEntity>) service.getUsersByDepartmentId(departmentId);
			setData(userList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "ajax";
	}
}
