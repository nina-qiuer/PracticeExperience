package com.tuniu.qms.qs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.qs.dao.QualityCostAuxAccountMapper;
import com.tuniu.qms.qs.dto.QualityCostAuxAccountDto;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;

@Service
public class QualityCostAuxAccountServiceImpl implements QualityCostAuxAccountService {

	@Autowired
	private QualityCostAuxAccountMapper mapper;
	

	@Override
	public void add(QualityCostAuxAccount obj) {

		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {

		mapper.delete(id);
	}

	@Override
	public void update(QualityCostAuxAccount obj) {
		
		mapper.update(obj);
		
	}


	@Override
	public QualityCostAuxAccount get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<QualityCostAuxAccount> list(QualityCostAuxAccountDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QualityCostAuxAccountDto dto) {
		
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
		
	}

	@Override
	public void deleteByQcId(Integer qcId) {
		
		mapper.deleteByQcId(qcId);
	}

	@Override
	public void updateByCmpId(QualityCostAuxAccount auxAccount) {
		
		mapper.updateByCmpId(auxAccount);
	}

	@Override
	public List<QualityCostAuxAccount> listAllDep() {
		
		return mapper.listAllDep();
	}

	@Override
	public List<QualityCostAuxAccount> listUseDep() {
		
		return mapper.listUseDep();
	}

	@Override
	public void updateDep(QualityCostAuxAccount auxAccount) {
		
		mapper.updateDep(auxAccount);
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
	public List<QualityCostAuxAccount> listPrdLineDepInfos() {
		return mapper.listPrdLineDepInfos();
	}

	@Override
	public void updateDepByPrdLineId(List<PrdLineDepInfoDto> unSameDepInfos) {
		for (PrdLineDepInfoDto depInfo : unSameDepInfos) {
			mapper.updateDepByPrdLineId(depInfo);
		}
	}
	
}
