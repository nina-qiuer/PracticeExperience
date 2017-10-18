package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.QcType;

public interface QcTypeService extends BaseService<QcType, QcTypeDto>{
	
	
	List<QcType> getQcTypeDataCache();
	
	List<QcType> getTypeFullNameList();
	
	QcType getTypeByFullName(String fullName);
	
    String getQtFullNameById(int qtId,List<QcType> list) ;
 
    QcType getTypeName(String name);
    
	 int getQcTypeCount(Integer id);
	 
	 List<QcType> getQcTypeByName(String name);
	 
	 QcType getQtByFullName(QcTypeDto dto);
	 /**
	  * 根据质检类型全名获得质检类型
	  * @param qcName
	  * @return
	  */
	QcType getQcTypeByFullName(String qcName);
}
