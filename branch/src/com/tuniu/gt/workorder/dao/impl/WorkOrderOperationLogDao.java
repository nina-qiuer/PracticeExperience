package com.tuniu.gt.workorder.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.workorder.dao.sqlmap.imap.IWorkOrderOperationLogMap;
import com.tuniu.gt.workorder.entity.WorkOrderOperationLog;

@Repository("wo_dao_impl-operation_log")
public class WorkOrderOperationLogDao extends DaoBase<WorkOrderOperationLog, IWorkOrderOperationLogMap>  implements IWorkOrderOperationLogMap 
{
	public WorkOrderOperationLogDao() {  
		tableName = Constant.appTblPreMap.get("workorder") + "operation_log";
	}
	
	@Autowired
	@Qualifier("wo_dao_sqlmap-operation_log")
	public void setMapper(IWorkOrderOperationLogMap mapper) {
		this.mapper = mapper;
	}
    
}
