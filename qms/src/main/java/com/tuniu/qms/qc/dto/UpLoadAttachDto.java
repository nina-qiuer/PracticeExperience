package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.UpLoadAttach;

/**
 * 上传
 * @author chenhaitao
 *
 */
public class UpLoadAttachDto extends  BaseDto<UpLoadAttach>{


   private Integer qpId;

	public Integer getQpId() {
		return qpId;
	}
	
	public void setQpId(Integer qpId) {
		this.qpId = qpId;
	}
	   

}
