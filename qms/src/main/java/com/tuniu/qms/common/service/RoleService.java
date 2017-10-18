package com.tuniu.qms.common.service;

import com.tuniu.qms.common.dto.RoleDto;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.service.base.BaseService;

public interface RoleService extends BaseService<Role, RoleDto> {
	
	void deleteResources(Integer id);
	
	void addResources(Integer id, String[] resIds);
	
}
