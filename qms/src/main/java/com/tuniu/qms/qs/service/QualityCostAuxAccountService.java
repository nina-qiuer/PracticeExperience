package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.QualityCostAuxAccountDto;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.QualityCostLedger;

public interface QualityCostAuxAccountService extends BaseService<QualityCostAuxAccount, QualityCostAuxAccountDto> {
	
	void addBatch(List<QualityCostLedger> list);
	
	void deleteByQcId(Integer qcId);
	
	void deleteByCmpId(Integer cmpId);
	
	void updateByCmpId(QualityCostAuxAccount auxAccount);
	
	List<QualityCostAuxAccount>  listAllDep();
	
	List<QualityCostAuxAccount>  listUseDep();
	
	void updateDep(QualityCostAuxAccount auxAccount);
	
	List<QualityCostAuxAccount> listPrdLineDepInfos();
	
	void updateDepByPrdLineId(List<PrdLineDepInfoDto> unSameDepInfos);
	
}
