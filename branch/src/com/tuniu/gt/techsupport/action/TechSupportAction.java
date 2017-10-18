/**
 * 
 */
package com.tuniu.gt.techsupport.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.techsupport.entity.TechSupportEntity;
import com.tuniu.gt.techsupport.service.ITechSupportService;

/**
 * @author jiangye
 *
 */
@Service("ts_action-ts")
@Scope("prototype")
public class TechSupportAction extends FrmBaseAction<ITechSupportService, TechSupportEntity>{

	private String sqlParam;
	private Map<String,Object>  info;
	
	


	public TechSupportAction() {
		info = new HashMap<String, Object>();
	}

	@Autowired
	@Qualifier("ts_service_impl-tech_support")
	public void setService(ITechSupportService service) {
		this.service = service;
	}
	
	public String index(){
		return "index";
	}
	
	public String execute(){
			System.out.println(sqlParam);
			String[] sqls = sqlParam.split(";");
			try {
				for (String sql : sqls) {
					service.execute(sql);
				}
			} catch (Exception e) {
				info.put("success", false);
				info.put("msg", e.getMessage());
				return "info";
			}
			
			info.put("success", true);
			info.put("msg", "操作成功");
			
			return "info";
	}
	
	public String getSqlParam() {
		return sqlParam;
	}

	public void setSqlParam(String sqlParam) {
		this.sqlParam = sqlParam;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
