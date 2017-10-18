package com.tuniu.qms.qc.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcAuxiliaryTemplateDto;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;


public interface QcAuxiliaryTemplateMapper  extends BaseMapper<QcAuxiliaryTemplate, QcAuxiliaryTemplateDto> {

	 QcAuxiliaryTemplate getByQcType(QcAuxiliaryTemplateDto dto);
}
