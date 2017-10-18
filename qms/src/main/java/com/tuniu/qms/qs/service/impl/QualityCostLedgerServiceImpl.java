package com.tuniu.qms.qs.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qs.dao.QualityCostLedgerMapper;
import com.tuniu.qms.qs.dto.QualityCostLedgerDto;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.service.QualityCostLedgerService;

@Service
public class QualityCostLedgerServiceImpl implements QualityCostLedgerService {

	@Autowired
	private QualityCostLedgerMapper mapper;
	

	@Override
	public void add(QualityCostLedger obj) {
		mapper.add(obj);
		
	}


	@Override
	public void delete(Integer id) {
		mapper.delete(id);
		
	}


	@Override
	public void update(QualityCostLedger obj) {
		
		mapper.update(obj);
	}


	@Override
	public QualityCostLedger get(Integer id) {
		
		return mapper.get(id);
	}


	@Override
	public List<QualityCostLedger> list(QualityCostLedgerDto dto) {
		
		return mapper.list(dto);
	}


	@Override
	public void loadPage(QualityCostLedgerDto dto) {
		
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
		
	}


	@Override
	public void deleteByQcId(Integer qcId) {
		
		mapper.deleteByQcId(qcId);
	}


	@Override
	public List<Integer> getCmpIdFromResp(Map<String, Object> map) {
		return mapper.getCmpIdFromResp(map);
	}

	@Override
	public void updateByCmpId(QualityCostLedger ledger) {
		mapper.updateByCmpId(ledger);
	}
	
	@Override
	public void deleteByCmpId(Integer cmpId) {
		mapper.deleteByCmpId(cmpId);
	}

	@Override
	public void addBatch(List<QualityCostLedger> list) {
		mapper.addBatch(list);
	}


	@Override
	public List<String> getCostAccount() {
		
		return mapper.getCostAccount();
	}

}
