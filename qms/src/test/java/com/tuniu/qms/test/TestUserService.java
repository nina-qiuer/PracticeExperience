package com.tuniu.qms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.common.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring.xml"})
public class TestUserService {

	@Autowired
	private UserService us;
	
	@Test
	public void testCmpMapper() {
		//syncCmpDataTask.syncCmpData();
	}

	@Test
	public void testUserAdd() {
		//tspService.getProductInfo(25440022);
	}

	@Test
	public void testGetUserById() {
		
	}

}
