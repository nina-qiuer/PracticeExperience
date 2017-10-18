package com.tuniu.qms.qc.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.model.InnerRespBill;

public interface InnerRespBillMapper extends BaseMapper<InnerRespBill, InnerRespBillDto> {

	/**
	 * 查询内部责任人是否重复
	 * @param innerBill
	 * @return
	 */
	 int  getInnerRespIsExist(InnerRespBill innerBill);
	/**
	 * 根据质量问题ID删除内部责任单
	 * @param qpId
	 */
	 void deleteInnerRespBill(Integer qpId);
	 
	 
	 Double getRespRate(Integer qcId);
	 
	 /**
	  * 复制内部责任单
	  * @param innerRespBill
	  */
	 void copyInnerRespBill( InnerRespBill innerRespBill);
}
