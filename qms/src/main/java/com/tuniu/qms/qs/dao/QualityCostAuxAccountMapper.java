package com.tuniu.qms.qs.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.qs.dto.QualityCostAuxAccountDto;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.QualityCostLedger;

public interface QualityCostAuxAccountMapper extends BaseMapper<QualityCostAuxAccount, QualityCostAuxAccountDto> {
	
	void addBatch(List<QualityCostLedger> list);
	
	void deleteByQcId(Integer qcId);
	
	void deleteByCmpId(Integer cmpId);
	
	void updateByCmpId(QualityCostAuxAccount auxAccount);
	
	List<QualityCostAuxAccount>  listAllDep();
	
	List<QualityCostAuxAccount>  listUseDep();
	
	List<QualityCostAuxAccount>  listByDep(Map<String, Object> map);
	
	void updateDep(QualityCostAuxAccount auxAccount);
	
	List<QualityCostAuxAccount> listPrdLineDepInfos();
	
	void updateDepByPrdLineId(PrdLineDepInfoDto depInfo);
	
}
