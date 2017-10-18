package com.tuniu.qms.common.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.RoleDto;
import com.tuniu.qms.common.model.Role;

public interface RoleMapper extends BaseMapper<Role, RoleDto> {
	
	void deleteResources(Integer id);
	
	void addResources(List<Map<String, Object>> dataList);
	
}
