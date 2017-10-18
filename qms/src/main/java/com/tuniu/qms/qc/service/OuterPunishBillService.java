package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.OuterPunishBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.model.OuterPunishBill;

public interface OuterPunishBillService  extends BaseService<OuterPunishBill, OuterPunishBillDto> {

	List<OuterPunishBill> listOuterPunish(Integer qcId);
	
	int getOuterPunishIsExist(OuterPunishBill outerBill);
	
	void deletePunish(Integer outerId);
	
	void deleteUnUsePunish(OuterPunishBillDto dto);
	
	void addPunish(OuterPunishBill outerBill);
	
	void updatePunishBill(OuterPunishBasis basis);
	
	/**
	 * 添加复制的处罚单
	 * @param qcDetailCopyDto
	 * @return
	 */
	Integer copyOuterPunishBill(QcDetailCopyDto qcDetailCopyDto);

	boolean getPunishIsExistByqcId(Integer id);

}
