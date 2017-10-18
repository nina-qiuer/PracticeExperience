package com.tuniu.gt.complaint.service.impl;

import org.junit.Test;
import org.springframework.util.Assert;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.mq.ComplaintResultMQProducer;


public class CompMQProTest extends TestCaseExtend {
	
	private ComplaintResultMQProducer complaintResultMQProducer = (ComplaintResultMQProducer) Bean.get("complaintResultMQProducer");
	
	@Test
	public void testSend() {
		Assert.notNull(complaintResultMQProducer);
		complaintResultMQProducer.sendMessage("222", "Success");
	}

}
