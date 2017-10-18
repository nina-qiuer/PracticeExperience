package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.DevRespBill;
/**
 * 
 * @author chenhaitao
 *
 */
public class DevRespBillDto extends BaseDto<DevRespBill> {

	/** 故障单单号*/
	private Integer devId;

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}
		
	
}
