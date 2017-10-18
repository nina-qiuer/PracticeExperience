package com.tuniu.gt.complaint.service.impl;

import org.junit.Test;

import tuniu.frm.service.Bean;
import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.service.IQcService;

public class QCServiceImplTest extends TestCaseExtend {
	private IQcService qcService = (IQcService)Bean.get("complaint_service_impl-qc");	
	
	@Test
	public void testAdd() {	
		assertNotNull(qcService);
		QcEntity qc = new QcEntity();
		qc.setId(5);
		qc.setOrderId(111);
		
		int id = qcService.add(qc);
		System.out.println(id);
		
	}
	
	@Test 
	public void testFind() {
		assertNotNull(qcService);
		int id = 6;
		QcEntity qc = qcService.find(id);
		assertNotNull(qc);
	}
	

}
