package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdOrderAmtDto;
import com.tuniu.qms.qs.service.SubstdOrderAmtService;

@Controller
@RequestMapping("/qs/substdOrderAmt")
public class SubstdOrderAmtController {
	
	@Autowired
	private SubstdOrderAmtService service;
	
	private void list(SubstdOrderAmtDto dto, HttpServletRequest request) {
		if (null == dto.getStatisticDate()) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
	}
	
	private void graphDep(SubstdOrderAmtDto dto, HttpServletRequest request) {
		if (null == dto.getStatisticDate()) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataListNum", service.statGraphDepByNum(dto));
		request.setAttribute("dataListTotAmount", service.statGraphDepByTotAmount(dto));
		request.setAttribute("dto", dto);
	}
	
	private void graphDate(SubstdOrderAmtDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDateBgn()) && StringUtils.isBlank(dto.getStatisticDateEnd())) {
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			java.sql.Date dateBgn = DateUtil.addSqlDates(yesterday, -15);
			dto.setStatisticDateBgn(dateBgn.toString());
			dto.setStatisticDateEnd(yesterday.toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataListNum", service.statGraphDateByNum(dto));
		request.setAttribute("dataListTotAmount", service.statGraphDateByTotAmount(dto));
		request.setAttribute("dto", dto);
	}
	
	/** 合同价异常监控 */
	@RequestMapping("/contractAmountAbnormal")
	public String contractAmountAbnormal(HttpServletRequest request) {
		return "qs/substd_order_amt/contractAmountAbnormal";
	}
	
	/** 合同价异常监控-列表 */
	@RequestMapping("/contractAmountAbnormalList")
	public String contractAmountAbnormalList(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("contractAmountAbnormal");
		list(dto, request);
		return "qs/substd_order_amt/contractAmountAbnormalList";
	}
	
	/** 合同价异常监控-图表-组织架构维度 */
	@RequestMapping("/contractAmountAbnormalDepGraph")
	public String contractAmountAbnormalDepGraph(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("contractAmountAbnormal");
		graphDep(dto, request);
		return "qs/substd_order_amt/contractAmountAbnormalDepGraph";
	}
	
	/** 合同价异常监控-图表-添加日期维度 */
	@RequestMapping("/contractAmountAbnormalDateGraph")
	public String contractAmountAbnormalDateGraph(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("contractAmountAbnormal");
		graphDate(dto, request);
		return "qs/substd_order_amt/contractAmountAbnormalDateGraph";
	}
	
	/** 订单收款超时监控 */
	@RequestMapping("/collectTimeout")
	public String collectTimeout(HttpServletRequest request) {
		return "qs/substd_order_amt/collectTimeout";
	}
	
	/** 订单收款超时监控-列表 */
	@RequestMapping("/collectTimeoutList")
	public String collectTimeoutList(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("collectTimeout");
		list(dto, request);
		return "qs/substd_order_amt/collectTimeoutList";
	}
	
	/** 订单收款超时监控-图表-组织架构维度 */
	@RequestMapping("/collectTimeoutDepGraph")
	public String collectTimeoutDepGraph(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("collectTimeout");
		graphDep(dto, request);
		return "qs/substd_order_amt/collectTimeoutDepGraph";
	}
	
	/** 订单收款超时监控-图表-添加日期维度 */
	@RequestMapping("/collectTimeoutDateGraph")
	public String collectTimeoutDateGraph(SubstdOrderAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("collectTimeout");
		graphDate(dto, request);
		return "qs/substd_order_amt/collectTimeoutDateGraph";
	}
	
}
