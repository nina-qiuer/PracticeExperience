package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.TalkConfigMapper;
import com.tuniu.qms.common.dto.TalkConfigDto;
import com.tuniu.qms.common.model.TalkConfig;
import com.tuniu.qms.common.service.TalkConfigService;

@Service
public class TalkConfigServiceImpl implements TalkConfigService {

    @Autowired
    private TalkConfigMapper mapper;

    @Override
    public void add(TalkConfig obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(Integer id) {
        mapper.delete(id);
    }

    @Override
    public void update(TalkConfig obj) {
        mapper.update(obj);
    }

    @Override
    public TalkConfig get(Integer id) {
        return mapper.get(id);
    }

    @Override
    public List<TalkConfig> list(TalkConfigDto dto) {
        return mapper.list(dto);
    }

    @Override
    public void loadPage(TalkConfigDto dto) {
    }

	@Override
	public TalkConfig getByType(TalkConfigDto dto) {
		
		return mapper.getByType(dto);
	}

}