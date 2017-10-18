package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.SubstdOrderSnapshotDto;
import com.tuniu.qms.qs.model.SubstdOrderSnapshot;

public interface SubstdOrderSnapshotService extends BaseService<SubstdOrderSnapshot, SubstdOrderSnapshotDto>{
	
	/** 从BI库同步订单数据，仅保留每日数据 */
	void syncOrderExt();
	
	/** 创建不合格订单快照 */
	void createSubstdOrderSnapshot();
	
	/** 创建部门订单数量统计快照 */
	void createDepOrdNumSnapshot();
	
	List<String> getBusinessUnits(SubstdOrderSnapshotDto dto);
	
	List<String> getPrdDeps(SubstdOrderSnapshotDto dto);
	
	/** 统计部门出团通知超时率-组织架构维度 */
	List<Map<String, Object>> statDepNoticeTimeOutRate(SubstdOrderSnapshotDto dto);
	
	/** 统计部门出团通知超时率-统计日期维度 */
	List<Map<String, Object>> statDateNoticeTimeOutRate(SubstdOrderSnapshotDto dto);
	
}
