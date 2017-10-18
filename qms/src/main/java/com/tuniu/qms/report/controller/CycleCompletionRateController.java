package com.tuniu.qms.report.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.report.dto.CycleCompletionRateDto;
import com.tuniu.qms.report.service.CycleCompletionRateService;

@Controller
@RequestMapping("/report/completionRate")
public class CycleCompletionRateController {
	
	@Autowired
	private CycleCompletionRateService service;
	
	@RequestMapping("/list")
	public String list(CycleCompletionRateDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getQcPeriodTimeBgn()) && StringUtils.isBlank(dto.getQcPeriodTimeEnd())) {
			java.sql.Date today = new java.sql.Date(new Date().getTime());
			dto.setQcPeriodTimeBgn(today.toString());
			dto.setQcPeriodTimeEnd(today.toString());
			dto.setStatisticDate(today.toString());
		}
		request.setAttribute("dataList", service.list(dto));
		request.setAttribute("dto", dto);
		return "report/cycleCompletionRateList";
	}
	
}
