package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.model.OuterRespBill;

public interface OuterRespBillService  extends BaseService<OuterRespBill, OuterRespBillDto>{

	 int getOuterRespIsExist(OuterRespBill outerBill);
	
	 void addRespAndPunish(OuterRespBill outerBill); 
	 
	 List<OuterRespBill>  listOuterResp(OuterRespBillDto dto);
	 
	 List<OuterRespBill> listResp();
	 
	/**
	 * 删除外部责任单
	 * @param respId
	 */
	void deleteRespBill(Integer outerId);
	
	/**
	 * 复制外部责任单
	 * @param qcDetailCopyDto
	 * @return
	 */
	Integer copyOuterRespBill(QcDetailCopyDto qcDetailCopyDto);

	void updateOuterResp(OuterRespBill outerBill);
	
	/**
	 * 根据投诉单Id获得外部责任单信息
	 * @param dataId
	 * @return
	 */
	OuterRespBill getRespInfoByComplaintId(Integer dataId);
}
