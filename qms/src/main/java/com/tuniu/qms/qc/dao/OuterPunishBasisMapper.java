package com.tuniu.qms.qc.dao;

import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.OuterPunishBasisDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
public interface OuterPunishBasisMapper  extends BaseMapper<OuterPunishBasis, OuterPunishBasisDto> {
	

	 void deletePunish(OuterPunishBasis basis);
	/**
	 * 更新处罚依据执行状态
	 * @param basis
	 */
	 void updatePunish(OuterPunishBasis basis);
	/**
	 * 更新外部处罚单金额和记分
	 * @param basis
	 */
	 void updatePunishBill(OuterPunishBasis basis);
	
	/**
	 * 根据质检单删除外处罚依据
	 * @param qcId
	 */
	 void deletePunishByOpb(Map<String, Object> map);
	
	
	/**
	 * 查询处罚单有无关联处罚依据
	 * @return
	 */
	 int getPunishBasisIsExist(Map<String, Object> map);
	 
	 void addCopyOuterBasis(Map<String, Object> map);
}
