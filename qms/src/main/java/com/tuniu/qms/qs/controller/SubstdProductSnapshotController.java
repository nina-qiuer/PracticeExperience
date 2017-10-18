package com.tuniu.qms.qs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdProductSnapshotDto;
import com.tuniu.qms.qs.model.DestcateStandard;
import com.tuniu.qms.qs.service.DestcateStandardService;
import com.tuniu.qms.qs.service.SubstdProductSnapshotService;

@Controller
@RequestMapping("/qs/substdProductSnapshot")
public class SubstdProductSnapshotController {
	
	@Autowired
	private SubstdProductSnapshotService service;
	
	@Autowired
	private DestcateStandardService stdService;
	
	private void listSubstdProduct(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (null == dto.getStatisticDate()) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		if (null == dto.getDestCate()) {
			dto.setDestCate("出境长线");
		}
		service.loadPage(dto);
		List<DestcateStandard> destStdList = stdService.getDestStdCache();
		request.setAttribute("destStdList", destStdList);
		request.setAttribute("dto", dto);
	}
	
	/** 销售期长不合格产品列表 */
	@RequestMapping("/listSubstdSpl")
	public String listSubstdSpl(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		dto.setSubstdKey("salesPeriodLength");
		listSubstdProduct(dto, request);
		return "qs/substdSplList";
	}
	
	/** 销售期长不合格问题发生率 */
	@RequestMapping("/showSubstdSplRate")
	public String showSubstdSplRate(HttpServletRequest request) {
		return "qs/substdSplRate";
	}
	
	/** 销售期长不合格问题发生率-组织架构维度 */
	@RequestMapping("/showSubstdSplRate1")
	public String showSubstdSplRate1(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDate())) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDepSplRate(dto));
		request.setAttribute("dto", dto);
		return "qs/substdSplRate1";
	}
	
	/** 销售期长不合格问题发生率-统计日期维度 */
	@RequestMapping("/showSubstdSplRate2")
	public String showSubstdSplRate2(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDateBgn()) && StringUtils.isBlank(dto.getStatisticDateEnd())) {
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			java.sql.Date dateBgn = DateUtil.addSqlDates(yesterday, -7);
			dto.setStatisticDateBgn(dateBgn.toString());
			dto.setStatisticDateEnd(yesterday.toString());
		}
		
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDateSplRate(dto));
		request.setAttribute("dto", dto);
		return "qs/substdSplRate2";
	}
	
	/** 团期丰富度不合格产品列表 */
	@RequestMapping("/listSubstdGr")
	public String listSubstdGr(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		dto.setSubstdKey("groupRichness");
		listSubstdProduct(dto, request);
		return "qs/substdGrList";
	}
	
	/** 团期丰富度不合格问题发生率 */
	@RequestMapping("/showSubstdGrRate")
	public String showSubstdGrRate(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		return "qs/substdGrRate";
	}
	
	/** 团期丰富度不合格问题发生率-组织架构维度 */
	@RequestMapping("/showSubstdGrRate1")
	public String showSubstdGrRate1(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDate())) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDepGrRate(dto));
		request.setAttribute("dto", dto);
		return "qs/substdGrRate1";
	}
	
	/** 团期丰富度不合格问题发生率-统计日期维度 */
	@RequestMapping("/showSubstdGrRate2")
	public String showSubstdGrRate2(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDateBgn()) && StringUtils.isBlank(dto.getStatisticDateEnd())) {
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			java.sql.Date dateBgn = DateUtil.addSqlDates(yesterday, -7);
			dto.setStatisticDateBgn(dateBgn.toString());
			dto.setStatisticDateEnd(yesterday.toString());
		}
		
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDateGrRate(dto));
		request.setAttribute("dto", dto);
		return "qs/substdGrRate2";
	}
	
	/** 非独立成团牛人专线列表 */
	@RequestMapping("/listUnAloneGroup")
	public String listUnAloneGroup(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		dto.setSubstdKey("aloneGroup");
		listSubstdProduct(dto, request);
		return "qs/unAloneGroupList";
	}
	
	/** 非独立成团牛人专线问题发生率 */
	@RequestMapping("/showUnAloneGroupRate")
	public String showUnAloneGroupRate(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		return "qs/unAloneGroupRate";
	}
	
	/** 非独立成团牛人专线问题发生率-组织架构维度 */
	@RequestMapping("/showUnAloneGroupRate1")
	public String showUnAloneGroupRate1(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDate())) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDepUnAgRate(dto));
		request.setAttribute("dto", dto);
		return "qs/unAloneGroupRate1";
	}
	
	/** 非独立成团牛人专线问题发生率-统计日期维度 */
	@RequestMapping("/showUnAloneGroupRate2")
	public String showUnAloneGroupRate2(SubstdProductSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDateBgn()) && StringUtils.isBlank(dto.getStatisticDateEnd())) {
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			java.sql.Date dateBgn = DateUtil.addSqlDates(yesterday, -7);
			dto.setStatisticDateBgn(dateBgn.toString());
			dto.setStatisticDateEnd(yesterday.toString());
		}
		
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDateUnAgRate(dto));
		request.setAttribute("dto", dto);
		return "qs/unAloneGroupRate2";
	}
	
}
