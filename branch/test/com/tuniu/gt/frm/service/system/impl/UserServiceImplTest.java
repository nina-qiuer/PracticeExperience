package com.tuniu.gt.frm.service.system.impl;

import java.util.List;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;

public class UserServiceImplTest extends TestCaseExtend {

	IUserService service = (IUserService)Bean.get("frm_service_system_impl-user");		
	
	@Test
	public void testGetUsersByJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersByDepartmentId() {
		assertNotNull(service);
		int departmentId = 24;
		List<UserEntity> users = service.getUsersByDepartmentId(departmentId);
		assertNotNull(users);
	}

}
