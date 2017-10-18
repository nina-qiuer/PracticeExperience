package com.tuniu.qms.test.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.common.task.DataSyncTask;
import com.tuniu.qms.common.task.MailSendTask;
import com.tuniu.qms.qs.monitor.OrderMonitor;
import com.tuniu.qms.qs.monitor.ProductMonitor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class SendMailTaskTest {
	
	@Autowired
	private MailSendTask sendEmailTask;
	
	@Autowired
	private DataSyncTask dsTask;
	
	@Autowired
	private OrderMonitor mo;
	
	@Autowired
	private ProductMonitor po;
	
	@Test
	public void testExecute(){
		sendEmailTask.sendEmail();
		//mo.addSubstdOrderSnapshot();
		//po.addSubstdProductSnapshot();
//		dsTask.syncComplaint();
	}
	
}
