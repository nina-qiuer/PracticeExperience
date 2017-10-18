package com.tuniu.gt.workorder.dao.sqlmap.imap;


import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;

import tuniu.frm.core.IMapBase;

@Repository("wo_dao_sqlmap-config")
public interface IWorkOrderConfigMap extends IMapBase 
{
	/**
	 * 根据姓名获取所负责的部门列表
	 * @param principal
	 * @return
	 */
	List<String > getResponsibleDepartmentList(String principal);
	
	List<String> getDepartmentList();

	String getPrincipalsByDepartment(String department);
	
	
	@Select("select * from wo_config where delFlag=0")
	List<WorkOrderConfig>  getAllConfig();
	
	List<WorkOrderConfig> getResponsibleConfig(String principal);
	
	/**
	 * 根据姓名获取配置项中的数量（负责人或者是分配人所在配置的数量）
	 * @param userName
	 * @return
	 */
	Integer getResponsibleConfigCountByUserName(String userName);

    /**
     * @param config
     * @return
     */
	
    int getDepartmentCount(WorkOrderConfig config);

	List<WorkOrderConfig> getConfigList();

	WorkOrderConfig getConfigByDepartment(String name);

	List<WorkOrderConfig> getConfigListByPid(int pid);
}
