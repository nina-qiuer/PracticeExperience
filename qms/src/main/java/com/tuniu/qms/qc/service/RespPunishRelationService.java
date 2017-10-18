package com.tuniu.qms.qc.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.RespPunishRelation;

public interface RespPunishRelationService extends BaseService<RespPunishRelation, RespPunishRelationDto> {
	
	/**
	 * 根据处罚单信息删除关联关系
	 * @param dto
	 */
	void deleteByPunish(RespPunishRelationDto dto);
	
	/**
	 * 添加责任单处罚单关联关系
	 * @param qcDetailCopyDto
	 */
	void addRespPunishRelation(QcDetailCopyDto qcDetailCopyDto);
	
	/**
	 * 验证质检单中处罚单关联关系，是否都关联
	 * @param qcBillId
	 * @return
	 */
	boolean checkPunishRelation(Integer qcBillId);
	
	/**
	 * 根据处罚单信息获得关联关系
	 * @param id
	 * @param innerRespPunFalg
	 * @return
	 */
	RespPunishRelation getRelationByPunish(Integer id, int innerRespPunFalg);
}
