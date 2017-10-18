package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.SubstdRefundAmtDto;
import com.tuniu.qms.qs.model.SubstdRefundAmt;

public interface SubstdRefundAmtMapper extends BaseMapper<SubstdRefundAmt, SubstdRefundAmtDto> {

	void addBatch(List<SubstdRefundAmt> refundList);
	
	void deleteByAddDate(java.sql.Date addDate);
	
	List<String> getBusinessUnits(SubstdRefundAmtDto dto);
	
	List<String> getPrdDeps(SubstdRefundAmtDto dto);
	
	/** 统计图-组织架构维度-导致超额退款-退款单数 */
	List<Map<String, Object>> statGraphDepByNum(SubstdRefundAmtDto dto);
	
	/** 统计图-组织架构维度-导致超额退款-退款总额 */
	List<Map<String, Object>> statGraphDepByTotAmount(SubstdRefundAmtDto dto);
	
	/** 统计图-添加日期维度-导致超额退款-退款单数 */
	List<Map<String, Object>> statGraphDateByNum(SubstdRefundAmtDto dto);
	
	/** 统计图-添加日期维度-导致超额退款-退款总额 */
	List<Map<String, Object>> statGraphDateByTotAmount(SubstdRefundAmtDto dto);
	
}
