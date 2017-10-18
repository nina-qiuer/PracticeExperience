package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.RtxRemindMapper;
import com.tuniu.qms.common.dto.RtxRemindDto;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.service.RtxRemindService;

@Service
public class RtxRemindServiceImpl implements RtxRemindService {

	@Autowired
	private RtxRemindMapper mapper;
	
	@Override
	public void add(RtxRemind obj) {
		mapper.add(obj);

	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(RtxRemind obj) {
		mapper.update(obj);
	}

	@Override
	public RtxRemind get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<RtxRemind> list(RtxRemindDto dto) {
		return mapper.list(dto);
	}
	
	
	@Override
	public void loadPage(RtxRemindDto dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RtxRemind> getList2Send() {
		return mapper.getList2Send();
	}

}
