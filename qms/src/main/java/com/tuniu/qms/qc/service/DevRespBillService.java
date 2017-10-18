package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.DevRespBillDto;
import com.tuniu.qms.qc.model.DevRespBill;

public interface DevRespBillService  extends BaseService<DevRespBill, DevRespBillDto> {

	 int  getDevRespIsExist(DevRespBill respBill);
	
	 List<DevRespBill>  listResp(DevRespBillDto dto);
  	
 }
