package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcAuxiliaryBillDto;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;


public interface QcAuxiliaryBillMapper  extends BaseMapper<QcAuxiliaryBill, QcAuxiliaryBillDto> {

	List<QcAuxiliaryBill> getByTemplate(QcAuxiliaryBillDto dto);
	
	/**
	 *运营质检辅助表导出
	 * @param dto
	 * @return
	 */
	List<QcAuxiliaryBill> exportList(QcAuxiliaryBillDto dto);
	
	Integer exportCount(QcAuxiliaryBillDto dto);
}
