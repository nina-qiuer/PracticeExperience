package com.tuniu.qms.qs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dao.DestcateStandardMapper;
import com.tuniu.qms.qs.dto.DestcateStandardDto;
import com.tuniu.qms.qs.model.DestcateStandard;
import com.tuniu.qms.qs.service.DestcateStandardService;
import com.whalin.MemCached.MemCachedClient;

@Service
public class DestcateStandardServiceImpl implements DestcateStandardService {

	@Autowired
	private DestcateStandardMapper mapper;
	
	@Autowired
	private MemCachedClient memCachedClient;

	@Override
	public void add(DestcateStandard obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(DestcateStandard obj) {
		mapper.update(obj);
	}

	@Override
	public DestcateStandard get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<DestcateStandard> list(DestcateStandardDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(DestcateStandardDto dto) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DestcateStandard> getDestStdCache() {
		List<DestcateStandard> destStdList = (List<DestcateStandard>) memCachedClient.get(CacheConstant.DEST_STD);
		if (null == destStdList || destStdList.isEmpty()) {
			destStdList = mapper.list(new DestcateStandardDto());
			memCachedClient.set(CacheConstant.DEST_STD, destStdList, DateUtil.getTodaySurplusTime());
		}
		return destStdList;
	}

}
