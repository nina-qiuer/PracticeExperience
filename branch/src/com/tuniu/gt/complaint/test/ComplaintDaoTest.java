package com.tuniu.gt.complaint.test;

import org.junit.Before;
import org.junit.Test;

import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.entity.ComplaintEntity;

public class ComplaintDaoTest {
	
	
	private ComplaintDao complaint = new ComplaintDao();
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testInsert() {
		int k=10000;
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("order_id", "123456");/
//		data.put("guest_name", "测试�?);
		ComplaintEntity e=new ComplaintEntity();
		e.setComeFrom("");
		e.setOrderId(123456);
		e.setCustomer("测试数据");
//		complaint.insert(e);
		for(int i=0;i<k;i++){
			complaint.insert(e);
		}
//		fail("Not yet implemented");
	}

}
