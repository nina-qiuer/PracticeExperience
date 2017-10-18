package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.QualityCostLedgerDto;
import com.tuniu.qms.qs.model.QualityCostLedger;

public interface QualityCostLedgerMapper extends BaseMapper<QualityCostLedger, QualityCostLedgerDto> {

	/**
	 * 批量插入台账
	 * @param list
	 */
	void addBatch(List<QualityCostLedger> list);
	
	void deleteByQcId(Integer qcId);
	
	void deleteByCmpId(Integer cmpId);
	
	List<Integer> getCmpIdFromResp(Map<String, Object> map);
	
	void updateByCmpId(QualityCostLedger ledger);
	
	List<String> getCostAccount();
}
