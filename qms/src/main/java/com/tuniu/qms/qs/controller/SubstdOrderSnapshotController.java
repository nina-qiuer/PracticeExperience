package com.tuniu.qms.qs.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dto.SubstdOrderSnapshotDto;
import com.tuniu.qms.qs.service.DestcateStandardService;
import com.tuniu.qms.qs.service.SubstdOrderSnapshotService;

@Controller
@RequestMapping("/qs/substdOrderSnapshot")
public class SubstdOrderSnapshotController {
	
	@Autowired
	private SubstdOrderSnapshotService service;
	
	@Autowired
	private DestcateStandardService stdService;
	
	/** 出团通知超时订单列表 */
	@RequestMapping("/listNoticeTimeout")
	public String listNoticeTimeout(SubstdOrderSnapshotDto dto, HttpServletRequest request) {
		if (null == dto.getStatisticDate()) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		if (null == dto.getDestCate()) {
			dto.setDestCate("出境长线");
		}
		service.loadPage(dto);
		request.setAttribute("destStdList", stdService.getDestStdCache());
		request.setAttribute("dto", dto);
		return "qs/noticeTimeoutOrdList";
	}
	
	/** 出团通知超时率 */
	@RequestMapping("/showNoticeTimeoutRate")
	public String showNoticeTimeoutRate(SubstdOrderSnapshotDto dto, HttpServletRequest request) {
		return "qs/noticeTimeoutRate";
	}
	
	/** 出团通知超时率-组织架构维度 */
	@RequestMapping("/showNoticeTimeoutRate1")
	public String showNoticeTimeoutRate1(SubstdOrderSnapshotDto dto, HttpServletRequest request) {
		if (StringUtils.isBlank(dto.getStatisticDate())) {
			dto.setStatisticDate(DateUtil.getSqlYesterday().toString());
		}
		request.setAttribute("businessUnits", service.getBusinessUnits(dto));
		if (StringUtils.isNotBlank(dto.getBusinessUnit())) {
			request.setAttribute("prdDeps", service.getPrdDeps(dto));
		}
		request.setAttribute("dataList", service.statDepNoticeTimeOutRate(dto));
		request.setAttribute("dto", dto);
		return "qs/noticeTimeoutRate1";
	}
	
	/** 出团通知超时率-统计日期维度 */
	@RequestMapping("/showNoticeTimeoutRate2")
	public String showNoticeTimeoutRate2(SubstdOrderSnapshotDto dto, HttpServletRequest request) {
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
		
		request.setAttribute("dataList", service.statDateNoticeTimeOutRate(dto));
		request.setAttribute("dto", dto);
		return "qs/noticeTimeoutRate2";
	}
	
}
