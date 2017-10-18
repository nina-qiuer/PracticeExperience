package com.tuniu.gt.frm.service.privilege.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.UserRoleDao;
import com.tuniu.gt.frm.service.privilege.IUserRoleService;
@Service("frm_service_privilege_impl-user_role")
public class UserRoleServiceImpl extends ServiceBaseImpl<UserRoleDao> implements IUserRoleService {
	@Autowired
	@Qualifier("frm_dao_impl-user_role")
	public void setDao(UserRoleDao dao) {
		this.dao = dao;
	};
}
