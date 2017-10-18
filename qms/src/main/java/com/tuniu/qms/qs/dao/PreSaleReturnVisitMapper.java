package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.PreSaleReturnVisitDto;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;

public interface PreSaleReturnVisitMapper extends BaseMapper<PreSaleReturnVisit, PreSaleReturnVisitDto> {

	void addBatch(List<PreSaleReturnVisit> list);
	
	List<PreSaleReturnVisit>  needRvList(Map<String, Object> map);
	
	/**
	 * 获取一小时之内的有效数据
	 * @param map
	 * @return
	 */
	PreSaleReturnVisit getValidVistByParam(Map<String, Object> map);
}
