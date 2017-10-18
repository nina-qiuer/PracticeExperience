package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.tuniu.qms.qc.service.QcNoteService;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.util.QcConstant;

/**
 * 内部质检20160225
 * 
 * @author chenhaitao
 * 
 */
@Controller
@RequestMapping("/qc/innerQcBill")
public class InnerQcBillController {

	private final static Logger logger = LoggerFactory.getLogger(InnerQcBillController.class);

	@Autowired
	private InnerQcBillService service;

	@Autowired
	private QcNoteService qcNoteService;

	@Autowired
	private QualityProblemService qualityProblemService;

	@Autowired
	private InnerPunishBillService innerPunishService;

	@Autowired
	private QcPointAttachService qcPointAttachService;

	@Autowired
	private QcTypeService qcTypeService;

	@Autowired
	private OperationLogService operLogService;

	@Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
	
	@Autowired
    private OuterPunishBillService outerPunishService;

	@RequestMapping("/launchList")
	public String launchList(QcBillDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		Integer userFlag = 0;
		if (1 == user.getPositionId()) {
			if (null != user.getRole().getType() && Constant.ROLE_ADMINISTRATOR == user.getRole().getType()) {
				dto.setLaunchFlag(0);
			} else {
				dto.setLaunchFlag(1);
			}
		} else {
			dto.setLaunchFlag(0);
		}
		dto.setState(-1);
		
		List<Integer> states = new ArrayList<Integer>();
		states.add(QcConstant.QC_BILL_STATE_BEGIN);
		states.add(QcConstant.QC_BILL_STATE_WAIT);
		states.add(QcConstant.QC_BILL_STATE_QCBEGIN);
		states.add(QcConstant.QC_BILL_STATE_FINISH);
		dto.setStates(states);

		if (null == user.getRole().getType()) {
			dto.setAddPerson(user.getRealName());
			dto.setCheckFlag(0);

		} else {
			if (Constant.ROLE_ADMINISTRATOR != user.getRole().getType()) {
				if (user.getRole().getName().equals(QcConstant.DEV_MANAGER)) { // 运营质检经理
					userFlag = 1;
					dto.setLaunchFlag(1);
					dto.setReturnFlag(QcConstant.YES);

				} else if (user.getRole().getName().equals(QcConstant.DEV_COMMISSIONER)) {// 运营质检专员

					dto.setQcPerson(user.getRealName());
					dto.setReturnFlag(QcConstant.YES);
					userFlag = 1;
					dto.setLaunchFlag(1);
				} else {

					dto.setAddPerson(user.getRealName());
					dto.setCheckFlag(0);
				}
			}
		}

		List<QcType> qcTypeList = qcTypeService.getQcTypeByName(QcConstant.INNER_QC);

		service.loadPage(dto);
		request.setAttribute("dto", dto);
		request.setAttribute("userFlag", userFlag);
		request.setAttribute("qcTypeList", qcTypeList);
		return "qc/qc_inner/launchList";
	}

	/**
	 * 发起质检初始化
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("loginUser");
		QcBill qcBill = new QcBill();
		qcBill.setAddPerson(user.getRealName());
		qcBill.setAddPersonId(user.getId());
		qcBill.setDelFlag(Constant.YES);
		qcBill.setGroupFlag(Constant.QC_INNER);// 内部质检
		service.add(qcBill);
		
		List<QcType> qcTypeList = qcTypeService.getQcTypeByName(QcConstant.INNER_QC);
		request.setAttribute("qcBill", qcBill);
		request.setAttribute("qcTypeList", qcTypeList);
		return "qc/qc_inner/launchForm";

	}

	/**
	 * 修改质检单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("{id}/toUpdate")
	public String toUpdate(HttpServletRequest request, @PathVariable("id") Integer id) {

		QcBill qcBill = service.get(id);
		List<QcType> qcTypeList = qcTypeService.getQcTypeByName(QcConstant.INNER_QC);
		QcPointAttachDto dto = new QcPointAttachDto();
		dto.setQcId(id);
		dto.setBillType(1);
		
		List<QcPointAttach> attachlist = qcPointAttachService.list(dto);
		request.setAttribute("qcBill", qcBill);
		request.setAttribute("attachlist", attachlist);
		request.setAttribute("qcTypeList", qcTypeList);
		return "qc/qc_inner/launchForm";
	}

	/**
	 * 初始化附件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("{id}/toShowAttach")
	public String toShowAttach(HttpServletRequest request, @PathVariable("id") Integer id) {

		QcPointAttachDto dto = new QcPointAttachDto();
		dto.setQcId(id);
		dto.setBillType(1);
		List<QcPointAttach> attachlist = qcPointAttachService.list(dto);
		request.setAttribute("list", attachlist);
		request.setAttribute("qcId", id);
		return "qc/qc_inner/attachList";
	}

	/**
	 * 保存内部质检
	 * 
	 * @param request
	 * @param qcBill
	 * @return
	 */
	@RequestMapping("/addInnerQc")
	@ResponseBody
	public HandlerResult addInnerQc(HttpServletRequest request, QcBill qcBill) {
		HandlerResult result = new HandlerResult();
		
		try {
			qcBill.setState(QcConstant.QC_BILL_STATE_BEGIN);// 发起中
			qcBill.setDelFlag(Constant.NO);
			
			service.update(qcBill);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("保存失败");
			logger.error("内部质检保存失败", e.getMessage(), e);
		}

		return result;
	}

