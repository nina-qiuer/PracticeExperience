package com.tuniu.qms.report.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.report.dto.CmpQcWorkStatDto;
import com.tuniu.qms.report.service.CmpQcWorkStatService;

@Controller
@RequestMapping("/report/cmpQcWorkStat")
public class CmpQcWorkStatController {
	
	@Autowired
	private CmpQcWorkStatService service;
	
	@RequestMapping("/list")
	public String list(CmpQcWorkStatDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatDateBgn()) && StringUtils.isBlank(dto.getStatDateEnd())) {
			java.sql.Date today = new java.sql.Date(new Date().getTime());
			dto.setStatDateBgn(today.toString());
			dto.setStatDateEnd(today.toString());
		}
		if (StringUtils.isBlank(dto.getStatDateBgn()) ) {
			dto.setStatDateBgn(dto.getStatDateEnd());
		}
		if (StringUtils.isBlank(dto.getStatDateEnd())) {
			java.sql.Date today = new java.sql.Date(new Date().getTime());
			dto.setStatDateEnd(today.toString());
		}
		
		dto.setStatDateBgn(dto.getStatDateBgn() + " 00:00:00");
		dto.setStatDateEnd(dto.getStatDateEnd() + " 23:59:59");
		
		Map<String, Object> dataMap = service.getCmpQcWorkStatReport(dto);
		
		dto.setStatDateBgn(dto.getStatDateBgn().substring(0, 10));
		dto.setStatDateEnd(dto.getStatDateEnd().substring(0, 10));
		request.setAttribute("dataList", dataMap.get("dataList"));
		request.setAttribute("departmentTotal", dataMap.get("departmentTotal"));
		request.setAttribute("departmentWorPro", dataMap.get("departmentWorPro"));
		request.setAttribute("dto", dto);
		return "report/cmpQcWorkStatList";
	}
	
}
