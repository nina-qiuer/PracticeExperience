package com.tuniu.gt.frm.action.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.LogEntity;
import com.tuniu.gt.frm.service.log.ILogService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_log-log")
@Scope("prototype")
public class LogAction extends FrmBaseAction<ILogService,LogEntity> {  
	
	private Logger logger = Logger.getLogger(getClass());
	
	public LogAction() {
		setManageUrl(manageUrl + "frm/action/log/log");
	}
	
	@Autowired
	@Qualifier("frm_service_log_impl-log")
	public void setService(ILogService service) {
		this.service = service;
	};
	
	public String execute()  {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		String res = "";
		try{
			res = super.execute(paramMap); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "sql_error";
		}
		request.setAttribute("search",search);
		return res;
	}
}
