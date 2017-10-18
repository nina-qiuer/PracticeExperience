/**
 * 
 */
package com.tuniu.gt.wo.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.workorder.dao.impl.WorkOrderDao;
import com.tuniu.gt.workorder.entity.WorkOrder;

/**
 * @author jiangye
 *
 */
public class WorkOrderTest extends TestCaseExtend {
	
	
	WorkOrderDao dao = (WorkOrderDao) Bean.get("wo_dao_impl-work_order");
	
	@Test
	public void testDao() throws Exception {
		assertNotNull(dao);
	}
	
	@Test
	public void testAdd() throws Exception {
		WorkOrder e = new WorkOrder();
		e.setDepartment("天猫");
		e.setOrderId("123456");
		e.setCustomerName("张三");
		e.setPhone("13850575546");
		e.setPhoneMatter("我就是撩闲");
		e.setAnswerTime("10分钟");
		e.setAddPerson("我加的");
		e.setDealPerson("小红");
		System.out.println(dao.insert(e));
	}
	
	@Test
	public void testGet() throws Exception {
		System.out.println(dao.get(1));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("principal", "小b");
		System.out.println(dao.get(paramMap));
	}
	
	@Test
	public void testUpdate() throws Exception {
		WorkOrder e = dao.get(2);
		e.setDepartment("去哪儿");
		e.setState(2);
		e.setOrderId("67890");
		e.setDealPerson("换人了");
		e.setCustomerName("另一个客户");
		e.setPhone("110");
		e.setPhoneMatter("另一件事儿");
		e.setAnswerTime("尽快吧");
		e.setSolveResult("已经解决");
		dao.update(e);
	}
	
	@Test
	public void testDel() throws Exception {
		dao.delete(1);
	}
	
	@Test
	public void testList() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("department", "去哪儿");
		paramMap.put("addTimeEnd", new Date());
		paramMap.put("state", 2);
		paramMap.put("dealPerson", "换人了");
		dao.fetchList(paramMap);
	}
	
}
