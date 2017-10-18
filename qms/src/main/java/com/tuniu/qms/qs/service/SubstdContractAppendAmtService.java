package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.SubstdContractAppendAmtDto;
import com.tuniu.qms.qs.model.SubstdContractAppendAmt;

public interface SubstdContractAppendAmtService extends BaseService<SubstdContractAppendAmt, SubstdContractAppendAmtDto> {
	
	/** 创建不合格合同增补单快照 */
	void createSubstdContractAppendAmtSnapshot();
	
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
