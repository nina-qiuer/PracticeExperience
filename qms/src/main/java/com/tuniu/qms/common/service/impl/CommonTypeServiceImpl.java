package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.CommonTypeMapper;
import com.tuniu.qms.common.dto.CommonTypeDto;
import com.tuniu.qms.common.model.CommonType;
import com.tuniu.qms.common.service.CommonTypeService;

/**
 * 通用类型配置
 * @author zhangsensen
 *
 */
@Service
public class CommonTypeServiceImpl implements CommonTypeService {
	
	@Autowired
	private CommonTypeMapper commonTypeMapper;
	
	@Override
	public void add(CommonType obj) {
		commonTypeMapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		commonTypeMapper.delete(id);
	}

	@Override
	public void update(CommonType obj) {
		commonTypeMapper.update(obj);
	}

	@Override
	public CommonType get(Integer id) {
		return commonTypeMapper.get(id);
	}

	@Override
	public List<CommonType> list(CommonTypeDto dto) {
		return commonTypeMapper.list(dto);
	}

	@Override
	public void loadPage(CommonTypeDto dto) {

	}

}
