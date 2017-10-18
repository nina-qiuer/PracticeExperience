package com.tuniu.qms.report.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.report.dto.CmpQcBUStatDto;
import com.tuniu.qms.report.model.CmpQcBUStat;
import com.tuniu.qms.report.service.CmpQcBUStatService;

@Controller
@RequestMapping("/report/cmpQcBUStat")
public class CmpQcBUStatController {
	
	@Autowired
	private CmpQcBUStatService service;
	
	@RequestMapping("/list")
	public String list(CmpQcBUStatDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatDateBgn()) && StringUtils.isBlank(dto.getStatDateEnd())) {
			java.sql.Date today = new java.sql.Date(new Date().getTime());
			dto.setStatDateBgn(today.toString());
			dto.setStatDateEnd(today.toString());
		}
		List<CmpQcBUStat> dataList = service.list(dto);
		//部门汇总
		CmpQcBUStat departmentTotal = service.departmentTotal(dataList);
		
		request.setAttribute("dataList", dataList);
		request.setAttribute("departmentTotal", departmentTotal);
		request.setAttribute("dto", dto);
		return "report/cmpQcBUStatList";
	}
	
}
