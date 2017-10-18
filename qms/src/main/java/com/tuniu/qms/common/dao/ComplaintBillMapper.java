package com.tuniu.qms.common.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.ComplaintBillDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.DataSyncModel;
import com.tuniu.qms.qc.model.QcSameGroupBill;

public interface ComplaintBillMapper extends BaseMapper<ComplaintBill, ComplaintBillDto> {
	
	void deleteBatch(ComplaintBillDto dto);
	
	void addBatch(List<ComplaintBill> list);
	
	void addCmpSyncTasks();
	
	void addCmpSyncTask(Integer cmpId);
	
	void increFailTimes(DataSyncModel cmpData);
	
	List<DataSyncModel> listCmpSyncTask();
	
	void deleteCmpSyncTask(Integer cmpId);
	
	/**
     * 功能描述: 根据一串订单号查询哪些已经有投诉
     */
	List<QcSameGroupBill> queryHasCtOrders(Map<String, Object> paramMap);
	
}
