/**
 * 
 */
package com.tuniu.gt.wo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.workorder.dao.impl.WorkOrderConfigDao;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;

/**
 * @author jiangye
 *
 */
public class WorkOrderConfigTest extends TestCaseExtend {
	
	
	WorkOrderConfigDao dao = (WorkOrderConfigDao) Bean.get("wo_dao_impl-config");
	
	@Test
	public void testDao() throws Exception {
		assertNotNull(dao);
	}
	
	@Test
	public void testGet() throws Exception {
		System.out.println(dao.get(2));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("principals", "小b");
		System.out.println(dao.get(paramMap));
	}
	
	@Test
	public void testUpdate() throws Exception {
		WorkOrderConfig e = dao.get(1);
		e.setDepartment("去哪儿");
		dao.update(e);
	}
	
	@Test
	public void testDel() throws Exception {
		dao.delete(1);
	}
	
	@Test
	public void testList() throws Exception {
		dao.fetchList();
	}
	
	@Test
	public void testGetDepartmentList() throws Exception {
		dao.getResponsibleDepartmentList("姜野");
	}
	
	@Test
    public void testPrincipals() throws Exception {
        dao.getPrincipalsByDepartment("天猫");
    }
	
	@Test
    public void testMembers() throws Exception {
	    List<String> departments = new ArrayList<String>();
	    departments.add("天猫");
	    departments.add("去哪儿");
    }
	
	@Test
    public void testtestName() throws Exception {
        System.out.println(dao.getAllConfig());
    }
	
	@Test
    public void testGetResponsibleConfigCount() throws Exception {
        System.out.println(dao.getResponsibleConfigCountByUserName("姜野"));
    }
}
