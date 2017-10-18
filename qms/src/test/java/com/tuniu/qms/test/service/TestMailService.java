package com.tuniu.qms.test.service;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.service.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class TestMailService {

	@Autowired
	private MailService mailService;
	
	@Test
	public void testService(){
		System.out.println(mailService);
	}
	
	@Test
	public void testAdd(){
		MailTask mail = new MailTask();
		mail.setReAddrs("jiangye@tuniu.com;chenhaitao@tuniu.com".split(","));
		mail.setCcAddrs("jiangye@tuniu.com;chenhaitao@tuniu.com".split(","));
		mail.setSubject("SubjectTest");
		mail.setContent("ContentTest");
		//mailService.add(mail);
	}
	
	@Test
	public void testAddDto(){
		MailTaskDto mailDto = new MailTaskDto();
		mailDto.setReAddrs("jiangye@tuniu.com;chenhaitao@tuniu.com;wangmingfang@tuniu.com".split(";"));
		mailDto.setCcAddrs("jiangye@tuniu.com;chenhaitao@tuniu.com;wangmingfang@tuniu.com".split(";"));
		mailDto.setSubject("SubjectTest");
		mailDto.setTemplateName("mail.ftl");
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("username", "jiangye");
		mailDto.setDataMap(dataMap);
		//mailService.add(mailDto);
	}
	
	@Test
	public void testDelete() {
		//mailService.delete(1);
	}
	
	@Test
	public void testUpdate() {
		MailTask obj = new MailTask();
		//obj = mailService.get(2);
		//obj.setFailTimes(obj.getFailTimes()+3);
		//mailService.update(obj);
	}
	
	@Test
	public void testGet(){
		//MailTask  mail = mailService.get(2);
		//System.out.println(JSONObject.toJSONString(mail));
	}
	
	@Test
	public void testList(){
		//System.out.println(mailService.list(null));
	}
	
	@Test
	public void testSendEmail() {
		//mailService.sendEmail(mailService.get(10));
	}
	
}
