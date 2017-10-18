package com.tuniu.qms.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.access.server.UserServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml", "classpath:SpringMVC.xml" })
public class TestUserController {
//
//	private UserServer uc;
//	
//	@Autowired
//	public void setUc(UserServer uc) {
//		this.uc = uc;
//	}
	
	@Before
	public void before() {
//		String[] resource = new String[] { "Spring.xml", "Spring-MyBatis.xml" };
//		ApplicationContext ac = new ClassPathXmlApplicationContext(resource);
//		us = (UserService) ac.getBean("userService");
//
//		logger = Logger.getLogger(TestUserService.class);
	}
	

	@Test
	public void testUserAdd() {
//		User user = new User();
//		user.setUserName("louis");
//		user.setPassWord("123456");
//		user.setName("周艳");
//		user.setBirthDate(new java.sql.Date(new Date().getTime()));
//
//		us.addUser(user);
//
//		logger.info("Success");

	}

	@Test
	public void testGetUserById() {
//		String str = uc.getUserById(1);
//
//		logger.info("----------:" + str);
	}

}
