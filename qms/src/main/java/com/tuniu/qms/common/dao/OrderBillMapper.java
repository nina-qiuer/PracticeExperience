package com.tuniu.qms.common.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.OrderBillDto;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.OrderBillAgency;
import com.tuniu.qms.common.model.OrderBillOperator;
import com.tuniu.qms.qc.model.QcBill;

public interface OrderBillMapper extends BaseMapper<OrderBill, OrderBillDto> {
	
	void addOrdSyncTask(Integer ordId);
	
	List<Integer> listOrdSyncTask();
	
	void deleteOrdSyncTask(Integer ordId);
	
	void addOperators(List<OrderBillOperator> ops);
	
	void deleteOperatorByOrdId(Integer ordId);
	
	void addAgencies(List<OrderBillAgency> agencies);
	
	void deleteAgencyByOrdId(Integer ordId);
	
	void increFailTimes(Integer ordId);
	
	void addAttachOrdSyncTask(List<QcBill> list);
}
