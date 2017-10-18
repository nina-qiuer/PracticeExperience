package com.tuniu.gt.frm.action.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBase;
import tuniu.frm.tools.ResultMap;;

@Service("frm_action_util-sql_result_map")
@Scope("prototype")
public class SqlResultMapAction extends FrmBase{
	
	
	@SuppressWarnings("unchecked")
	public String execute() {
	
		pageTitle = "填写sql";
		jspTpl ="sql_form";
		return jspTpl; 
	}
	
	
	public String doFormat() {
		ResultMap resultMap = new ResultMap();
		String sql = request.getParameter("sql");	
		String sqlResult = resultMap.formatResultMap(sql);
		request.setAttribute("sql", sql);
		request.setAttribute("sqlResult", sqlResult); 
		return jspTpl;
	}
	
	
}
