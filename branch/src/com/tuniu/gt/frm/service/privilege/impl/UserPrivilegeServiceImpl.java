package com.tuniu.gt.frm.service.privilege.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.UserPrivilegeDao;
import com.tuniu.gt.frm.service.privilege.IUserPrivilegeService;
@Service("frm_service_privilege_impl-user_privilege")
public class UserPrivilegeServiceImpl extends ServiceBaseImpl<UserPrivilegeDao> implements IUserPrivilegeService {
	@Autowired
	@Qualifier("frm_dao_impl-user_privilege")
	public void setDao(UserPrivilegeDao dao) {
		this.dao = dao;
	};
}
