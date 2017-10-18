package com.tuniu.qms.qs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qs.dao.KcpTypeMapper;
import com.tuniu.qms.qs.dto.KcpTypeDto;
import com.tuniu.qms.qs.model.KcpType;
import com.tuniu.qms.qs.service.KcpTypeService;

@Service
public class KcpTypeServiceImpl implements KcpTypeService {

	
	@Autowired
	private KcpTypeMapper mapper;
	@Override
	public void add(KcpType obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(KcpType obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public KcpType get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<KcpType> list(KcpTypeDto dto) {
	
		return mapper.list(dto);
	}

	@Override
	public void loadPage(KcpTypeDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	@Override
	public int getKcpTypeIsExist(KcpTypeDto dto) {
		
		return mapper.getKcpTypeIsExist(dto);
	}

	
	
}
