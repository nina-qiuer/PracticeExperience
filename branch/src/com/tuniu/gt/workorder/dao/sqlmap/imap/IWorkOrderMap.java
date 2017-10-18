package com.tuniu.gt.workorder.dao.sqlmap.imap;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.workorder.entity.WorkOrderObj;
import com.tuniu.gt.workorder.entity.WorkOrderReport;
import com.tuniu.gt.workorder.vo.WorkOrderVo;

@Repository("wo_dao_sqlmap-work_order")
public interface IWorkOrderMap extends IMapBase {
		Integer count(Map<String,Object> paramMap);
		
		List<WorkOrderObj> getWorkOrderList(Map<String,Object> paramMap);

		List<WorkOrderReport> getWorkOrderReportData(Map<String, Object> parameterMap);
}
