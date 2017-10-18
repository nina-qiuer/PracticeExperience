package com.tuniu.qms.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.service.QcBillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class TestQcBillService {

	@Autowired
	private QcBillService qcBillService;
	
	@Test
	public void testCount() {
		qcBillService.loadPage(new QcBillDto());
	}

	@Test
	public void testUserAdd() {

	}

	@Test
	public void testGetUserById() {
		
	}

}
