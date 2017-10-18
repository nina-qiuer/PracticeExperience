package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.ResourceMapper;
import com.tuniu.qms.common.dto.ResourceDto;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.service.ResourceService;
import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.DateUtil;
import com.whalin.MemCached.MemCachedClient;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceMapper mapper;
	
	@Autowired
	private MemCachedClient memCachedClient;

	@Override
	public void add(Resource res) {
		mapper.add(res);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Resource res) {
		mapper.update(res);
	}

	@Override
	public Resource get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Resource> list(ResourceDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(ResourceDto dto) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> getResDataCache() {
		List<Resource> resList = (List<Resource>) memCachedClient.get(CacheConstant.RES_DATA);
		if (null == resList || resList.isEmpty()) {
			resList = mapper.list(new ResourceDto());
			memCachedClient.set(CacheConstant.RES_DATA, resList, DateUtil.getTodaySurplusTime());
		}
		return resList;
	}

}
