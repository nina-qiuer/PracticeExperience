package com.tuniu.qms.qs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qs.dao.KCPMapper;
import com.tuniu.qms.qs.dto.KCPDto;
import com.tuniu.qms.qs.model.KCP;
import com.tuniu.qms.qs.service.KCPService;

@Service
public class KCPServiceImpl implements KCPService {

	@Autowired
	private KCPMapper mapper;
	
	@Override
	public void add(KCP obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(KCP obj) {
		mapper.update(obj);
	}

	@Override
	public KCP get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<KCP> list(KCPDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(KCPDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

}
