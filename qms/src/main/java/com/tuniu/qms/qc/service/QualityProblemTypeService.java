package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.QualityProblemType;

public interface QualityProblemTypeService extends BaseService<QualityProblemType, QualityProblemTypeDto>{
	
 	List<QualityProblemType> getQpTypeDataCache(Integer qcFlag);
	
	
	List<QualityProblemType> getQpTypeFullNameList(Integer qcFlag);
	
	/**
	 * 根据全名查询部门
	 * @param fullName
	 * @return
	 */
	QualityProblemType getQpTypeByFullName(String fullName); 
	/**
	 * 根据全名查询部门
	 * @param fullName
	 * @return
	 */
	QualityProblemType getQpTypeFullName(String fullName); 
	/**
	 * 根据问题类型Id查问题全名
	 * @param qptId
	 * @return
	 */
	String	getQpFullNameById(int qptId, List<QualityProblemType> listType);
	
	QualityProblemType	getQpType(int qptId, List<QualityProblemType> listType);
	
	List<QualityProblemType> getQpTypeByFlag(Integer qcFlag);
	
	List<QualityProblemType> getQueryQpTypeByFlag(Integer qcFlag);
}
