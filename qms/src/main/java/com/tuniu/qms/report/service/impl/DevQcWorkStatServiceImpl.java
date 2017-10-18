package com.tuniu.qms.report.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuniu.qms.report.dao.DevQcWorkStatMapper;
import com.tuniu.qms.report.dto.DevQcWorkStatDto;
import com.tuniu.qms.report.model.DevQcWorkStat;
import com.tuniu.qms.report.service.DevQcWorkStatService;

@Service
public class DevQcWorkStatServiceImpl implements DevQcWorkStatService {
	
	@Autowired
	private DevQcWorkStatMapper mapper;

	@Override
	public void add(DevQcWorkStat obj) {}

	@Override
	public void delete(Integer id) {}

	@Override
	public void update(DevQcWorkStat obj) {}

	@Override
	public DevQcWorkStat get(Integer id) {
		return null;
	}

	@Override
	public void loadPage(DevQcWorkStatDto dto) {}
	
	@Override
	public List<DevQcWorkStat> list(DevQcWorkStatDto dto) {
		List<DevQcWorkStat> dataList = mapper.list(dto);
		DevQcWorkStat combine = mapper.getCombine(dto);
		dataList.add(combine);
		return dataList;
	}
	
	
}
