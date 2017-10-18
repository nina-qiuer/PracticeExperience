package com.tuniu.gt.complaint.service.impl;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.entity.QcTrackerEntity;
import com.tuniu.gt.complaint.service.IQcQuestionService;

public class QcQuestionServiceImplTest extends TestCaseExtend {

	IQcQuestionService qcService = (IQcQuestionService)Bean.get("complaint_service_impl-qc_question");
	
	@Test
	public void testDeleteQuestionsByQid() {		
		assertNotNull(qcService);
		
		int qid = 6;
		qcService.deleteQuestionsByReportId(qid);
	}

	@Test
	public void testAdd() {
		assertNotNull(qcService);
		
		QcQuestionEntity question = new QcQuestionEntity();
		question.setDelFlag(0);
		QcTrackerEntity tracker = new QcTrackerEntity();
		tracker.setDelFlag(0);
		question.getTrackers().add(tracker);
		
		qcService.add(question);		
	}
}
