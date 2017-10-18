package com.tuniu.gt.frm.action.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBase;

import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;
import tuniu.frm.tools.Db;
import tuniu.frm.tools.GenerateCode;

@Service("frm_action_util-rebuild") 
@Scope("prototype")
public class RebuildAction extends FrmBase {
	
	private Db db = new Db();
	
	public RebuildAction() {
		db.connect();
	}
	

	public String execute() {
		pageTitle = "重建应用";
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(request.getParameterMap(),"search.");
		
		
		if(!search.containsKey("app_id")) {
			if(request.getParameter("app_id") != null) {
				search.put("app_id", request.getParameter("app_id"));
			} else {
				search.put("app_id", "frm");
			}
		}
		
//		GenerateCode gc  = new GenerateCode();
//		gc.makeAll();
		
		
		String sql = "select * from "+Constant.appTblPreMap.get("frm")+"table where app_id='"+search.get("app_id")+"'";
		List<Map> dataList = db.fetchList(sql);
		request.setAttribute("appTblPreMap", Constant.appTblPreMap);
		request.setAttribute("search", search); 
		request.setAttribute("dataList", dataList);
		jspTpl = "rebuild_list";
		
		return jspTpl;
	}
	
	
	public String doRebuild() {
		Integer tableId =  Integer.parseInt(request.getParameter("table_id").toString());
		String modules = request.getParameter("modules");
		if("".equals(modules)) {
			String aModule[] = modules.split(",");
			GenerateCode gc = new GenerateCode(tableId);
			gc.setaModules(aModule);
			gc.make(true);
			
		}
		String sql = "select app_id from "+Constant.appTblPreMap.get("frm")+"table where id=" + tableId + " limit 1";
		
		Map<String, String> tableInfo = db.fetchOne(sql);
		String appId = tableInfo.get("app_id");
		redirctUrl = "/frm/action/util/rebuild?app_id="+appId;
		System.out.println(redirctUrl);
		return "redirect";
	}
}
	

