package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.QcType;

public interface QcTypeMapper extends BaseMapper<QcType, QcTypeDto> {

   QcType getQtByFullName(QcTypeDto dto); 
   
   QcType getTypeName(String name);
   
   int getQcTypeCount(Integer id);
   
   List<QcType> getQcTypeByName(String name);
}
