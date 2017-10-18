package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.qs.dto.PreSaleReturnVisitDto;
import com.tuniu.qms.qs.service.PreSaleReturnVisitService;

@Controller
@RequestMapping("/qs/returnVisit")
public class PreSaleReturnVisitController {
	
	@Autowired
	private PreSaleReturnVisitService service;
	
	@RequestMapping("/list")
	public String list(PreSaleReturnVisitDto dto, HttpServletRequest request) {
		
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qs/returnvisit/pre_sale_return_visit_list";
	}

	
	
}
