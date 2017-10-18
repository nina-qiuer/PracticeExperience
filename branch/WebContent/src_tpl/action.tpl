package {?app_dot_dir}{?app_id}.action{?module_dot_dir};

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import {?app_dot_dir}{?app_id}.entity.{?ufirst_table_name}Entity;
import {?app_dot_dir}{?app_id}.service{?module_dot_dir}.I{?ufirst_table_name}Service;
import {?app_dot_dir}{?app_id}.service{?module_dot_dir}.impl.{?ufirst_table_name}ServiceImpl;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("{?app_id}_action{?module_underline_dir}-{?table_name}")
@Scope("prototype")
public class {?ufirst_table_name}Action extends FrmBaseAction<I{?ufirst_table_name}Service,{?ufirst_table_name}Entity> {  
	
	public {?ufirst_table_name}Action() {
		setManageUrl(manageUrl + "{?app_id}/action/{?module_dir}{?table_name}");
	}
	
	@Autowired
	@Qualifier("{?app_id}_service{?module_underline_dir}_impl-{?table_name}")
	public void setService(I{?ufirst_table_name}Service service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);
		String res = "";
		try{  
			res = super.execute(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
			return "sql_error";
		}
		request.setAttribute("search",search);
		return res;
	}
}