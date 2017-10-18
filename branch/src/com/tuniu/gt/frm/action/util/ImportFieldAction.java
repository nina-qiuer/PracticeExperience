package com.tuniu.gt.frm.action.util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.tools.*;

@Service("frm_action_util-import_field")
@Scope("prototype")
public class ImportFieldAction {
	private String pageTitle;
	public String getPageTitle() {
		return pageTitle;
	}
	public String execute() {

		ImportField importField = new ImportField();
		importField.doImport();
		pageTitle = "导入字段";
		return "success";
	}
}