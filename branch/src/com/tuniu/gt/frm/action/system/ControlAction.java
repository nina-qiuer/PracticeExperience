package com.tuniu.gt.frm.action.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.ControlEntity;
import com.tuniu.gt.frm.service.system.IControlService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_system-control")
@Scope("prototype")
public class ControlAction extends FrmBaseAction<IControlService,ControlEntity> { 
	
	public ControlAction() {
		setManageUrl(manageUrl + "frm/action/system/control");
	}
	
	@Autowired
	@Qualifier("frm_service_system_impl-control")
	public void setService(IControlService service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		String res = super.execute(paramMap);
		request.setAttribute("search",search);
		return res;
	}
}
