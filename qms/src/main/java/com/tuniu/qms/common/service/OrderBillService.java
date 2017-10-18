package com.tuniu.qms.common.service;

import java.util.List;

import com.tuniu.qms.common.dto.OrderBillDto;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.service.base.BaseService;

public interface OrderBillService extends BaseService<OrderBill, OrderBillDto> {
	
	void increFailTimes(Integer ordId);
	
	List<Integer> listOrdSyncTask();

	void addOrdSyncTask(Integer ordId);

	void syncOrder();
	
}
