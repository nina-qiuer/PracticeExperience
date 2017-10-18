/**
 * 
 */
package com.tuniu.gt.tac;

import java.util.Date;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.tac.dao.impl.PrdProcurementIssuesFeedbackDao;
import com.tuniu.gt.tac.entity.PrdProcurementIssusFeedbak;

/**
 * @author jiangye
 *
 */
public class PrdProcurementIssuesFeedbackTest extends TestCaseExtend {
	
	
    PrdProcurementIssuesFeedbackDao dao = (PrdProcurementIssuesFeedbackDao) Bean.get("tac_dao_impl-prdProcurementIssuesFeedback");
	
	@Test
	public void testDao() throws Exception {
		assertNotNull(dao);
	}
	
	@Test
    public void testInsert() throws Exception {
	    PrdProcurementIssusFeedbak e = new PrdProcurementIssusFeedbak();
	    e.setAddPerson("姜野");
	    e.setArea("上海");
	    e.setDepartment("华东一区");
	    e.setDescription("啦啦啦");
	    e.setDestType("欧洲跟团");
	    e.setIssueType("行程、航班、酒店变更");
	    e.setOrderId("123456789");
	    e.setPrdMajordomo("张传力");
	    e.setPrdOfficer("姜野");
	    e.setPrdManager("姜野啊");
	    e.setResolveState(0);
	    e.setRouteId("21011119890205");
	    e.setDepartDate(new Date());
	    e.setSupplier("供应商啊");
	    dao.insert(e);
	    
    }
	
	@Test
    public void testGet() throws Exception {
        System.out.println(dao.get(1));
    }
	
	@Test
    public void testUpdate() throws Exception {
	    PrdProcurementIssusFeedbak e =dao.get(1);
	    e.setAddPerson("王明方");
	    dao.update(e);
    }
	
}
