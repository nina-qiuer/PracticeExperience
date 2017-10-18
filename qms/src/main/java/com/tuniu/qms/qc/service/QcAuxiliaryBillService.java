package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcAuxiliaryBillDto;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;

public interface QcAuxiliaryBillService  extends BaseService<QcAuxiliaryBill, QcAuxiliaryBillDto> {

  
	List<QcAuxiliaryBill> getByTemplate(QcAuxiliaryBillDto dto);
	
	void deleteByIds(QcAuxiliaryBillDto dto);
	
	/**
	 *运营质检辅助表导出
	 * @param dto
	 * @return
	 */
	List<QcAuxiliaryBill> exportList(QcAuxiliaryBillDto dto);
	
	Integer exportCount(QcAuxiliaryBillDto dto);
}
