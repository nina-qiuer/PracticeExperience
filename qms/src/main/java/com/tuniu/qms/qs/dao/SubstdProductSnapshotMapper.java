package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.bi.dto.ProductExtDto;
import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.SubstdProductSnapshotDto;
import com.tuniu.qms.qs.model.SubstdProductSnapshot;

public interface SubstdProductSnapshotMapper extends BaseMapper<SubstdProductSnapshot, SubstdProductSnapshotDto> {
	
	void deleteAllPrdExts();
	
	void addPrdExts(List<ProductExtDto> prdExtList);
	
	List<SubstdProductSnapshot> listFromProductExt(Map<String, Object> params);
	
	void addBatch(List<SubstdProductSnapshot> prdList);
	
	void deleteByStatisticDate(java.sql.Date statisticDate);
	
	void addDepPrdNumSnapshot(java.sql.Date statisticDate);
	
	void deleteDepPrdNumSnapshot(java.sql.Date statisticDate);
	
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
