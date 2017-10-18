package com.tuniu.gt.workorder.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.workorder.entity.WorkOrderObj;
import com.tuniu.gt.workorder.entity.WorkOrderReport;
import com.tuniu.gt.workorder.vo.WorkOrderVo;

public interface IWorkOrderService extends IServiceBase
{
	Integer count(Map<String,Object> paramMap);
	
	List<WorkOrderObj> getWorkOrderList(Map<String,Object> paramMap);
	
	/**
	 * 根据查询条件，查询项目组数量
	 * @param parameterMap
	 * @return
	 */
	List<WorkOrderReport> getWorkOrderReportData(Map<String, Object> parameterMap);
}
