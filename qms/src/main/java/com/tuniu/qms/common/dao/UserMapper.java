package com.tuniu.qms.common.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.UserDepjobDto;
import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.User;

public interface UserMapper extends BaseMapper<User, UserDto> {
	
	User getUserByUserName(String userName);
	
	User getUserByRealName(String realName);
	
	void addUserDepjob(UserDepjobDto dto);
	
	void updateUserDepjob(UserDepjobDto dto);
	
	int countUserRole(UserDto dto);
	
	List<User> listUserRole(UserDto dto);
	
	List<User> getUsersByDepId(Integer depId);
	
	List<User> getUsersByJobName(String jobName);
	
	void assignRole(UserDto dto);
  
	List<User> listUnUse(UserDto dto);
	
	List<User> listByDepId(Map<String, Object> map);
	
	List<User> listQcUser();
	
	List<User> listByDepName(Map<String, Object> map);
	
    List<User> listByRealName(Map<String, Object> map);
    
}
