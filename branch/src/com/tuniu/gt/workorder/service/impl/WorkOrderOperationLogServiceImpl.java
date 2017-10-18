package com.tuniu.gt.workorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.workorder.dao.impl.WorkOrderOperationLogDao;
import com.tuniu.gt.workorder.service.IWorkOrderOperationLogService;
@Service("wo_service_impl-operation_log")
public class WorkOrderOperationLogServiceImpl extends ServiceBaseImpl<WorkOrderOperationLogDao> implements IWorkOrderOperationLogService
{
	
	@Autowired
	@Qualifier("wo_dao_impl-operation_log")
	public void setDao(WorkOrderOperationLogDao dao) {
		this.dao = dao;
	}
	
}
