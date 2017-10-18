package com.tuniu.gt.workorder.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.workorder.dao.impl.WorkOrderConfigDao;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;
import com.tuniu.gt.workorder.service.IWorkOrderConfigService;
@Service("wo_service_impl-config")
public class WorkOrderConfigServiceImpl extends ServiceBaseImpl<WorkOrderConfigDao> implements IWorkOrderConfigService
{
	public static final String DEPARTMENTS = "work_order_config_departments";
	private static Logger logger = Logger.getLogger(WorkOrderConfigServiceImpl.class);
	
	
	@Autowired
	@Qualifier("wo_dao_impl-config")
	public void setDao(WorkOrderConfigDao dao) {
		this.dao = dao;
	}
	
	@Override
	public String getPrincipalsByDepartment(String department) {
		return dao.getPrincipalsByDepartment(department);
	}

    @Override
    public List<String> getResponsibleDepartmentList(String principal) {
        return dao.getResponsibleDepartmentList(principal);
    }

    @Override
    public List<String> getDepartmentList() {
        return dao.getDepartmentList();
    }

    @Override
    public List<WorkOrderConfig> getAllConfig() {
        return dao.getAllConfig();
    }


    @Override
    public List<WorkOrderConfig> getResponsibleConfig(String principal) {
        return dao.getResponsibleConfig(principal);
    }

    @Override
    public boolean checkDepartmentExist(WorkOrderConfig config) {
        int count = dao.getDepartmentCount(config);
        return count>0;
    }

    @Override
    public boolean isPrincipalOrAssigner(String userName) {
        return dao.getResponsibleConfigCountByUserName(userName)>0;
    }

	@Override
	public List<WorkOrderConfig> getConfigList() {
		return dao.getConfigList();
	}

	@Override
	public WorkOrderConfig getConfigByDepartmrnt(String parentName) {
		return dao.getConfigByDepartment(parentName);
	}

	@Override
	public List<WorkOrderConfig> getConfigListByPid(int pid) {
		return dao.getConfigListByPid(pid);
	}

}
