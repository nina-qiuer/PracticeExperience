package com.tuniu.gt.complaint.service.impl;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.service.IComplaintTSPService;

public class BOHTest extends TestCaseExtend{

	private IComplaintTSPService service = (IComplaintTSPService)Bean.get("tspService");
	
	@Test
	public void testQueryMainRouteId() {
		System.out.println(service.queryMainRouteId(22576746));
	}
	
	@Test
	public void testGetProductInfo() throws Exception {
		System.out.println(service.getProductInfo(12345L));
	}
	

}
