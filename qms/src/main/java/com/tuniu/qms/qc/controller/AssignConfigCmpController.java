package com.tuniu.qms.qc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.dto.AssignConfigCmpDto;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.service.AssignConfigCmpService;

@Controller
@RequestMapping("/qc/assignConfigCmp")
public class AssignConfigCmpController {
	
	@Autowired
	private AssignConfigCmpService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService depService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		List<AssignConfigCmp> acList = service.list(new AssignConfigCmpDto());
		request.setAttribute("acList", acList);
		return "qc/assignConfigCmpList";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		AssignConfigCmp ac = new AssignConfigCmp();
		ac.setNoOrderFlag(Constant.NO);
		request.setAttribute("ac", ac);
		
		List<User> userList = userService.getUserDataCache();
		request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		
		return "qc/assignConfigCmpAdd";
	}
	
	@RequestMapping("/checkPersonSame")
	@ResponseBody
	public boolean checkPersonSame(AssignConfigCmpDto dto, HttpServletRequest request) {
		boolean isNotSame = false;
		List<AssignConfigCmp> list = service.list(dto);
		if (list.isEmpty()) {
			isNotSame = true;
		}
		return isNotSame;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(AssignConfigCmp ac, HttpServletRequest request) {
		UserDto dto = new UserDto();
		dto.setRealName(ac.getQcPersonName());
		User user = userService.list(dto).get(0);
		ac.setQcPersonId(user.getId());
		service.add(ac);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		AssignConfigCmp ac = service.get(id);
		request.setAttribute("ac", ac);
		return "qc/assignConfigCmpUpdate";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(AssignConfigCmp ac, HttpServletRequest request) {
		service.update(ac);
		return "Success";
	}
	
	@RequestMapping("/depConfig")
	public String depConfig(HttpServletRequest request) {
		List<Department> depList = depService.list(new DepartmentDto());
		request.setAttribute("depList", depList);
		return "qc/depConfig";
	}
	
	@RequestMapping("/saveDepConfig")
	@ResponseBody
	public void saveDepConfig(HttpServletRequest request) {
		String[] checkedIds = request.getParameterValues("checkedIds");
		String[] unCheckedIds = request.getParameterValues("unCheckedIds");
		depService.updateCmpQcUseFlag(checkedIds, unCheckedIds);
	}
	
	@RequestMapping("/{qcPersonId}/dockdepConfig")
	public String dockdepConfig(@PathVariable Integer qcPersonId, HttpServletRequest request) {
		DepartmentDto depDto = new DepartmentDto();
		depDto.setCmpQcUseFlag(Constant.YES);
		List<Department> depList = depService.list(depDto);
		request.setAttribute("depList", depList);
		
		List<Map<String, Object>> dockdepList = service.getDockdepList();
		request.setAttribute("dockdepList", dockdepList);
		
		User qcPerson = userService.get(qcPersonId);
		request.setAttribute("qcPerson", qcPerson);
		
		return "qc/dockdepConfig";
	}
	
	@RequestMapping("/dockdepConfigShow")
	public String dockdepConfigShow( HttpServletRequest request) {
		DepartmentDto depDto = new DepartmentDto();
		depDto.setCmpQcUseFlag(Constant.YES);
		List<Department> depList = depService.list(depDto);
		request.setAttribute("depList", depList);
		
		List<Map<String, Object>> dockdepList = service.getDockdepList();
		request.setAttribute("dockdepList", dockdepList);
		
		return "qc/dockdepConfigShow";
	}
	
	
	
	@RequestMapping("/saveDockdeps")
	@ResponseBody
	public void saveDockdeps(HttpServletRequest request) {
		String qcPersonId = request.getParameter("qcPersonId");
		String qcPersonName = request.getParameter("qcPersonName");
		String[] dockdepIds = request.getParameterValues("dockdepIds");
		service.addDockdeps(Integer.valueOf(qcPersonId), qcPersonName, dockdepIds);
	}
	
}
