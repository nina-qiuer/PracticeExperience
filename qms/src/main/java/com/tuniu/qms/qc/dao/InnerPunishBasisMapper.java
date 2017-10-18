package com.tuniu.qms.qc.dao;

import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.InnerPunishBasisDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;

public interface InnerPunishBasisMapper  extends BaseMapper<InnerPunishBasis, InnerPunishBasisDto> {
	

	 void deletePunish(InnerPunishBasis basis);
	/**
	 * 更新处罚依据执行状态
	 * @param basis
	 */
	 void updatePunish(InnerPunishBasis basis);
	/**
	 * 更新内部处罚单金额和记分
	 * @param basis
	 */
	 void updatePunishBill(InnerPunishBasis basis);
	
	/**
	 * 根据质检单删除内部处罚依据
	 * @param qcId
	 */
	 void deletePunishByIpb(Map<String, Object> map);
	
	/**
	 * 查询处罚单有无关联处罚依据
	 * @return
	 */
	 int getPunishBasisIsExist(Map<String, Object> map);
	 
	 void addCopyInnerBasis(Map<String, Object> map);
}
