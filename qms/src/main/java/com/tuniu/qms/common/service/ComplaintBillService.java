package com.tuniu.qms.common.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dto.ComplaintBillDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.DataSyncModel;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.model.QcSameGroupBill;

public interface ComplaintBillService extends BaseService<ComplaintBill, ComplaintBillDto> {
	
	void addCmpSyncTasks();
	
	void deleteCmpSyncTask(Integer cmpId);
	
	void increFailTimes(DataSyncModel cmpData);
	
	List<DataSyncModel> listCmpSyncTask();
	
	/**
     * 功能描述: 根据一串订单号查询哪些已经有投诉
     */
	List<QcSameGroupBill> queryHasCtOrders(Map<String, Object> paramMap);
	
	/**
	 * 同步投诉单信息
	 */
	void syncComplaint();

	void addCmpSyncTask(Integer cmpId);
}
