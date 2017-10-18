package com.tuniu.gt.complaint.service.impl;

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
import com.tuniu.gt.complaint.service.IFinanceSubbankService;
import com.tuniu.gt.punishprd.entity.LowSatisfyRouteEntity;

public class FinanceSubbankTest extends TestCaseExtend{

	IFinanceSubbankService service = (IFinanceSubbankService)Bean.get("complaint_service_complaint_impl-finance_subbank");
	
	@Test
	public void testService() throws Exception {
		assertNotNull(service);
	}
	
	@Test
    public void testGetMaxLastUpdateDate() throws Exception {
        System.out.println(service.getMaxLastUpdateDate());
    }
	
}
