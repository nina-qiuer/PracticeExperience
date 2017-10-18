package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.junit.Test;

import tuniu.frm.service.Bean;
import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.service.IQcQuestionClassService;

public class QcQuestionClassServiceImplTest extends TestCaseExtend{

	@Test
	public void test() {
		IQcQuestionClassService qcService = (IQcQuestionClassService)Bean.get("complaint_service_impl-qc_question_class");		
		assertNotNull(qcService);
		List<QcQuestionClassEntity> li = qcService.list();
		assertNotNull(li);
		QcQuestionClassEntity qc = li.get(0);
		System.out.println(qc);
	}

}
;