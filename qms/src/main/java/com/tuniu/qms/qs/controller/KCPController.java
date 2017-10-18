package com.tuniu.qms.qs.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qs.dto.KCPDto;
import com.tuniu.qms.qs.dto.KcpSourceDto;
import com.tuniu.qms.qs.dto.KcpTypeDto;
import com.tuniu.qms.qs.model.KCP;
import com.tuniu.qms.qs.model.KcpSource;
import com.tuniu.qms.qs.model.KcpType;
import com.tuniu.qms.qs.service.KCPService;
import com.tuniu.qms.qs.service.KcpSourceService;
import com.tuniu.qms.qs.service.KcpTypeService;

@Controller
@RequestMapping("/qs/kcp")
public class KCPController {
	
	@Autowired
	private KCPService service;
	
	@Autowired
	private KcpTypeService kcpTypeService;
	
	@Autowired
	private KcpSourceService kcpSourceService;
	
	@RequestMapping("/apply")
	public String apply(KCPDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if (null != user.getRole()) {
			if(!(Constant.ROLE_ADMINISTRATOR == user.getRole().getType())) {
				dto.setAddPersonId(user.getId());
			}
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qs/kcp/kcpApply";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		KCP kcp = new KCP();
		request.setAttribute("kcp", kcp);
		return "qs/kcp/kcpApplyForm";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(KCP kcp, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		kcp.setAddPersonId(user.getId());
		kcp.setAddPerson(user.getRealName());
		service.add(kcp);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		KCP kcp = service.get(id);
		request.setAttribute("kcp", kcp);
		return "qs/kcp/kcpApplyForm";
	}
	
	
	@RequestMapping("/{id}/toShow")
	public String toShow(@PathVariable("id") Integer id, HttpServletRequest request) {
		KCP kcp = service.get(id);
		request.setAttribute("kcp", kcp);
		
		List<KcpType> kcpTypeList = kcpTypeService.list(new KcpTypeDto());
		List<KcpSource> kcpSourceList = kcpSourceService.list(new KcpSourceDto());
		request.setAttribute("kcpTypeList", kcpTypeList);
		request.setAttribute("kcpSourceList", kcpSourceList);
		return "qs/kcp/kcpShowForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(KCP kcp, HttpServletRequest request) {
		service.update(kcp);
		return "Success";
	}
	
	@RequestMapping("/list")
	public String list(KCPDto dto, HttpServletRequest request) {
		List<KcpType> kcpTypeList = kcpTypeService.list(new KcpTypeDto());
		List<KcpSource> kcpSourceList = kcpSourceService.list(new KcpSourceDto());
		request.setAttribute("kcpTypeList", kcpTypeList);
		request.setAttribute("kcpSourceList", kcpSourceList);
		
		if (null == dto.getState()) {
			dto.setState(1);
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qs/kcp/kcpList";
	}
	
	@RequestMapping("/{id}/toAudit")
	public String toAudit(@PathVariable("id") Integer id, HttpServletRequest request) {
		KCP kcp = service.get(id);
		request.setAttribute("kcp", kcp);
		
		List<KcpType> kcpTypeList = kcpTypeService.list(new KcpTypeDto());
		List<KcpSource> kcpSourceList = kcpSourceService.list(new KcpSourceDto());
		request.setAttribute("kcpTypeList", kcpTypeList);
		request.setAttribute("kcpSourceList", kcpSourceList);
		
		return "qs/kcp/kcpAuditForm";
	}
	
	@RequestMapping("/audit")
	@ResponseBody
	public String audit(Integer auditFlag, KCP kcp, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		int state = kcp.getState() + 1;
		if (2 == state) { // 初审操作
			kcp.setAudit1Id(user.getId());
			kcp.setAudit1Name(user.getRealName());
		} else if (3 == state) { // 复审操作
			kcp.setAudit2Id(user.getId());
			kcp.setAudit2Name(user.getRealName());
		}
		
		if (Constant.YES == auditFlag) { // 审核通过
			kcp.setState(state);
			if (3 == state) { // 终审通过才更新审核时间
				kcp.setAuditTime(new Date());
			}
		} else {
			kcp.setState(0);
		}
		service.update(kcp);
		return "Success";
	}
	
	
}
