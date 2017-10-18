package com.tuniu.bi.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.bi.gmvrank.dao.impl.GMVRankDao;
import com.tuniu.bi.gmvrank.service.IGMVRankService;
import com.tuniu.bi.gmvrank.service.impl.GMVRankServiceImpl;
import com.tuniu.bi.prdweeksatisfy.dao.impl.PrdWeekSatisfyDao;
import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.dao.impl.FinanceSubbankDao;
import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.punishprd.entity.LowSatisfyRouteEntity;

public class FinanceSubbankTest extends TestCaseExtend{

	FinanceSubbankDao dao = (FinanceSubbankDao)Bean.get("complaint_dao_impl-finance_subbank");
	
	@Test
	public void testGMVDao() throws Exception {
		assertNotNull(dao);
	}
	
	@Test
    public void testGetMaxLastUpdateDate() throws Exception {
        System.out.println(dao.getMaxLastUpdateDate());
    }
	
	@Test
    public void testGet() throws Exception {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("subbankName", "北京市商务中心支行");
        System.out.println(dao.get(paramMap));
    }
	
	@Test
    public void testUpdate() throws Exception {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("subbankName", "北京市商务中心支行");
        FinanceSubbankEntity entity = dao.get(paramMap);
        entity.setBankName("啦啦银行");
        dao.update(entity);
    }
	
}
