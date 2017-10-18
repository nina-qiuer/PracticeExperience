package com.tuniu.qms.qc.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.QualityProblemType;

public interface QualityProblemTypeMapper extends BaseMapper<QualityProblemType, QualityProblemTypeDto> {

	 int getQpTypeCount(Integer id);
	
	 QualityProblemType getQpTypeByFullName( QualityProblemTypeDto dto);
	 
}
