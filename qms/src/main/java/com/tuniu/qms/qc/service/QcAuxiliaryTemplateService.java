package com.tuniu.qms.qc.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcAuxiliaryTemplateDto;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;

public interface QcAuxiliaryTemplateService  extends BaseService<QcAuxiliaryTemplate, QcAuxiliaryTemplateDto> {

  
	 QcAuxiliaryTemplate getByQcType(QcAuxiliaryTemplateDto dto);
}
