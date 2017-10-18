package com.tuniu.gt.workorder.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.workorder.dao.sqlmap.imap.IWorkOrderMap;
import com.tuniu.gt.workorder.entity.WorkOrder;
import com.tuniu.gt.workorder.entity.WorkOrderObj;
import com.tuniu.gt.workorder.entity.WorkOrderReport;
import com.tuniu.gt.workorder.vo.WorkOrderVo;

@Repository("wo_dao_impl-work_order")
public class WorkOrderDao extends DaoBase<WorkOrder, IWorkOrderMap>  implements IWorkOrderMap 
{
	public WorkOrderDao() {  
		tableName = Constant.appTblPreMap.get("workorder") + "work_order";
	}
	
	@Autowired
	@Qualifier("wo_dao_sqlmap-work_order")
	public void setMapper(IWorkOrderMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public Integer count(Map<String, Object> paramMap) {
		return mapper.count(paramMap);
	}
	
	@Override
	public List<WorkOrderObj> getWorkOrderList(Map<String, Object> paramMap) {
		return mapper.getWorkOrderList(paramMap);
	}

	public List<WorkOrderReport> getWorkOrderReportData(Map<String, Object> parameterMap) {
		return mapper.getWorkOrderReportData(parameterMap);
	}

}
