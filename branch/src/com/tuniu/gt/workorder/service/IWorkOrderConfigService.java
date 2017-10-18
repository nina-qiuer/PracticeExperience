package com.tuniu.gt.workorder.service;

import java.util.List;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;

import tuniu.frm.core.IServiceBase;

public interface IWorkOrderConfigService extends IServiceBase
{
    /**
     * 根据姓名获取所负责的部门列表
     * @param principal
     * @return
     */
    List<String >  getResponsibleDepartmentList(String principal);
	
    List<String> getDepartmentList();
	
    String getPrincipalsByDepartment(String department);
    
    List<WorkOrderConfig>  getAllConfig();
    
    List<WorkOrderConfig> getResponsibleConfig(String principal);

    /**
     * @param config
     */
    boolean checkDepartmentExist(WorkOrderConfig config);
    
    boolean isPrincipalOrAssigner(String userName);

	List<WorkOrderConfig> getConfigList();

	WorkOrderConfig getConfigByDepartmrnt(String parentName);

	List<WorkOrderConfig> getConfigListByPid(int pid);
	
}
