package com.tuniu.gt.frm.action.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.tools.*;

@Service("frm_util-gen")
@Scope("prototype")
public class GenAction {
	
	private String pageTitle;
	public String getPageTitle() {
		return pageTitle;
	}
	@SuppressWarnings("unchecked")
	public String execute()
	{
		
		GenerateCode gCode = new GenerateCode();
		gCode.makeAll(); 
		pageTitle = "生成代码";
		return "success"; 
	}
	
	

}
