package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.SubstdOrderAmtDto;
import com.tuniu.qms.qs.model.SubstdOrderAmt;

public interface SubstdOrderAmtMapper extends BaseMapper<SubstdOrderAmt, SubstdOrderAmtDto> {
	
	void addBatch(List<SubstdOrderAmt> ordList);
	
	void deleteByStatisticDate(java.sql.Date statisticDate);

	List<String> getBusinessUnits(SubstdOrderAmtDto dto);
	
	List<String> getPrdDeps(SubstdOrderAmtDto dto);
	
	/** 统计图-组织架构维度-合同价异常单数 */
	List<Map<String, Object>> statGraphDepByNum(SubstdOrderAmtDto dto);
	
	/** 统计图-组织架构维度-合同价异常总额 */
	List<Map<String, Object>> statGraphDepByTotAmount(SubstdOrderAmtDto dto);
	
	/** 统计图-添加日期维度-合同价异常单数 */
	List<Map<String, Object>> statGraphDateByNum(SubstdOrderAmtDto dto);
	
	/** 统计图-添加日期维度-合同价异常总额 */
	List<Map<String, Object>> statGraphDateByTotAmount(SubstdOrderAmtDto dto);
	/**
	 * 获取前一天所有的数据 按照未付金额倒序排列
	 * @param dto
	 * @return
	 */
	List<SubstdOrderAmt> listAll(SubstdOrderAmtDto dto);
}
