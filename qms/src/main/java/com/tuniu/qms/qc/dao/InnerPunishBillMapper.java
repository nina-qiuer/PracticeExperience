package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;

public interface InnerPunishBillMapper  extends BaseMapper<InnerPunishBill, InnerPunishBillDto> {
	
	List<InnerPunishBill> listInnerPunishBill(Integer qcId);

	/**
	 * 根据质检单删除内部处罚单
	 */
	void deleteInnerPunishBill(Integer qcId);

	/**
	 * 查询内部处罚人是否重复
	 */
	int getInnerPunishIsExist(InnerPunishBill innerBill);

	/**
	 * 删除标记位为1的数据
	 */
	void deleteUnUsePunish(InnerPunishBillDto dto);

	/**
	 * 插入处罚单获取主键ID
	 */
	void addPunish(InnerPunishBill innerBill);

	void updatePunishBill(InnerPunishBasis basis);
	
	List<InnerPunishBill>  copyList(InnerPunishBillDto dto);
    
	void copyInnerPunishBill(InnerPunishBill innerBill);
	
	List<Integer> getByQcId(Map<String, Object> map);
	
	List<InnerPunishBill> exportList(InnerPunishBillDto dto);
	/**
	 * 根据质检id获得处罚单列表
	 * @param qcId
	 * @return
	 */
	List<InnerPunishBill> listInnerPunishByQcId(Integer qcId);

	Integer getTotalScores(InnerPunishBillDto dto);
}
