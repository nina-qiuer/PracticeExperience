package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.model.OuterRespBill;

public interface OuterRespBillMapper extends BaseMapper<OuterRespBill, OuterRespBillDto> {
	
	
	 int getOuterRespIsExist(OuterRespBill outerBill);
	/**
	 * 根据质量问题Id删除外部责任单
	 * @param qpId
	 */
	 void deleteOutRespBill(Integer qpId);
	 
	 List<OuterRespBill>  listResp();
	 
	 void copyOuterRespBill(OuterRespBill outerRespBill);
	 
	 OuterRespBill getRespInfoByComplaintId(Integer dataId);
}
