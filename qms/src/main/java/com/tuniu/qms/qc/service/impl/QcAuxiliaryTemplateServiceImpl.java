package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcAuxiliaryTemplateMapper;
import com.tuniu.qms.qc.dto.QcAuxiliaryTemplateDto;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;
import com.tuniu.qms.qc.service.QcAuxiliaryTemplateService;

@Service
public class QcAuxiliaryTemplateServiceImpl implements QcAuxiliaryTemplateService{

	
	@Autowired
	private QcAuxiliaryTemplateMapper mapper;
	@Override
	public void add(QcAuxiliaryTemplate obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(QcAuxiliaryTemplate obj) {
	
		mapper.update(obj);
	}

	@Override
	public QcAuxiliaryTemplate get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QcAuxiliaryTemplate> list(QcAuxiliaryTemplateDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcAuxiliaryTemplateDto dto) {
		
	}

	@Override
	public QcAuxiliaryTemplate getByQcType(QcAuxiliaryTemplateDto dto) {
		return mapper.getByQcType(dto);
	}

	


}
