package com.tuniu.qms.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.RoleDto;
import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.RoleService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;

@Controller
@RequestMapping("/common/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/authManagement")
	public String authManagement(UserDto dto, HttpServletRequest request) {
		service.loadPage(dto);
		List<Role> roleList = roleService.list(new RoleDto());
		request.setAttribute("roleList", roleList);
		request.setAttribute("dto", dto);
		
		return "common/authManagement";
	}
	
	@RequestMapping("/toAssignRole")
	public String toAssignRole(UserDto dto, HttpServletRequest request) {
		List<Department> depList = depService.getDepDataCache();
		List<User> uList = service.getUserDataCache();
		List<Job> jobList = jobService.getJobDataCache();
		List<Role> roleList = roleService.list(new RoleDto());
		request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		request.setAttribute("userNames", CommonUtil.getUserNames(uList));
		request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		request.setAttribute("roleList", roleList);
		
		Integer searchType = dto.getSearchType();
		if (null != searchType) {
			List<User> userList = new ArrayList<User>();
			if (1 == searchType) {
				userList = service.list(dto);
			} else if (2 == searchType) { //获取组织下所有人员
				
				Department dep = depService.getDepByFullName(dto.getDepName());
				List<Integer> list = service.listDepId(dep.getId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("depIds", list);
				userList = service.listByDepId(map);
			} else if (3 == searchType) {
				userList = service.getUsersByJobName(dto.getJobName());
			}
			request.setAttribute("userList", userList);
		} else {
			dto.setSearchType(1);
		}
		
		request.setAttribute("dto", dto);
		return "common/assignRole";
	}
	
	@RequestMapping("/assignRole")
	@ResponseBody
	public String assignRole(UserDto dto, HttpServletRequest request) {
		String userIdStr = CommonUtil.arrToStr(dto.getUserIds());
		dto.setUserIdStr(userIdStr);
		service.assignRole(dto);
		return "Success";
	}

	@RequestMapping("/checkUserExists")
	@ResponseBody
	public boolean checkUserExists(UserDto dto, HttpServletRequest request) {
		boolean isExists = false;
		List<User> list = service.list(dto);
		if (!list.isEmpty()) {
			isExists = true;
		}
		return isExists;
	}
	
	/**
	 * 获取对缓存中用户进行处理后的用户列表
	 * @return List<Map<String, Object>> 用户列表，map结构为label和realName,label由用户名字的汉字和拼音组成，作为自动补全匹配。 
	 * @author jiangye
	 */
	@RequestMapping("/getUserNamesInJSON")
	@ResponseBody
	public List<Map<String, Object>> getUserNamesInJSON() {
		return CommonUtil.getUserNames(service.getUserDataCache());
	}

}
