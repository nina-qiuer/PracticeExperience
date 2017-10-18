package com.tuniu.qms.qc.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;

public interface OperateQcBillService  extends BaseService<QcBill, QcBillDto> {

   int getQcBillIsExist(Integer qcId);
	/**
	 * 撤销质检单
	 * @param qcBill
	 */
	void deleteOperateBill(QcBill qcBill);
	
    void deleteInner(Integer qcId);
}
