package com.tuniu.qms.common.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dto.UserDepjobDto;
import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;

public interface UserService extends BaseService<User, UserDto> {
	
	User getUserByUserName(String userName);
	
	User getUserByRealName(String realName);
	
	void addUserDepjob(UserDepjobDto dto);
	
	void updateUserDepjob(UserDepjobDto dto);
	
	List<User> getUserDataCache();
	
	List<User> getUnUserDataCache();
	
	List<User> getUsersByDepId(Integer depId);
	
	List<User> getUsersByJobName(String jobName);
	
	void assignRole(UserDto dto);
	
	List<User>  getAllUserData();
	
	List<Integer> listDepId(Integer depId);
	
	List<User> listByDepId(Map<String, Object> map);
	
	List<User> listQcUser();
	
	List<User> listByDepName(Map<String, Object> map);
	
	List<User> listByRealName(Map<String, Object> map);
	
} 
