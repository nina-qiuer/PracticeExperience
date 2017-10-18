package com.tuniu.gt.frm.action.log;

import org.junit.Test;

import com.tuniu.gt.TestCaseExtend;

import tuniu.frm.service.Bean;

public class LogActionTest extends TestCaseExtend {

	@Test
	public final void testSetService() {
		LogAction la = (LogAction) Bean.get("frm_action_log-log");
	
	}
	
	public final void testExecute() {

	}

}
