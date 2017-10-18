package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.DevUpLoadAttachMapper;
import com.tuniu.qms.qc.dto.DevUpLoadAttachDto;
import com.tuniu.qms.qc.model.DevUpLoadAttach;
import com.tuniu.qms.qc.service.DevUpLoadService;

@Service
public class DevUpLoadServiceImpl implements DevUpLoadService {

	
	@Autowired
	private DevUpLoadAttachMapper mapper;
	
	@Override
	public void add(DevUpLoadAttach obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(DevUpLoadAttach obj) {
		
	}

	@Override
	public DevUpLoadAttach get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<DevUpLoadAttach> list(DevUpLoadAttachDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(DevUpLoadAttachDto dto) {
		
	}

	

	
	
}
