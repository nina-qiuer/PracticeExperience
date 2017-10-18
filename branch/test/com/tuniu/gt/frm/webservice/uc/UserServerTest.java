package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.tuniu.gt.TestCaseExtend;

public class UserServerTest extends TestCaseExtend {

	@Test
	public final void testDoAdd() {
		//fail("Not yet implemented"); // TODO
//		UserServer userServer = new UserServer();
//		try {
//			String msgString="{'type':'SUCCESS','code':0,'data':'a%3A14%3A%7Bs%3A2%3A%22id%22%3Bs%3A1%3A%227%22%3Bs%3A9%3A%22user_name%22%3Bs%3A7%3A%22gaoyuan%22%3Bs%3A9%3A%22real_name%22%3Bs%3A6%3A%22%E9%AB%98%E5%AA%9B%22%3Bs%3A8%3A%22password%22%3Bs%3A0%3A%22%22%3Bs%3A5%3A%22email%22%3Bs%3A17%3A%22gaoyuan%40tuniu.com%22%3Bs%3A3%3A%22tel%22%3Bs%3A0%3A%22%22%3Bs%3A6%3A%22mobile%22%3Bs%3A11%3A%2213675158112%22%3Bs%3A9%3A%22extension%22%3Bs%3A5%3A%2288334%22%3Bs%3A7%3A%22worknum%22%3Bs%3A2%3A%2225%22%3Bs%3A9%3A%22can_admin%22%3Bs%3A1%3A%221%22%3Bs%3A6%3A%22dep_id%22%3Bs%3A2%3A%2224%22%3Bs%3A11%3A%22position_id%22%3Bs%3A1%3A%223%22%3Bs%3A6%3A%22job_id%22%3Bs%3A2%3A%2219%22%3Bs%3A15%3A%22blackberry_code%22%3Bs%3A0%3A%22%22%3B%7D','src':{'client_key':'asdfl$(*@#$98234','client_id':0,'file':null,'method':null},'des':{'client_key':null,'client_id':null,'file':null,'method':null},'extra_data':'s%3A0%3A%22%22%3B'}";
//			userServer.doAdd(msgString); 
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Test
	public final void testDoEdit() {
		//fail("Not yet implemented"); // TODO
		UserServer userServer = new UserServer();
		try {
			String msgString="{'type':'SUCCESS','code':0,'data':'a%3A14%3A%7Bs%3A2%3A%22id%22%3Bs%3A2%3A%2218%22%3Bs%3A9%3A%22user_name%22%3Bs%3A9%3A%22chenfuwei%22%3Bs%3A9%3A%22real_name%22%3Bs%3A9%3A%22%E9%99%88%E7%A6%8F%E7%82%9C%22%3Bs%3A8%3A%22password%22%3Bs%3A0%3A%22%22%3Bs%3A5%3A%22email%22%3Bs%3A25%3A%22david.chenfuwei%40gmail.com%22%3Bs%3A3%3A%22tel%22%3Bs%3A0%3A%22%22%3Bs%3A6%3A%22mobile%22%3Bs%3A11%3A%2213813825653%22%3Bs%3A9%3A%22extension%22%3Bs%3A5%3A%2266838%22%3Bs%3A7%3A%22worknum%22%3Bs%3A1%3A%222%22%3Bs%3A9%3A%22can_admin%22%3Bs%3A1%3A%220%22%3Bs%3A6%3A%22dep_id%22%3Bs%3A2%3A%2226%22%3Bs%3A11%3A%22position_id%22%3Bs%3A1%3A%224%22%3Bs%3A6%3A%22job_id%22%3Bs%3A3%3A%22136%22%3Bs%3A15%3A%22blackberry_code%22%3Bs%3A11%3A%2218795951215%22%3B%7D','src':{'client_key':'asdfl$(*@#$98234','client_id':0,'file':null,'method':null},'des':{'client_key':null,'client_id':null,'file':null,'method':null},'extra_data':'a%3A3%3A%7Bs%3A17%3A%22secondary_dep_ids%22%3Ba%3A2%3A%7Bi%3A1746%3Bs%3A2%3A%2227%22%3Bi%3A1744%3Bs%3A2%3A%2225%22%3B%7Ds%3A9%3A%22is_manage%22%3Ba%3A2%3A%7Bi%3A27%3Bs%3A1%3A%221%22%3Bi%3A25%3Bs%3A1%3A%220%22%3B%7Ds%3A10%3A%22op_user_id%22%3Bs%3A3%3A%22961%22%3B%7D'}";
			userServer.doEdit(msgString); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
	}
}