	/**
	 * 删除质检
	 * 
	 * @param request
	 * @param qcBill
	 * @return
	 */
	@RequestMapping("/deleteQc")
	@ResponseBody
	public HandlerResult deleteQc(HttpServletRequest request, @RequestParam Integer id) {

		HandlerResult result = new HandlerResult();
		try {

			service.delete(id);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			logger.error("内部质检删除失败", e.getMessage(), e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}

		return result;
	}

	/**
	 * 提交内部质检
	 * 
	 * @param request
	 * @param qcBill
	 * @return
	 */
	@RequestMapping("/submitInnerQc")
	@ResponseBody
	public HandlerResult submitInnerQc(HttpServletRequest request, QcBill qcBill) {
		HandlerResult result = new HandlerResult();
		
		try {
			qcBill.setState(QcConstant.QC_BILL_STATE_WAIT);// 待分配
			qcBill.setSubmitTime(new Date());
			qcBill.setReturnFlag(QcConstant.NO);// 清除退回标识
			qcBill.setDelFlag(Constant.NO);
			
			if(null != qcBill.getOrdId() && qcBill.getOrdId() > 0){
				String oldQcPerson = service.getQcPersonByOrderId(qcBill.getOrdId());
				
				if(null != oldQcPerson){
					qcBill.setQcPerson(oldQcPerson);
				}
			}

			service.update(qcBill);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			logger.error("内部质检提交失败", e.getMessage(), e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("提交失败");
		}

		return result;
	}

	/**
	 * 内部质检质检列表
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(QcBillDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if (Constant.ROLE_EMPLOYEE == user.getRole().getType()) {
			dto.setQcPerson(user.getRealName());
		}
		if (dto.getState() == -1) { // 全部

			List<Integer> states = new ArrayList<Integer>();
			states.add(QcConstant.QC_BILL_STATE_WAIT);
			states.add(QcConstant.QC_BILL_STATE_QCBEGIN);
			states.add(QcConstant.QC_BILL_STATE_FINISH);
			dto.setStates(states);
		}
		List<QcType> qcTypeList = qcTypeService.getQcTypeByName(QcConstant.INNER_QC);
		service.loadPage(dto);// 查询内部质检单列表
		request.setAttribute("dto", dto);
		request.setAttribute("qcTypeList", qcTypeList);
		return "qc/qc_inner/qcBillList";
	}

	@RequestMapping("/{id}/toBill")
	public String toBill(@PathVariable("id") Integer id, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("loginUser");
		Integer qcNoteCount = getQcNoteCount(id);
		request.setAttribute("qcNoteCount", qcNoteCount);
		request.setAttribute("qcBillId", id);
		request.setAttribute("userRole", user.getRole().getType());
		return "qc/qc_inner/qcBill";
	}

	/**
	 * 获取质检备忘条数
	 * @param id 质检单id
	 * @return 质检备忘条数
	 * @author
	 */
	private Integer getQcNoteCount(Integer id) {
		QcNoteDto dto = new QcNoteDto();
		dto.setQcBillId(id);
		return qcNoteService.count(dto);
	}

	/**
	 * 分配质检人
	 * @return
	 */
	@RequestMapping("/assignDelPerson")
	@ResponseBody
	public HandlerResult assignDelPerson(HttpServletRequest request, QcBillDto dto) {

		HandlerResult result = new HandlerResult();
		User user = (User) request.getSession().getAttribute("loginUser");
		try {

			service.updateQcPerson(dto.getQcBillIds(), dto.getAssignTo(), user);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("分配失败");
		}

		return result;
	}

	/**
	 * 退回质检单
	 * 
	 * @param model
	 * @param qcId
	 * @param remark
	 * @return
	 */
	@RequestMapping("/returnQcBill")
	@ResponseBody
	public HandlerResult returnQcBill(HttpServletRequest request, @RequestParam("qcId") Integer qcId,
			@RequestParam("remark") String remark) {

		User user = (User) request.getSession().getAttribute("loginUser");
		HandlerResult result = HandlerResult.newInstance();
		QcBill qcBill = service.get(qcId);
		qcBill.setRemark(remark);
		qcBill.setState(QcConstant.QC_BILL_STATE_BEGIN);// 退回到发起中
		qcBill.setUpdatePerson(user.getRealName());
		qcBill.setReturnFlag(QcConstant.YES);// 已退回
		qcBill.setSubmitTime(null);
		try {
			service.returnQcBill(qcBill, user);

			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("退回失败!");
		}

		return result;
	}

	/**
	 * 质检单信息
	 * @param request
	 * @param qcBillId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryList/{qcBillId}")
	public String queryList(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request) {

		// 质检单信息
		QcBill qcBill = service.get(qcBillId);
		QcPointAttachDto dto = new QcPointAttachDto();
		dto.setQcId(qcBill.getId());
		dto.setBillType(1);
		List<QcPointAttach> attachList = qcPointAttachService.list(dto);
		request.setAttribute("attachList", attachList);
		request.setAttribute("qcBill", qcBill);
		return "qc/qc_inner/report";
	}

	/**
	 * 查看质检报告
	 * 
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/qcReport")
	public String qcReport(@PathVariable("qcId") Integer qcId,
			HttpServletRequest request) {

		QcBill qcBill = service.get(qcId);// 质检单

		// 日志信息
		OperationLogDto dto = new OperationLogDto();
		dto.setQcId(qcId);
		List<OperationLog> operlist = operLogService.list(dto);
		// 质量问题单
		List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcId, Constant.QC_INNER);

		// 内部处罚单列表
		List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);// 内部处罚单

		// 附件信息
		QcPointAttachDto qcPointAttachDto = new QcPointAttachDto();
		qcPointAttachDto.setQcId(qcId);
		qcPointAttachDto.setBillType(1);
		List<QcPointAttach> attachList = qcPointAttachService.list(qcPointAttachDto);

		request.setAttribute("qcBill", qcBill);
		request.setAttribute("innerPunishList", innerPunishList);
		request.setAttribute("qualityProblemlist", qualityProblemlist);
		request.setAttribute("dateTime", new Date());
		request.setAttribute("qcPerson", qcBill.getQcPerson());
		request.setAttribute("operlist", operlist);
		request.setAttribute("attachList", attachList);
		return "qc/qc_inner/qcReport";
	}

	/**
	 * 查看质检报告
	 * 
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/sendPreviewEmail")
	public String sendPreviewEmail(@PathVariable("qcId") Integer qcId,
			HttpServletRequest request) {

		QcBill qcBill = service.get(qcId);// 质检单
		request.setAttribute("qcBill", qcBill);

		// 质量问题单
		List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcId, Constant.QC_INNER);
		request.setAttribute("qualityProblemlist", qualityProblemlist);
		
		// 内部处罚单列表
		List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);// 内部处罚单
		request.setAttribute("innerPunishList", innerPunishList);
		
		List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(qcId);//外部处罚单
		request.setAttribute("outerPunishList", outerPunishList);

		// 设置邮件主题
		StringBuilder emailTitle = new StringBuilder("[内部质检报告]");
		emailTitle.append("[ID:").append(qcBill.getId()).append("]");
		emailTitle.append("[订单号:").append(qcBill.getOrdId()).append("]");
		emailTitle.append("[产品编号:").append(qcBill.getPrdId()).append("]");
		User user = (User) request.getSession().getAttribute("loginUser");
		emailTitle.append("-发送自").append(user.getRealName());
		request.setAttribute("emailTitle", emailTitle.toString());
		
		return "qc/qc_inner/qcPreviewEmail";
	}

	@RequestMapping(value = "/{id}/sendEmail")
	@ResponseBody
	public HandlerResult sendEmail(@PathVariable("id") Integer id,HttpServletRequest request) {
		HandlerResult result = HandlerResult.newInstance();
		try {
			QcBill bill = service.get(id);
			if (bill.getState() == QcConstant.QC_BILL_STATE_FINISH) {
				result.setRetCode(Constant.SYS_WARNING);
				result.setResMsg("质检单已完成,不能重复操作!");
				return result;
			}

			if ("".equals(bill.getVerification())) {
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("核实情况不能为空");
				return result;
			}

			bill.setSubject(request.getParameter("title"));
			bill.setReAddrs(request.getParameter("reEmails"));
			bill.setCcAddrs(request.getParameter("ccEmails"));

			User user = (User) request.getSession().getAttribute("loginUser");

			service.updateInnerQcReportEmail(bill, user);

			result.setRetCode(Constant.SYS_SUCCESS);
			result.setResMsg("发送成功");
		} catch (Exception e) {
			logger.error("邮件发送失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("发送邮件失败");
		}

		return result;
	}

	/**
	 * 质检单状态返回质检中【从已完成状态返回】
	 * 
	 * @author jiangye
	 */
	@RequestMapping("{id}/back2Processing")
	public String back2Processing(@PathVariable("id") Integer id, QcBillDto dto, HttpServletRequest request) {
		// 执行返回质检中逻辑
		User user = (User) request.getSession().getAttribute("loginUser");
		service.back2Processing(id);
		OperationLog log = new OperationLog();
		log.setDealPeople(user.getId());
		log.setDealPeopleName(user.getRealName());
		log.setQcId(id);
		log.setDealDepart(user.getRole().getName());
		log.setFlowName("返回质检中");
		log.setContent("质检单：" + id + "，返回质检中");
		operLogService.add(log);

		// 添加处罚单数据添加切片任务
		qcPunishSegmentTaskService.builTask(id, 2);
		return list(dto, request);
	}

	/**
	 * 内部质检申请导入
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("toImport")
	public String toImport(HttpServletRequest request) {

		return "qc/qc_inner/innerQcImport";
	}
}
