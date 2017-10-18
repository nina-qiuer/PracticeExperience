package com.tuniu.gt.frm.action.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.tools.CleanField;

@Service("frm_action_util-clean_field")
@Scope("prototype")
public class CleanFieldAction {
	private String pageTitle;
	public String getPageTitle() {
		return pageTitle;
	}
	public String execute() { 
		CleanField cleanField = new CleanField();
		cleanField.doClean();
		pageTitle = "清理字段";
		return "success";
	}
}
