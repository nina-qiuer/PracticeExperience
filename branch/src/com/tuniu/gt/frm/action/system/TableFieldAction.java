package com.tuniu.gt.frm.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.TableFieldEntity;
import com.tuniu.gt.frm.service.system.IControlService;
import com.tuniu.gt.frm.service.system.ITableFieldService;
import com.tuniu.gt.frm.service.system.ITableService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;
import tuniu.frm.tools.GenerateCode;


@Service("frm_action_system-table_field")
@Scope("prototype")
public class TableFieldAction extends FrmBaseAction<ITableFieldService,TableFieldEntity> { 
	
	private Logger logger = Logger.getLogger(getClass());
	
	private List<TableFieldEntity> fieldList;
	public List<TableFieldEntity> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<TableFieldEntity> fieldList) {
		this.fieldList = fieldList;
	}
	
	public TableFieldAction() {
		setManageUrl(manageUrl + "frm/action/system/table_field");
	}
	
	@Autowired
	@Qualifier("frm_service_system_impl-table_field")
	public void setService(ITableFieldService service) {
		this.service = service;
	};
	
	
	
	@Autowired
	@Qualifier("frm_service_system_impl-table")
	private ITableService tableService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-control")
	private IControlService controlService;
	
	
	public String execute() {
		
		this.setPageTitle("字段管理");	
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search); 
		
		Integer tableId = Integer.parseInt(getRequestParam("table_id"));
		
		if(tableId == 0 ) {	
			tableId = 1;
		} 
		paramMap.put("tableId",tableId);
		callbackMap.put("pre", "preList");
		request.setAttribute("table_id",tableId); 
		String res = super.execute(paramMap,-1);

		return res;
	}
	
	
	public void preList() { 

		
		try {
			
			List<Object> tableList = (List<Object>)tableService.fetchListMap(); 
			String appId = "";
			String fullTableName = "";
			for(int i=0;i<tableList.size();i++) {
				Map<String,Object> tmpMap = new HashMap<String, Object>();
				tmpMap = (Map<String,Object>)tableList.get(i);
				appId = tmpMap.get("appId").toString();
				fullTableName = Constant.appTblPreMap.get(appId) + tmpMap.get("tableName").toString();
				tmpMap.put("fullTableName", fullTableName);
			}
			request.setAttribute("tableList", tableList); 
			
			@SuppressWarnings("unchecked")
			List<Object> controlList = (List<Object>)controlService.fetchListMap(); 
			request.setAttribute("controlList", controlList); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	public String doEdit() {
		Integer tableId = Integer.parseInt(getRequestParam("table_id"));
		request.setAttribute("table_id",tableId);
		Map<String, String[]> queryParamMap = getRequestParam();
		
		int i=0;
		while(null != getRequestParam("fieldList["+i+"].id")) {
			Map<String, String> sqlParamMap = Common.getSqlMap(queryParamMap,"fieldList["+i+"]."); 
			
			service.update(sqlParamMap);
			i++;
		}
		GenerateCode gc = new GenerateCode(tableId);
		gc.make();  
		return execute();
		
	}
}
