package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.tuniu.gt.TestCaseExtend;

public class DepartmentServerTest extends TestCaseExtend {

	@Test
	public final void testDoEdit() {
		DepartmentServer departmentServer = new DepartmentServer();
		try { 
			String msgString="{'type':'SUCCESS','code':0,'data':'a%3A3%3A%7Bs%3A2%3A%22id%22%3Bs%3A3%3A%22644%22%3Bs%3A7%3A%22depName%22%3Bs%3A9%3A%22%E7%A0%94%E5%8F%91%E9%83%A8%22%3Bs%3A8%3A%22fatherId%22%3Bs%3A3%3A%22235%22%3B%7D','src':{'client_key':'asdfl$(*@#$98234','client_id':0,'file':null,'method':null},'des':{'client_key':null,'client_id':null,'file':null,'method':null},'extra_data':'s%3A0%3A%22%22%3B'}";
			departmentServer.doEdit(msgString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
	}

}
