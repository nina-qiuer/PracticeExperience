package com.tuniu.qms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.ResourceDto;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.service.ResourceService;
import com.tuniu.qms.common.util.Constant;

@Controller
@RequestMapping("/common/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService service;
	
	@RequestMapping("/{id}/toAddChild")
	public String toAddChild(@PathVariable Integer id, HttpServletRequest request) {
		Resource resource = new Resource();
		resource.setPid(id);
		resource.setMenuFlag(Constant.NO);
		resource.setOperType(Constant.R_OPERATE);
		resource.setChkAuthFlag(Constant.NO);
		request.setAttribute("resource", resource);
		return "common/resourceForm";
	}
	
	@RequestMapping("/addChild")
	@ResponseBody
	public String addChild(Resource resource, HttpServletRequest request) {
		service.add(resource);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable Integer id, HttpServletRequest request) {
		Resource resource = service.get(id);
		request.setAttribute("resource", resource);
		return "common/resourceForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(Resource resource, HttpServletRequest request) {
		service.update(resource);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}")
	public String get(@PathVariable Integer id, HttpServletRequest request) {
		
		return "";
	}
	
	@RequestMapping("/management")
	public String management(HttpServletRequest request) {
		List<Resource> resourceList = service.list(new ResourceDto());
		request.setAttribute("resourceList", resourceList);
		
		return "common/resourceManagement";
	}

}
