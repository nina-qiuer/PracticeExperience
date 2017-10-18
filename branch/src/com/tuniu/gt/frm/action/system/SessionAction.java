package com.tuniu.gt.frm.action.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.SessionEntity;
import com.tuniu.gt.frm.service.system.ISessionService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_system-session")
@Scope("prototype")
public class SessionAction extends FrmBaseAction<ISessionService,SessionEntity> { 
	
	public SessionAction() {
		setManageUrl(manageUrl + "frm/action/system/session");
	}
	
	@Autowired
	@Qualifier("frm_service_system_impl-session")
	public void setService(ISessionService service) {
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
