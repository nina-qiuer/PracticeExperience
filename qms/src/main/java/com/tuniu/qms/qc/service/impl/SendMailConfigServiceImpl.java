package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.SendMailConfigMapper;
import com.tuniu.qms.qc.dto.SendMailConfigDto;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.SendMailConfigService;

@Service
public class SendMailConfigServiceImpl implements SendMailConfigService {

	@Autowired
	private SendMailConfigMapper mapper;

	@Override
	public void add(SendMailConfig obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SendMailConfig obj) {
		mapper.update(obj);
	}

	@Override
	public SendMailConfig get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SendMailConfig> list(SendMailConfigDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SendMailConfigDto dto) {
	}

	@Override
	public int getExistConfig(Map<String, Object> map) {
		
		return mapper.getExistConfig(map);
	}

	@Override
	public SendMailConfig getByType(String mailType) {
		return mapper.getByType(mailType);
	}

}