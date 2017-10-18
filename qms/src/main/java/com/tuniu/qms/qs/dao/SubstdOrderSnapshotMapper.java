package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.bi.dto.OrderExtDto;
import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.SubstdOrderSnapshotDto;
import com.tuniu.qms.qs.model.SubstdOrderSnapshot;

public interface SubstdOrderSnapshotMapper extends BaseMapper<SubstdOrderSnapshot, SubstdOrderSnapshotDto> {
	
	void deleteAllOrdExts();
	
	void addOrdExts(List<OrderExtDto> ordExtList);
	
	List<SubstdOrderSnapshot> listFromOrderExt(Map<String, Object> params);
	
	void addBatch(List<SubstdOrderSnapshot> ordList);
	
	void deleteByStatisticDate(java.sql.Date statisticDate);
	
	void addDepOrdNumSnapshot(java.sql.Date statisticDate);
	
	void deleteDepOrdNumSnapshot(java.sql.Date statisticDate);
	
	List<String> getBusinessUnits(SubstdOrderSnapshotDto dto);
	
	List<String> getPrdDeps(SubstdOrderSnapshotDto dto);
	
	/** 统计部门出团通知超时率-组织架构维度 */
	List<Map<String, Object>> statDepNoticeTimeOutRate(SubstdOrderSnapshotDto dto);
	
	/** 统计部门出团通知超时率-统计日期维度 */
	List<Map<String, Object>> statDateNoticeTimeOutRate(SubstdOrderSnapshotDto dto);
	
}
