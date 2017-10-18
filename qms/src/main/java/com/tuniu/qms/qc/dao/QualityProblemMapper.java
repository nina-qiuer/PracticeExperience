package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.QualityProblem;

public interface QualityProblemMapper extends BaseMapper<QualityProblem, QualityProblemDto> {
	
	 List<QualityProblem> getQpByQcId(Integer qcId);

	 Map<String, Object> getImpPersonByQcId(Integer qpId);
	 
	 void  addCopyQpBill(QualityProblem quality);
	 /**
	  * 根据质量问题单id，获得责任单信息
	  * @param qpId
	  * @return
	  */
	 List<RespPunishRelationDto> getRespInfoByQpId(Integer qpId);
}
