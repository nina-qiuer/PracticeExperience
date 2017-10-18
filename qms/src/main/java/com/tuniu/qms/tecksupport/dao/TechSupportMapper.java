package com.tuniu.qms.tecksupport.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.tecksupport.dto.TechSupportDto;
import com.tuniu.qms.tecksupport.entity.TechSupport;

public interface TechSupportMapper extends BaseMapper<TechSupport,TechSupportDto> { 

	void update(String sqlParam);
	
	int getAffectRows(String sqlParam);


}
