package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.BusinessLogMapper;
import com.tuniu.qms.common.dto.BusinessLogDto;
import com.tuniu.qms.common.model.BusinessLog;
import com.tuniu.qms.common.service.BusinessLogService;

@Service
public class BusinessLogServiceImpl implements BusinessLogService {
	
	@Autowired
	private BusinessLogMapper mapper;

	@Override
	public void add(BusinessLog log) {
		mapper.add(log);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(BusinessLog log) {
		mapper.update(log);
	}

	@Override
	public BusinessLog get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<BusinessLog> list(BusinessLogDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(BusinessLogDto dto) {
		int totalRecords = mapper.count(dto);
		List<BusinessLog> logList = mapper.list(dto);
		
		dto.setTotalRecords(totalRecords);
		dto.setDataList(logList);
	}

}
