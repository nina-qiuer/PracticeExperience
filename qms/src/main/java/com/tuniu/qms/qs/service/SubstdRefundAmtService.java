package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.SubstdRefundAmtDto;
import com.tuniu.qms.qs.model.SubstdRefundAmt;

public interface SubstdRefundAmtService extends BaseService<SubstdRefundAmt, SubstdRefundAmtDto> {
	
	/** 创建不合格退款单快照 */
	void createSubstdRefundAmtSnapshot();
	
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
