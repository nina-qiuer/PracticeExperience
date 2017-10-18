package com.tuniu.qms.common.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.dto.DepartmentJobDto;
import com.tuniu.qms.common.dto.DepartmentUserDto;
import com.tuniu.qms.common.model.Department;

public interface DepartmentMapper extends BaseMapper<Department, DepartmentDto> {
	
	void addDepUser(DepartmentUserDto dto);
	
	void updateDepUser(DepartmentUserDto dto);
	
	void deleteDepUser(Integer id);
	
	void addDepJob(DepartmentJobDto dto);
	
	void updateDepJob(DepartmentJobDto dto);
	
	Department getDepByNameAndPid(DepartmentDto dto);
	
	void updateCmpQcUseFlag(Map<String, Object> map);
	
	Department getDepById(int depId);
	
	List<Department> listAll(DepartmentDto dto);
	
	List<Department> getUseDepData(DepartmentDto dto);
	
	void updateDepCmpQcUseFlag(Map<String, Object> map); 
	
	void updateDep(Department dep);
	
	List<Department> getDepDetailList(Map<String, Object> map);
	
	Department getDepByName(Map<String, Object> map);
}
