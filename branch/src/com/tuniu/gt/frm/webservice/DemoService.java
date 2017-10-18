package com.tuniu.gt.frm.webservice;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.anotation.Desc;
import tuniu.frm.core.webservice.WebServiceServerBase;

@Service("frm_webservice-demo")
@Scope("prototype")

public class DemoService extends WebServiceServerBase  {
	public DemoService() {
		
	}
	
	@Desc(
		desc="测试方法",
		params={
			"String encripty_key 校验的加密串",
			"String param 参数"
		},
		result="返回值为base64encode(jsonencode(retdata)) <br />" +
				"其中retdata为object 含两个属性type,data<br />" +
				"retdata.type 可选值为success,error<br />" +
				"retdata.data为真实返回的数据" ,
		datas={
			"int id 主键",
			"String desc 描述",
			"int type 类型"
		}, 
		example="http://localhost:8080/ssi/frm/webservice/DemoService?encripty_key=aaaaaaaaaaaaaaaaa&amp;param=bbbbbbbbbb"
	)

	public String test() {
		if(ValidData()) {	
			setData("this is a test and 中文" + INPUT.get("param")); 
		}
		return dataEncode();
	}
	
	
	public String test1() {
		if(ValidData()) {	
			setData("this is a test and 中文" + INPUT.get("param")); 
		}
		return dataEncode();
	}
	
	@Desc(
			desc="测试方法",
			result="返回值为base64encode(jsonencode(retdata))" +
					"其中retdata为object 含两个属性type,data" +
					"retdata.type 可选值为success,error" +
					"retdata.data为真实返回的数据"  
		)
	public String test2() {
		if(ValidData()) {	
			setData("this is a test and 中文" + INPUT.get("param")); 
		}
		return dataEncode();
	}
}
