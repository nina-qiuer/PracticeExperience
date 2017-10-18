package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdContractAppendAmtDto;
import com.tuniu.qms.qs.service.SubstdContractAppendAmtService;

@Controller
@RequestMapping("/qs/substdContractAppendAmt")
public class SubstdContractAppendAmtController {
	
	@Autowired
	private SubstdContractAppendAmtService service;
	
	private void list(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
		if (null == dto.getAddDate()) {
			dto.setAddDate(DateUtil.getSqlYesterday().toString());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
	}
	
	private void graphDep(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
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
	
	private void graphDate(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
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
	
	/** 跨月添加合同增补单监控 */
	@RequestMapping("/addDiffMonth")
	public String addDiffMonth(HttpServletRequest request) {
		return "qs/substd_contract_append_amt/addDiffMonth";
	}
	
	/** 跨月添加合同增补单监控-列表 */
	@RequestMapping("/addDiffMonthList")
	public String addDiffMonthList(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
		list(dto, request);
		return "qs/substd_contract_append_amt/addDiffMonthList";
	}
	
	/** 跨月添加合同增补单监控-图表-组织架构维度 */
	@RequestMapping("/addDiffMonthDepGraph")
	public String addDiffMonthDepGraph(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
		graphDep(dto, request);
		return "qs/substd_contract_append_amt/addDiffMonthDepGraph";
	}
	
	/** 跨月添加合同增补单监控-图表-添加日期维度 */
	@RequestMapping("/addDiffMonthDateGraph")
	public String addDiffMonthDateGraph(SubstdContractAppendAmtDto dto, HttpServletRequest request) {
		graphDate(dto, request);
		return "qs/substd_contract_append_amt/addDiffMonthDateGraph";
	}
	
}
