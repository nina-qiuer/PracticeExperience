package com.tuniu.config.db.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.gt.complaint.service.IComplaintTSPService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	@Qualifier("tspService")
	private IComplaintTSPService complaintTSPService;

	@RequestMapping("/getOrderDetailPageUrl")
	@ResponseBody
	public String getOrderDetailPageUrl(HttpServletRequest request) {
		Integer orderId = Integer.valueOf(request.getParameter("orderId"));
		return complaintTSPService.getOrderDetailPageUrl(orderId);
	}
}
