package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.Role;

public class RoleDto extends BaseDto<Role> {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
