package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcPointAttachMapper;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.service.QcPointAttachService;

@Service
public class QcPointAttachServiceImpl implements QcPointAttachService {

	@Autowired
	private QcPointAttachMapper mapper;
	
	@Override
	public void add(QcPointAttach obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcPointAttach obj) {
	}

	@Override
	public QcPointAttach get(Integer id) {
		return null;
	}

	@Override
	public List<QcPointAttach> list(QcPointAttachDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcPointAttachDto dto) {
	}

	@Override
	public void deleteByDto(QcPointAttachDto dto) {
		mapper.deleteByDto(dto);
	}

	@Override
	public List<Map<String, Object>> listImproveAttach(QcPointAttachDto attachDto) {
		return mapper.listImproveAttach(attachDto);
	}

	@Override
	public List<QcPointAttach> getAttachList(Integer dataId, int attachBillType) {
		QcPointAttachDto attachDto = new QcPointAttachDto();
		attachDto.setQcId(dataId);
		attachDto.setBillType(attachBillType);
		
		return this.list(attachDto);
	}
	
}
