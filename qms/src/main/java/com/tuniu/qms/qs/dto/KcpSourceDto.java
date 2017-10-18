package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.KcpSource;

public class KcpSourceDto extends BaseDto<KcpSource>{

	
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
