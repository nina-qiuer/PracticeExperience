package com.tuniu.qms.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.UserMapper;
import com.tuniu.qms.common.dto.ResourceDto;
import com.tuniu.qms.common.dto.UserDepjobDto;
import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.ResourceService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.util.NodeUtil;
import com.whalin.MemCached.MemCachedClient;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private ResourceService resService;
	
	@Autowired
	private MemCachedClient memCachedClient;

	@Autowired
	private DepartmentService depService;
	

		
	@Override
	public void add(User user) {
		mapper.add(user);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(User user) {
		mapper.update(user);
	}

	@Override
	public User get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<User> list(UserDto dto) {
		return mapper.list(dto);
	}

	@Override
	public User getUserByUserName(String userName) {
		ResourceDto resDto = new ResourceDto();
		resDto.setChkAuthFlag("0");
		List<Resource> unChkAuthResList = resService.list(resDto); // 取出不鉴权资源
		
		User user = mapper.getUserByUserName(userName);
		if (null != user) { // 添加不鉴权资源
			Role role = user.getRole();
			if (null != role) {
				List<Resource> resList = role.getResourceList();
				if (null != resList) {
					resList.addAll(unChkAuthResList);
					role.setResourceList(resList);
				}
			} else {
				role = new Role();
				role.setResourceList(unChkAuthResList);
				user.setRole(role);
			}
		}
		return user;
	}

	@Override
	public void addUserDepjob(UserDepjobDto dto) {
		mapper.addUserDepjob(dto);
	}

	@Override
	public void updateUserDepjob(UserDepjobDto dto) {
		mapper.updateUserDepjob(dto);
	}

	@Override
	public void loadPage(UserDto dto) {
		int totalRecords = mapper.countUserRole(dto);
		List<User> userList = mapper.listUserRole(dto);
		
		dto.setTotalRecords(totalRecords);
		dto.setDataList(userList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserDataCache() {
		List<User> userList = (List<User>) memCachedClient.get(CacheConstant.USER_DATA);
		if (null == userList || userList.isEmpty()) {
			userList = mapper.list(new UserDto());
			memCachedClient.set(CacheConstant.USER_DATA, userList, DateUtil.getTodaySurplusTime());
		}
		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUnUserDataCache() {
		List<User> userList = (List<User>) memCachedClient.get(CacheConstant.USER_UNUSE_DATA);
		if (null == userList || userList.isEmpty()) {
			userList = mapper.listUnUse( new UserDto());
			memCachedClient.set(CacheConstant.USER_UNUSE_DATA, userList, DateUtil.getTodaySurplusTime());
		}
		return userList;
	}
	
	@Override
	public List<User> getUsersByDepId(Integer depId) {
		return mapper.getUsersByDepId(depId);
	}

	@Override
	public List<User> getUsersByJobName(String jobName) {
		return mapper.getUsersByJobName(jobName);
	}

	@Override
	public void assignRole(UserDto dto) {
		mapper.assignRole(dto);
	}

	@Override
	public User getUserByRealName(String realName) {
		return mapper.getUserByRealName(realName);
	}

	@Override
	public List<User> getAllUserData() {
		
		  List<User> userList = getUserDataCache();
		  List<User> userAllList = getUnUserDataCache();
		  userList.addAll(userAllList);
		  return userList;
	}

	@Override
	public List<Integer> listDepId(Integer depId) {
		
		List<Department> list =  depService.getDepDataCache();
	    List<Integer> depList = NodeUtil.getChildNodes(list, depId);
	    HashSet<Integer> h = new HashSet<Integer>(depList); 
	    depList.clear(); 
	    depList.addAll(h); 
		return depList;
	}


	@Override
	public List<User> listByDepId(Map<String, Object> map) {
		
		
		return mapper.listByDepId(map);
	}

	@Override
	public List<User> listQcUser() {
		
		return mapper.listQcUser();
	}

	@Override
	public List<User> listByDepName(Map<String, Object> map) {
		
		
		return mapper.listByDepName(map);
	}

	@Override
	public List<User> listByRealName(Map<String, Object> map) {
		
		return mapper.listByRealName(map);
	}

	
	
}
