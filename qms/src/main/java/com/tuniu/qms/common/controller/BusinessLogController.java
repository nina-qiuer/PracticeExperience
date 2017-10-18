package com.tuniu.qms.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.dto.BusinessLogDto;
import com.tuniu.qms.common.service.BusinessLogService;

@Controller
@RequestMapping("/common/businessLog")
public class BusinessLogController {
	
	@Autowired
	private BusinessLogService service;
	
	@RequestMapping("/list")
	public String list(BusinessLogDto dto, HttpServletRequest request) {
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		
		return "common/businessLogList";
	}
	
	@RequestMapping("/{id}")
	public String get(@PathVariable Integer id, HttpServletRequest request) {
		
		return "showUser";
	}
	
}
