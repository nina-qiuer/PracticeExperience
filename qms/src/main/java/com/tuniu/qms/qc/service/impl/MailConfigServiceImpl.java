package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.MailConfigMapper;
import com.tuniu.qms.qc.dto.MailConfigDto;
import com.tuniu.qms.qc.model.MailConfig;
import com.tuniu.qms.qc.service.MailConfigService;

@Service
public class MailConfigServiceImpl implements MailConfigService {

	@Autowired
	private MailConfigMapper mapper;

	@Override
	public void add(MailConfig obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(MailConfig obj) {
		mapper.update(obj);
	}

	@Override
	public MailConfig get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<MailConfig> list(MailConfigDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(MailConfigDto dto) {
	}

}