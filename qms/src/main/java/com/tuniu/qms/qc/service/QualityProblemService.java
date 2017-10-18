package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.model.QualityProblem;

public interface QualityProblemService extends BaseService<QualityProblem, QualityProblemDto>{

	
	 void deleteQp(Integer qpId);
	
	 List<QualityProblem> listQp(QualityProblemDto dto);
	
	 void deleteInnerAndOuter(Integer qcId);
	
	 List<QualityProblem> listQpDetail(Integer qcId,Integer groupFlag);
	 
	 Map<String, Object> getImpPersonByQcId(Integer qpId);
	
	/**
	 * 复制质量问题单详情
	 * @param qcDetailCopyDto 旧质检单id
	 */
	void copyQualityDetails(QcDetailCopyDto qcDetailCopyDto);
}
