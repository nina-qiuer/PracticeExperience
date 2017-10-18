package com.tuniu.gt.frm.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import tuniu.frm.core.webservice.WebServiceClientBase;

public class DemoClient {
	
	@Autowired
	private WebServiceClientBase xBase = new  WebServiceClientBase();
	public String execute() {
//		xBase.http("http://www.baidu.com"); call http
		Map<String, String> xMap = new HashMap<String, String>();
		xMap.put("aa", "bb");
		Vector<Map> params = new Vector<Map>(); //call xmlrpc 
		params.add(xMap);
		xBase.xmlrpc("http://localhost/tuniu/crmst/new_frm/xmlrpc/test.server.php", "TuniuBoss.t", params); 
		return "xxx";
	}
}
