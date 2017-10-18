package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.SubstdPurchaseAmtDto;
import com.tuniu.qms.qs.model.SubstdPurchaseAmt;

public interface SubstdPurchaseAmtService extends BaseService<SubstdPurchaseAmt, SubstdPurchaseAmtDto> {
	
	/** 创建不合格采购单快照 */
	void createSubstdPurchaseAmtSnapshot();
	
	List<String> getBusinessUnits(SubstdPurchaseAmtDto dto);
	
	List<String> getPrdDeps(SubstdPurchaseAmtDto dto);
	
	/** 统计图-组织架构维度-超时采购单数 */
	List<Map<String, Object>> statGraphDepByNum(SubstdPurchaseAmtDto dto);
	
	/** 统计图-组织架构维度-超时采购总额 */
	List<Map<String, Object>> statGraphDepByTotPrice(SubstdPurchaseAmtDto dto);
	
	/** 统计图-添加日期维度-超时采购单数 */
	List<Map<String, Object>> statGraphDateByNum(SubstdPurchaseAmtDto dto);
	
	/** 统计图-添加日期维度-超时采购总额 */
	List<Map<String, Object>> statGraphDateByTotPrice(SubstdPurchaseAmtDto dto);
	
}
