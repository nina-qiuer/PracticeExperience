package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.model.QcPointAttach;

public interface QcPointAttachMapper extends BaseMapper<QcPointAttach, QcPointAttachDto>{

	/**
	 * 根据质检单ID删除附件表
	 * @param qpId
	 */
	 void deleteAttach(Integer qcId);
	 /**
	  * 批量插入附件
	  * @param list
	  */
	 void  addBatch(List<QcPointAttach> list);
	/**
	 * 删除附件
	 * @param dto
	 */
	 void deleteByDto(QcPointAttachDto dto);
	 /**
	  * 查询改进报告的附件
	  * @param attachDto
	  * @return
	  */
	List<Map<String, Object>> listImproveAttach(QcPointAttachDto attachDto);
}
