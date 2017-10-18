package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.SubstdContractAppendAmtDto;
import com.tuniu.qms.qs.model.SubstdContractAppendAmt;

public interface SubstdContractAppendAmtMapper extends BaseMapper<SubstdContractAppendAmt, SubstdContractAppendAmtDto> {
	
	void addBatch(List<SubstdContractAppendAmt> appendList);
	
	void deleteByAddDate(java.sql.Date addDate);
	
	List<String> getBusinessUnits(SubstdContractAppendAmtDto dto);
	
	List<String> getPrdDeps(SubstdContractAppendAmtDto dto);
	
	/** 统计图-组织架构维度-跨月添加合同增补单数 */
	List<Map<String, Object>> statGraphDepByNum(SubstdContractAppendAmtDto dto);
	
	/** 统计图-组织架构维度-跨月添加合同增补总额 */
	List<Map<String, Object>> statGraphDepByTotPrice(SubstdContractAppendAmtDto dto);
	
	/** 统计图-添加日期维度-跨月添加合同增补单数 */
	List<Map<String, Object>> statGraphDateByNum(SubstdContractAppendAmtDto dto);
	
	/** 统计图-添加日期维度-跨月添加合同增补总额 */
	List<Map<String, Object>> statGraphDateByTotPrice(SubstdContractAppendAmtDto dto);
	
}
