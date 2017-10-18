package com.tuniu.gt.complaint.service.impl;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.scheduling.AssignCompalintList;

public class ComplaintFollowNoteServiceTest extends TestCaseExtend{

	ComplaintFollowNoteServiceImpl service = (ComplaintFollowNoteServiceImpl) Bean.get("complaint_service_complaint_impl-complaint_follow_note"); 
	
	@Test
	public void testService() throws Exception{
		assertNotNull(service);
	}
}
