package com.tuniu.gt.workorder.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.workorder.dao.impl.WorkOrderDao;
import com.tuniu.gt.workorder.entity.WorkOrderObj;
import com.tuniu.gt.workorder.entity.WorkOrderReport;
import com.tuniu.gt.workorder.service.IWorkOrderService;
import com.tuniu.gt.workorder.vo.WorkOrderVo;
@Service("wo_service_impl-work_order")
public class WorkOrderServiceImpl extends ServiceBaseImpl<WorkOrderDao> implements IWorkOrderService
{
	private static Logger logger = Logger.getLogger(WorkOrderServiceImpl.class);
	
	@Autowired
	@Qualifier("wo_dao_impl-work_order")
	public void setDao(WorkOrderDao dao) {
		this.dao = dao;
	}

	@Override
	public Integer count(Map<String, Object> paramMap) {
		return dao.count(paramMap);
	}
	
	@Override
	public List<WorkOrderObj> getWorkOrderList(Map<String,Object> paramMap){
		return dao.getWorkOrderList(paramMap);
	}

	@Override
	public List<WorkOrderReport> getWorkOrderReportData(Map<String, Object> parameterMap) {
		return dao.getWorkOrderReportData(parameterMap);
	}
}
