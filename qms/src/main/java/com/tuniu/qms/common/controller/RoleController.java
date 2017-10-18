package com.tuniu.qms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.ResourceDto;
import com.tuniu.qms.common.dto.RoleDto;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.service.ResourceService;
import com.tuniu.qms.common.service.RoleService;
import com.tuniu.qms.common.util.Constant;

@Controller
@RequestMapping("/common/role")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		Role role = new Role();
		role.setType(Constant.ROLE_EMPLOYEE);
		request.setAttribute("role", role);
		return "common/roleForm";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Role role, HttpServletRequest request) {
		service.add(role);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		String info = "Success";
		// 校验角色是否关联用户, 如果关联则不允许删除
		
		service.delete(id);
		
		return info;
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable Integer id, HttpServletRequest request) {
		Role role = service.get(id);
		request.setAttribute("role", role);
		return "common/roleForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(Role role, HttpServletRequest request) {
		service.update(role);
		return "Success";
	}
	
	@RequestMapping("/checkRoleSame")
	@ResponseBody
	public boolean checkRoleSame(RoleDto dto, HttpServletRequest request) {
		boolean isNotSame = false;
		List<Role> list = service.list(dto);
		if (list.isEmpty()) {
			isNotSame = true;
		}
		return isNotSame;
	}
	
	@RequestMapping("/management")
	public String management(RoleDto dto, HttpServletRequest request) {
		List<Role> roleList = service.list(dto);
		request.setAttribute("roleList", roleList);
		
		return "common/roleManagement";
	}
	
	@RequestMapping("/{id}/authConfig")
	public String authConfig(@PathVariable Integer id, HttpServletRequest request) {
		ResourceDto resDto = new ResourceDto();
		resDto.setChkAuthFlag("1");
		List<Resource> resourceList = resourceService.list(resDto);
		request.setAttribute("resourceList", resourceList);
		
		resDto.setRoleId(id);
		List<Resource> roleResources = resourceService.list(resDto);
		request.setAttribute("roleResources", roleResources);
		
		Role role = service.get(id);
		request.setAttribute("role", role);
		
		return "common/authConfig";
	}
	
	@RequestMapping("/{id}/saveAuth")
	@ResponseBody
	public void saveAuth(@PathVariable Integer id, HttpServletRequest request) {
		String[] resIds = request.getParameterValues("resIds");
		service.addResources(id, resIds);
	}

}
