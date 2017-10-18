package com.tuniu.qms.qc.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.DevRespBillDto;
import com.tuniu.qms.qc.model.DevRespBill;

public interface DevRespBillMapper extends BaseMapper<DevRespBill, DevRespBillDto> {

	/**
	 * 查询责任人是否重复
	 * @param innerBill
	 * @return
	 */
	 int  getDevRespIsExist(DevRespBill respBill);
	/**
	 * 根据故障单ID删除责任单
	 * @param qpId
	 */
	 void deleteDevRespBill(Integer devId);
}
