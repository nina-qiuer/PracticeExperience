package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.RespDepartmentDto;
import com.tuniu.qms.qs.model.DepRespRate;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.RespDepartment;

public interface RespDepartmentService extends BaseService<RespDepartment, RespDepartmentDto>{
	
	void addBatch(List<QualityCostAuxAccount> list);
	
	void addIsNotExist(RespDepartment dep);
	
	void updateDep(List<DepRespRate> list,User user);

}
