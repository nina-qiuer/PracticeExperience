package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.OuterPunishBillDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.model.OuterPunishBill;

public interface OuterPunishBillMapper  extends BaseMapper<OuterPunishBill, OuterPunishBillDto> {
	
	List<OuterPunishBill> listOuterPunishBill(Integer qcId);
	
	/**
	 * 根据质检单号删除外部处罚单
	 * @param qpId
	 */
	 void deleteOuterPunishBill(Integer qcId);
	/**
	 * 查询外部处罚人是否重复
	 * @param innerBill
	 * @return
	 */
	 int getOuterPunishIsExist(OuterPunishBill outerBill);
	
	/**
	 * 删除标记位为1的数据
	 * @param dto
	 */
	 void  deleteUnUsePunish(OuterPunishBillDto dto);
	
	/**
	 * 插入处罚单获取主键ID
	 * @param outerBill
	 */
	 void addPunish(OuterPunishBill outerBill);
	
	 void updatePunishBill(OuterPunishBasis basis);
	 
	List<OuterPunishBill>  copyList(OuterPunishBillDto dto);
	    
	void copyOuterPunishBill(OuterPunishBill outerBill);
	
	List<Integer> getByQcId(Map<String, Object> map);
}
