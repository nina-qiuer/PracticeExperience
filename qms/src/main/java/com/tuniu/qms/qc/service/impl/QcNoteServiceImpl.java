package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcNoteMapper;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.model.QcNote;
import com.tuniu.qms.qc.service.QcNoteService;

@Service
public class QcNoteServiceImpl implements QcNoteService {

	@Autowired
	private QcNoteMapper mapper;
	
	@Override
	public void add(QcNote qcNote) {
		mapper.add(qcNote);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcNote qcNote) {
		mapper.update(qcNote);
	}

	@Override
	public QcNote get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QcNote> list(QcNoteDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcNoteDto dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer count(QcNoteDto dto) {
		return mapper.count(dto);
	}

}
