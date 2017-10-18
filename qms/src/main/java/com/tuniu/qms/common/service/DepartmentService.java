package com.tuniu.qms.common.service;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.dto.DepartmentJobDto;
import com.tuniu.qms.common.dto.DepartmentUserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.base.BaseService;

public interface DepartmentService extends BaseService<Department, DepartmentDto> {
	
	void addDepUser(DepartmentUserDto dto);
	
	void updateDepUser(DepartmentUserDto dto);
	
	void deleteDepUser(Integer id);
	
	void addDepJob(DepartmentJobDto dto);
	
	void updateDepJob(DepartmentJobDto dto);
	
	List<Department> getDepFullNameList();
	
	List<Department> getAllDepFullNameList();//包含已撤销的部门
	
	List<Department> getDepDataCache();
	
	List<Department> getAllDepDataCache();//包含已撤销的部门
	
	/**
	 * 根据全名查询部门
	 * @param fullName
	 * @return
	 */
	Department getDepByFullName(String fullName);
	
	void updateCmpQcUseFlag(String[] checkedIds, String[] unCheckedIds);
	
	/**
	 * 根据部门Id查部门全名
	 * @param qptId
	 * @return
	 */
	String	getDepFullNameById(int depId);
	
	/*String getDepFullName(int depId,List<Department> list);*/
	
	Department getDepById(int depId);
	
	List<Department> getUseDepData(DepartmentDto dto);
	
	void updateDepCmpQcUseFlag(Map<String, Object> map);
	
	void updateDep(Department dep);
	
	List<Department> getDepDetailList(Map<String, Object> map);
	
	Department getDepByName(Map<String, Object> map);
	
	Department getDepByNameAndPid(DepartmentDto dto);
	/**
	 * 根据部门id获得该部门所有上层部门和自己
	 * @param depId
	 * @return
	 */
	Stack<Department> getDepartmentList(Integer depId);
}
