package com.tuniu.gt.frm.action.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.TableEntity;
import com.tuniu.gt.frm.service.system.ITableService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;
import tuniu.frm.tools.GenerateCode;


@Service("frm_action_system-table")
@Scope("prototype")
public class TableAction extends FrmBaseAction<ITableService,TableEntity> { 
	
	public TableAction() {
		setManageUrl(manageUrl + "frm/action/system/table");
	}
	
	@Autowired
	@Qualifier("frm_service_system_impl-table")
	public void setService(ITableService service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		perPage = 30;
		String res = super.execute(paramMap);
		request.setAttribute("search",search);
		return res;
	}
	
	public void _preAdd() {
		request.setAttribute("appTblPreMap", constant.appTblPreMap);
	}
	
	
	
	
	public void _aftDoAdd() {
		if(entity.getFormAction() == 1) {
			GenerateCode gc = new GenerateCode(entity.getId());
			gc.makeStrutsPackage();
		}	
	}
	
	
	public void _preEdit() { 
		request.setAttribute("appTblPreMap", constant.appTblPreMap);
	}
	
	
	
	
	
	public void _preDoEdit(Map<String, String> sqlMap) { 
		if(!sqlMap.containsKey("formAction")) {
			sqlMap.put("formAction", "0");
		}
	}
	
	
	public void _aftDoEdit() {
		if(entity.getFormAction() == 1) {
			GenerateCode gc = new GenerateCode(entity.getId());
			gc.makeStrutsPackage();
		}
	}
}
