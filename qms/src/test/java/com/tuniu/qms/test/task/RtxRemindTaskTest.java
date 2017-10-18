package com.tuniu.qms.test.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.common.task.RtxRemindTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class RtxRemindTaskTest {
	
	@Autowired
	private RtxRemindTask rtxRemind;
	
	@Test
	public void testRemind(){
		rtxRemind.execute();
	}
}
