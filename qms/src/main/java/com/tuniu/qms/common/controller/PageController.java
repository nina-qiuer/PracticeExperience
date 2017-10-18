package com.tuniu.qms.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.service.TspService;

@Controller
@RequestMapping("/common/page")
public class PageController {
	
	@Autowired
	private TspService tspService;
	
	/**
	 * 订单详情页面链接
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryOrderPageUrl")
	@ResponseBody
	public ResponseDto queryOrderPageUrl(Integer orderId){
		
		ResponseDto dto = new ResponseDto();
		
		dto.setData(tspService.getOrderDetailPageUrl(orderId));
        return dto;
	}
}
