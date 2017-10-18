package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.OperationLogDto;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcMonitorDto;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerQcBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcMonitorRelationService;
import com.tuniu.qms.qc.service.QcMonitorService;
import com.tuniu.qms.qc.service.QcNoteService;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.util.QcConstant;

/**
 * 质检监控报表
 * 
 * @author zhangxian2
 * 
 */
@Controller
@RequestMapping("/qc/qcMonitor")
public class QcMonitorController {

	private final static Logger logger = LoggerFactory.getLogger(QcMonitorController.class);

	
	@Autowired
	private QcMonitorService qcMonitorService;
	
	@Autowired
	private QcMonitorRelationService qcMonitorRelationService;

	/**
	 * 删除质检监控
	 * 
	 * @param request
	 * @param qcBill
	 * @return
	 */
	@RequestMapping("/deleteQcMonitor")
	@ResponseBody
	@Transactional
	public HandlerResult deleteQcMonitor(HttpServletRequest request, @RequestParam Integer id) {

		HandlerResult result = new HandlerResult();
		
		try {
		    qcMonitorRelationService.deleteByMonitorId(id);
		    qcMonitorService.delete(id);
			result.setRetCode(Constant.SYS_SUCCESS);
		} catch (Exception e) {

			logger.error("质检监测删除失败", e.getMessage(), e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}

		return result;
	}

	/**
	 * 质检监测列表
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(QcMonitorDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		
		if (Constant.ROLE_EMPLOYEE == user.getRole().getType()) {
			dto.setQcPerson(user.getRealName());
		}

		qcMonitorService.loadPage(dto);// 查询质检监测单列表
		request.setAttribute("dto", dto);
		return "qc/qc_monitor/qcMonitorList";
	}

	/**
	 * 质检监测申请导入
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("toImport")
	public String toImport(HttpServletRequest request) {
		return "qc/qc_monitor/qcMonitorImport";
	}
}
