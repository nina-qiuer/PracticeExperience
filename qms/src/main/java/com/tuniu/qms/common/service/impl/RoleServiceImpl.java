package com.tuniu.qms.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.RoleMapper;
import com.tuniu.qms.common.dto.RoleDto;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper mapper;

	@Override
	public void add(Role role) {
		mapper.add(role);
	}

	@Override
	public void delete(Integer id) {
		mapper.deleteResources(id);
		mapper.delete(id);
	}

	@Override
	public void update(Role role) {
		mapper.update(role);
	}

	@Override
	public Role get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Role> list(RoleDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void deleteResources(Integer id) {
		mapper.deleteResources(id);
	}

	@Override
	public void addResources(Integer id, String[] resIds) {
		mapper.deleteResources(id); // 先删除角色与资源之间的关联
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for (String resId : resIds) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId", id);
			map.put("resId", resId);
			dataList.add(map);
		}
		mapper.addResources(dataList); // 再添加角色与资源之间的关联
	}

	@Override
	public void loadPage(RoleDto dto) {
		
	}

}
