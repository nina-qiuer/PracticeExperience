package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdPurchaseAmtDto;
import com.tuniu.qms.qs.service.SubstdPurchaseAmtService;

@Controller
@RequestMapping("/qs/substdPurchaseAmt")
public class SubstdPurchaseAmtController {
	
	@Autowired
	private SubstdPurchaseAmtService service;
	
	private void list(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		if (null == dto.getAddDate()) {
			dto.setAddDate(DateUtil.getSqlYesterday().toString());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
	}
	
	private void graphDep(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		if (null == dto.getAddDate()) {
			dto.setAddDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataListNum", service.statGraphDepByNum(dto));
		request.setAttribute("dataListTotPrice", service.statGraphDepByTotPrice(dto));
		request.setAttribute("dto", dto);
	}
	
	private void graphDate(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
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
		request.setAttribute("dataListTotPrice", service.statGraphDateByTotPrice(dto));
		request.setAttribute("dto", dto);
	}
	
	/** 采购单添加超时监控 */
	@RequestMapping("/addTimeout")
	public String addTimeout(HttpServletRequest request) {
		return "qs/substd_purchase_amt/addTimeout";
	}
	
	/** 采购单添加超时监控-列表 */
	@RequestMapping("/addTimeoutList")
	public String addTimeoutList(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addTimeout");
		list(dto, request);
		return "qs/substd_purchase_amt/addTimeoutList";
	}
	
	/** 采购单添加超时监控-图表-组织架构维度 */
	@RequestMapping("/addTimeoutDepGraph")
	public String addTimeoutDepGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addTimeout");
		graphDep(dto, request);
		return "qs/substd_purchase_amt/addTimeoutDepGraph";
	}
	
	/** 采购单添加超时监控-图表-添加日期维度 */
	@RequestMapping("/addTimeoutDateGraph")
	public String addTimeoutDateGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addTimeout");
		graphDate(dto, request);
		return "qs/substd_purchase_amt/addTimeoutDateGraph";
	}
	
	/** 跨月添加采购单监控 */
	@RequestMapping("/addDiffMonth")
	public String addDiffMonth(HttpServletRequest request) {
		return "qs/substd_purchase_amt/addDiffMonth";
	}
	
	/** 跨月添加采购单监控-列表 */
	@RequestMapping("/addDiffMonthList")
	public String addDiffMonthList(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addDiffMonth");
		list(dto, request);
		return "qs/substd_purchase_amt/addDiffMonthList";
	}
	
	/** 跨月添加采购单监控-图表-组织架构维度 */
	@RequestMapping("/addDiffMonthDepGraph")
	public String addDiffMonthDepGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addDiffMonth");
		graphDep(dto, request);
		return "qs/substd_purchase_amt/addDiffMonthDepGraph";
	}
	
	/** 跨月添加采购单监控-图表-添加日期维度 */
	@RequestMapping("/addDiffMonthDateGraph")
	public String addDiffMonthDateGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("addDiffMonth");
		graphDate(dto, request);
		return "qs/substd_purchase_amt/addDiffMonthDateGraph";
	}
	
	/** 非现付采购单监控 */
	@RequestMapping("/substdNonpay")
	public String substdNonpay(HttpServletRequest request) {
		return "qs/substd_purchase_amt/substdNonpay";
	}
	
	/** 非现付采购单监控-列表 */
	@RequestMapping("/substdNonpayList")
	public String substdNonpayList(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("substdNonpay");
		list(dto, request);
		return "qs/substd_purchase_amt/substdNonpayList";
	}
	
	/** 非现付采购单监控-图表-组织架构维度 */
	@RequestMapping("/substdNonpayDepGraph")
	public String substdNonpayDepGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("substdNonpay");
		graphDep(dto, request);
		return "qs/substd_purchase_amt/substdNonpayDepGraph";
	}
	
	/** 非现付采购单监控-图表-添加日期维度 */
	@RequestMapping("/substdNonpayDateGraph")
	public String substdNonpayDateGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("substdNonpay");
		graphDate(dto, request);
		return "qs/substd_purchase_amt/substdNonpayDateGraph";
	}
	
	/** 订单负采购监控 */
	@RequestMapping("/negativePurchase")
	public String negativePurchase(HttpServletRequest request) {
		return "qs/substd_purchase_amt/negativePurchase";
	}
	
	/** 订单负采购监控-列表 */
	@RequestMapping("/negativePurchaseList")
	public String negativePurchaseList(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativePurchase");
		list(dto, request);
		return "qs/substd_purchase_amt/negativePurchaseList";
	}
	
	/** 订单负采购监控-图表-组织架构维度 */
	@RequestMapping("/negativePurchaseDepGraph")
	public String negativePurchaseDepGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativePurchase");
		graphDep(dto, request);
		return "qs/substd_purchase_amt/negativePurchaseDepGraph";
	}
	
	/** 订单负采购监控-图表-添加日期维度 */
	@RequestMapping("/negativePurchaseDateGraph")
	public String negativePurchaseDateGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativePurchase");
		graphDate(dto, request);
		return "qs/substd_purchase_amt/negativePurchaseDateGraph";
	}
	
	/** 订单负利润监控 */
	@RequestMapping("/negativeProfit")
	public String negativeProfit(HttpServletRequest request) {
		return "qs/substd_purchase_amt/negativeProfit";
	}
	
	/** 订单负利润监控-列表 */
	@RequestMapping("/negativeProfitList")
	public String negativeProfitList(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativeProfit");
		list(dto, request);
		return "qs/substd_purchase_amt/negativeProfitList";
	}
	
	/** 订单负利润监控-图表-组织架构维度 */
	@RequestMapping("/negativeProfitDepGraph")
	public String negativeProfitDepGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativeProfit");
		graphDep(dto, request);
		return "qs/substd_purchase_amt/negativeProfitDepGraph";
	}
	
	/** 订单负利润监控-图表-添加日期维度 */
	@RequestMapping("/negativeProfitDateGraph")
	public String negativeProfitDateGraph(SubstdPurchaseAmtDto dto, HttpServletRequest request) {
		dto.setStatItem("negativeProfit");
		graphDate(dto, request);
		return "qs/substd_purchase_amt/negativeProfitDateGraph";
	}
	
	
}
