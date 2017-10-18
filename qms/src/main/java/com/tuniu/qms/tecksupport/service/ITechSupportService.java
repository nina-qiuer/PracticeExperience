package com.tuniu.qms.tecksupport.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.tecksupport.dto.TechSupportDto;
import com.tuniu.qms.tecksupport.entity.TechSupport;


public interface ITechSupportService  extends BaseService<TechSupport,TechSupportDto> {
	void execute(String sqlParam);
}
