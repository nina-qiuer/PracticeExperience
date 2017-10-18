package com.tuniu.gt.workorder.dao.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintMap;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcNoteMap;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcNoteEntity;
import com.tuniu.gt.workorder.dao.sqlmap.imap.IWorkOrderConfigMap;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;

@Repository("wo_dao_impl-config")
public class WorkOrderConfigDao extends DaoBase<WorkOrderConfig, IWorkOrderConfigMap>  implements IWorkOrderConfigMap 
{
	public WorkOrderConfigDao() {  
		tableName = Constant.appTblPreMap.get("workorder") + "config";
	}
	
	@Autowired
	@Qualifier("wo_dao_sqlmap-config")
	public void setMapper(IWorkOrderConfigMap mapper) {
		this.mapper = mapper;
	}

	/**
	 * @param department
	 * @return
	 */
	@Override
	public String getPrincipalsByDepartment(String department) {
		return mapper.getPrincipalsByDepartment(department);
	}

    @Override
    public List<String> getResponsibleDepartmentList(String principal) {
        return mapper.getResponsibleDepartmentList(principal);
    }

    @Override
    public List<String> getDepartmentList() {
        return mapper.getDepartmentList();
    }


    @Override
    public List<WorkOrderConfig> getAllConfig() {
        return mapper.getAllConfig();
    }

    @Override
    public List<WorkOrderConfig> getResponsibleConfig(String principal) {
        return mapper.getResponsibleConfig(principal);
    }

    /**
     * @param config
     * @return
     */
    public int getDepartmentCount(WorkOrderConfig config) {
        return mapper.getDepartmentCount(config);
    }

    @Override
    public Integer getResponsibleConfigCountByUserName(String userName) {
        return mapper.getResponsibleConfigCountByUserName(userName);
    }

	public List<WorkOrderConfig> getConfigList() {
		return mapper.getConfigList();
	}

	@Override
	public WorkOrderConfig getConfigByDepartment(String name) {
		 return mapper.getConfigByDepartment(name);
	}

	public List<WorkOrderConfig> getConfigListByPid(int pid) {
		return mapper.getConfigListByPid(pid);
	}
    
}
