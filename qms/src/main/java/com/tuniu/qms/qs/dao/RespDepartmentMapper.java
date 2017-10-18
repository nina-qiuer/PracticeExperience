package com.tuniu.qms.qs.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.RespDepartmentDto;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.RespDepartment;

public interface RespDepartmentMapper extends BaseMapper<RespDepartment, RespDepartmentDto> {

	void addBatch(List<QualityCostAuxAccount> list);
	
	void addIsNotExist(RespDepartment dep);
}
