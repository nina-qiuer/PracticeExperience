package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.RespPunishRelation;

public interface RespPunishRelationMapper extends BaseMapper<RespPunishRelation, RespPunishRelationDto> {
	
	/**
	 * 根据责任单信息删除关联关系
	 * @param dto
	 */
	void deleteByResp(RespPunishRelationDto dto);
	
	/**
	 * 根据处罚单信息删除关联关系
	 * @param dto
	 */
	void deleteByPunish(RespPunishRelationDto dto);
	
	/**
	 * 验证质检单中处罚单关联关系，是否都关联
	 * @param qcBillId
	 * @return
	 */
	List<RespPunishRelation> checkPunishRelation(Integer qcBillId);

	RespPunishRelation getRelationByPunish(RespPunishRelationDto dto);

}
