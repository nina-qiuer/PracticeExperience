package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.PunishStandardMapper;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.PunishStandard;
import com.tuniu.qms.qc.service.PunishStandardService;

@Service
public class PunishStandardServiceImpl implements PunishStandardService {

	@Autowired
	private PunishStandardMapper mapper;
	
	@Override
	public void add(PunishStandard obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(PunishStandard obj) {
		mapper.update(obj);
	}

	@Override
	public PunishStandard get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<PunishStandard> list(PunishStandardDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(PunishStandardDto dto) {
		
	}

	/**
	 * 获取处罚依据等级
	 */
	public List<String> getLevel(PunishStandardDto dto) {

		
		return mapper.getLevel(dto);
	}

}
