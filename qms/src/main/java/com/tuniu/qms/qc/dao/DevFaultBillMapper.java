package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.DevFaultBillDto;
import com.tuniu.qms.qc.model.DevFaultBill;

public interface DevFaultBillMapper  extends BaseMapper<DevFaultBill, DevFaultBillDto> {

	List<DevFaultBill> getFaultByQcId(Integer qcId);
}
