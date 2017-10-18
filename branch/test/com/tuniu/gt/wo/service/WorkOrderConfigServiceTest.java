/**
 * 
 */
package com.tuniu.gt.wo.service;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;
import com.tuniu.gt.workorder.service.IWorkOrderConfigService;

/**
 * @author jiangye
 *
 */
public class WorkOrderConfigServiceTest  extends TestCaseExtend{
	IWorkOrderConfigService service  = (IWorkOrderConfigService) Bean.get("wo_service_impl-config");
	
	@Test
	public void testSercvice() throws Exception {
		assertNotNull(service);
	}
	
	@Test
	public void testAdd() throws Exception {
		WorkOrderConfig e = new WorkOrderConfig();
		e.setDepartment("天猫");
		service.insert(e);
	}
	
	
	@Test
	public void testDel() throws Exception {
		service.delete(1);
	}
	
	@Test
	public void testUpdate() throws Exception {
		WorkOrderConfig e= (WorkOrderConfig) service.get(3);
		service.update(e);
	}
	
	@Test
	public void testList() throws Exception {
		service.fetchList();
	}
}
