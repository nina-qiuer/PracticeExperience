/**
 * 
 */
package com.tuniu.gt.workorder.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;
import com.tuniu.gt.workorder.service.IWorkOrderConfigService;

/**
 * @author jiangye
 *
 */
@SuppressWarnings("serial")
@Service("wo_action-config")
@Scope("prototype")
public class WorkOrderConfigAction  extends FrmBaseAction<IWorkOrderConfigService, WorkOrderConfig> {
	
	private Integer id; // 配置序号
	private WorkOrderConfig config;
	private Map<String,Object> info;
	
	public WorkOrderConfigAction() {
		setManageUrl("wo");
		info = new HashMap<String, Object>();
	}
	
	@Autowired
	@Qualifier("wo_service_impl-config")
	public void setService(IWorkOrderConfigService service) {
		this.service = service;
	}
	
	public  String execute() {
		this.setPageTitle("工单配置管理");
		String jspUrl = "";
		
		//配置权限过滤（负责人和分配人有权限进行配置）
		boolean hasConfigAuthority = false;
		
		//初始时方有明和严敏有权限配置,其余项目负责人、分配人有权限
		if("方有明".equals(user.getRealName()) || "严敏".equals(user.getRealName())) {
		    hasConfigAuthority = true;
		}else{
			hasConfigAuthority = service.isPrincipalOrAssigner(user.getRealName());
		}
		
		if(!hasConfigAuthority) {
		    jspUrl = "authority_not_enough";
		}else {
		    List<WorkOrderConfig> dataList = service.getConfigList();
		    request.setAttribute("dataList", dataList);
		    jspUrl = "config_list";
		}
		
		return jspUrl;
	}
	
	public String toAddOrUpdate(){
		if(null != id && id > 0){
			config = (WorkOrderConfig) service.get(id);
		}
		
		return "config_form";
	}
	
	public String addOrUpdate() {
	    boolean exist = service.checkDepartmentExist(config);
        
	    if(exist) {
            info.put("success", false);
            info.put("msg", "该项目组已经存在");
        }else{
        	
        	if(null != config.getParentName() && !("").equals(config.getParentName())){
        		WorkOrderConfig wo = service.getConfigByDepartmrnt(config.getParentName());
        		
        		if(null != wo){
        			config.setPid(wo.getId());
	        	}else{
	        		info.put("success", false);
	                info.put("msg", "该父项目组不存在");
	                return "info";
	        	}
        	}else{
        		config.setPid(0);
        	}
        	
    		if(config.getId() != null){
                service.update(config);
                info.put("msg", "更新成功");
            }else {
                service.insert(config);
                info.put("msg", "添加成功");
            }
            info.put("success", true);
        }
	    
        return "info";
	}
	
	public String checkDepartmentNameExist(){
		WorkOrderConfig wo = new WorkOrderConfig();
		wo.setDepartment((String) request.getParameter("departmentName"));
		
		if(service.checkDepartmentExist(wo)) {
            info.put("success", true);
            info.put("msg", "该项目组存在");
        }else{
        	info.put("success", false);
            info.put("msg", "该项目组不存在");
        }
		
        return "info";
	}
	
	public String delete(){
		service.delete(id);
		return "success";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkOrderConfig getConfig() {
		return config;
	}

	public void setConfig(WorkOrderConfig config) {
		this.config = config;
	}

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
	
	
}
