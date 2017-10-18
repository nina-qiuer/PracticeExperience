package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;

public class QcAuxiliaryTemplateDto extends BaseDto<QcAuxiliaryTemplate> {

	
	private Integer qcType;

	public Integer getQcType() {
		return qcType;
	}

	public void setQcType(Integer qcType) {
		this.qcType = qcType;
	}
	
	
}
