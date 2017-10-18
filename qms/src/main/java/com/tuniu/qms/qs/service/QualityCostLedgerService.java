package com.tuniu.qms.qs.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.QualityCostLedgerDto;
import com.tuniu.qms.qs.model.QualityCostLedger;

public interface QualityCostLedgerService extends BaseService<QualityCostLedger, QualityCostLedgerDto> {
	
	void addBatch(List<QualityCostLedger> list);
	
	void deleteByQcId(Integer qcId);
	
	void deleteByCmpId(Integer cmpId);
	
	List<Integer> getCmpIdFromResp(Map<String, Object> map);
	
	void updateByCmpId(QualityCostLedger ledger);
	
	List<String> getCostAccount();
}
