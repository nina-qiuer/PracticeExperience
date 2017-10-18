package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcEntity;

public class QcDaoTest extends TestCaseExtend{

	QcDao dao = (QcDao)Bean.get("complaint_dao_impl-qc");
	
	@Test
	public void testGetQcListByState() {
//		IQcQuestionService service = (IQcQuestionService)Bean.get("complaint_service_impl-qc_question");
		assertNotNull(dao);
		
		int state = 1;
		List<QcEntity> qcs = dao.getQcListByState(state);
		assertNotNull(qcs);
	}

	@Test
	public void testSearch() {
		assertNotNull(dao);
		
		int state = 0;
		int orderId = 111;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("state", state);
		List<QcEntity> qcs = dao.search(paramMap);
		assertNotNull(qcs);
	}
}
