package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.PositionMapper;
import com.tuniu.qms.common.dto.PositionDto;
import com.tuniu.qms.common.model.Position;
import com.tuniu.qms.common.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionMapper mapper;

	@Override
	public void add(Position pos) {
		mapper.add(pos);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Position pos) {
		mapper.update(pos);
	}

	@Override
	public Position get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Position> list(PositionDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(PositionDto dto) {
		
	}

}
