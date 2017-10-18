package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.DevFaultBillDto;
import com.tuniu.qms.qc.model.DevFaultBill;

public interface DevFaultBillService  extends BaseService<DevFaultBill, DevFaultBillDto> {

	 List<DevFaultBill> listFault(DevFaultBillDto dto);
	
	void deleteFault(Integer devId);
	
	List<DevFaultBill>  listDevFaultDetail(Integer qcId);
}
