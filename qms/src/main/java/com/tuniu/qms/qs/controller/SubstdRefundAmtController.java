package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdRefundAmtDto;
import com.tuniu.qms.qs.service.SubstdRefundAmtService;

@Controller
@RequestMapping("/qs/substdRefundAmt")
public class SubstdRefundAmtController {
	
	@Autowired
	private SubstdRefundAmtService service;
	
	private void list(SubstdRefundAmtDto dto, HttpServletRequest request) {
		if (null == dto.getAddDate()) {
			dto.setAddDate(DateUtil.getSqlYesterday().toString());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
	}
	
	private void graphDep(SubstdRefundAmtDto dto, HttpServletRequest request) {
		if (null == dto.getAddDate()) {
			dto.setAddDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataListNum", service.statGraphDepByNum(dto));
		request.setAttribute("dataListTotAmount", service.statGraphDepByTotAmount(dto));
		request.setAttribute("dto", dto);
	}
	
	private void graphDate(SubstdRefundAmtDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getAddDateBgn()) && StringUtils.isBlank(dto.getAddDateEnd())) {
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			java.sql.Date dateBgn = DateUtil.addSqlDates(yesterday, -15);
			dto.setAddDateBgn(dateBgn.toString());
			dto.setAddDateEnd(yesterday.toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataListNum", service.statGraphDateByNum(dto));
		request.setAttribute("dataListTotAmount", service.statGraphDateByTotAmount(dto));
		request.setAttribute("dto", dto);
	}
	
	/** 订单超额退款监控 */
	@RequestMapping("/beyondRefund")
	public String beyondRefund(HttpServletRequest request) {
		return "qs/substd_refund_amt/beyondRefund";
	}
	
	/** 订单超额退款监控-列表 */
	@RequestMapping("/beyondRefundList")
	public String beyondRefundList(SubstdRefundAmtDto dto, HttpServletRequest request) {
		list(dto, request);
		return "qs/substd_refund_amt/beyondRefundList";
	}
	
	/** 订单超额退款监控-图表-组织架构维度 */
	@RequestMapping("/beyondRefundDepGraph")
	public String beyondRefundDepGraph(SubstdRefundAmtDto dto, HttpServletRequest request) {
		graphDep(dto, request);
		return "qs/substd_refund_amt/beyondRefundDepGraph";
	}
	
	/** 订单超额退款监控-图表-添加日期维度 */
	@RequestMapping("/beyondRefundDateGraph")
	public String beyondRefundDateGraph(SubstdRefundAmtDto dto, HttpServletRequest request) {
		graphDate(dto, request);
		return "qs/substd_refund_amt/beyondRefundDateGraph";
	}
	
}
