package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.SubstdProductSnapshotDto;
import com.tuniu.qms.qs.model.SubstdProductSnapshot;

public interface SubstdProductSnapshotService extends BaseService<SubstdProductSnapshot, SubstdProductSnapshotDto> {
	
	/** 从BI库同步产品数据，仅保留每日数据 */
	void syncProductExt();
	
	/** 创建不合格产品快照 */
	void createSubstdProductSnapshot();
	
	/** 创建部门产品数量统计快照 */
	void createDepPrdNumSnapshot();
	
	List<String> getBusinessUnits(SubstdProductSnapshotDto dto);
	
	List<String> getPrdDeps(SubstdProductSnapshotDto dto);
	
	/** 统计销售期长不合格问题发生率-组织架构维度 */
	List<Map<String, Object>> statDepSplRate(SubstdProductSnapshotDto dto);
	
	/** 统计销售期长不合格问题发生率-时间维度 */
	List<Map<String, Object>> statDateSplRate(SubstdProductSnapshotDto dto);
	
	/** 统计团期丰富度不合格问题发生率-组织架构维度 */
	List<Map<String, Object>> statDepGrRate(SubstdProductSnapshotDto dto);
	
	/** 统计团期丰富度不合格问题发生率-时间维度 */
	List<Map<String, Object>> statDateGrRate(SubstdProductSnapshotDto dto);
	
	/** 统计非独立成团牛人专线问题发生率-组织架构维度 */
	List<Map<String, Object>> statDepUnAgRate(SubstdProductSnapshotDto dto);
	
	/** 统计非独立成团牛人专线问题发生率-时间维度 */
	List<Map<String, Object>> statDateUnAgRate(SubstdProductSnapshotDto dto);
	
}
