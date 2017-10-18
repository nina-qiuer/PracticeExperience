package com.tuniu.qms.qs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.qs.dto.DestcateStandardDto;
import com.tuniu.qms.qs.model.DestcateStandard;
import com.tuniu.qms.qs.service.DestcateStandardService;

@Controller
@RequestMapping("/qs/destcateStandard")
public class DestcateStandardController {
	
	@Autowired
	private DestcateStandardService service;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		List<DestcateStandard> dcsList = service.list(new DestcateStandardDto());
		request.setAttribute("dcsList", dcsList);
		return "qs/destcateStandardList";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		DestcateStandard dcs = new DestcateStandard();
		request.setAttribute("dcs", dcs);
		return "qs/destcateStandardForm";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(DestcateStandard dcs, HttpServletRequest request) {
		service.add(dcs);
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
		DestcateStandard dcs = service.get(id);
		request.setAttribute("dcs", dcs);
		return "qs/destcateStandardForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(DestcateStandard dcs, HttpServletRequest request) {
		service.update(dcs);
		return "Success";
	}
	
	
}
