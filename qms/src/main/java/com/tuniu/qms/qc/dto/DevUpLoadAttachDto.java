package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.DevUpLoadAttach;

/**
 * 上传
 * @author chenhaitao
 *
 */
public class DevUpLoadAttachDto extends  BaseDto<DevUpLoadAttach>{

	/** 故障单单号*/
   private Integer devId;

	public Integer getDevId() {
		return devId;
	}
	
	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	
	   

}
