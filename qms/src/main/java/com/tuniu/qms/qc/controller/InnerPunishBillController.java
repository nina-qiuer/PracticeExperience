package com.tuniu.qms.qc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.InnerPunishBasisDto;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.InnerPunishBasisService;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.util.QcConstant;

@Controller
@RequestMapping("/qc/innerPunish")
public class InnerPunishBillController {

	@Autowired
	private InnerPunishBillService innerService;

	@Autowired
	private InnerPunishBasisService basisService;

	@Autowired
	private DepartmentService depService;

	@Autowired
	private JobService jobService;

	@Autowired
	private UserService userService;

	@Autowired
	private QcTypeService qcTypeService;

	/**
	 * 初始化内部处罚单
	 */
	@RequestMapping("/list")
	public String list(InnerPunishBillDto dto, HttpServletRequest request) {
		innerService.loadPage(dto);//查询内部责任单数据
		request.setAttribute("dto", dto);
		
		List<Department> depList = depService.getDepDataCache();
		request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		List<Job> jobList = jobService.getJobDataCache();
		request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		request.setAttribute("qcTypeNames", CommonUtil.getQtNames(qtList));
		
		return "qc/qc_punish/innerPunishBillList";
	}

	@RequestMapping("/{qcId}/{useFlag}/toAdd")
	public String toAdd(@PathVariable("qcId") Integer qcId, @PathVariable("useFlag") Integer useFlag, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("loginUser");
		InnerPunishBill innerPunish = new InnerPunishBill();
		innerPunish.setQcId(qcId);
		innerPunish.setPunishReason("");
		innerPunish.setAddPerson(user.getRealName());
		innerPunish.setDelFlag(Integer.parseInt(QcConstant.DEL_FLAG_TRUE));
		innerPunish.setRelatedFlag(Constant.YES);
		innerService.addPunish(innerPunish);

		innerPunish.setEconomicPunish(0.00);
		innerPunish.setScorePunish(0);
		innerPunish.setRespId(0);
		innerPunish.setRespType(QcConstant.INNER_RESP_PUN_FALG);
		request.setAttribute("innerPunish", innerPunish);

		List<Department> depList = depService.getAllDepDataCache();
		request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		List<User> userList = userService.getAllUserData();
		request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		List<Job> jobList = jobService.getJobDataCache();
		request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		request.setAttribute("useFlag", useFlag);

		return "qc/qc_punish/innerPunishForm";
	}
	
	/**
	 * 修改记分单
	 * 
	 * @param request
	 * @param innerId
	 * @return
	 */
	@RequestMapping("/{innerId}/{useFlag}/updateInner")
	public String updateInner(HttpServletRequest request, @PathVariable("innerId") Integer innerId, @PathVariable("useFlag") Integer useFlag) {

		InnerPunishBill innerPunish = innerService.get(innerId);
		innerPunish.setDepName(depService.getDepFullNameById(innerPunish.getDepId()));
		request.setAttribute("innerPunish", innerPunish);

		List<Department> depList = depService.getDepDataCache();
		request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		List<Job> jobList = jobService.getJobDataCache();
		request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		request.setAttribute("useFlag", useFlag);

		return "qc/qc_punish/innerPunishUpdateForm";
	}

	/**
	 * 更新内部处罚单
	 * 
	 * @param request
	 * @param innerBill
	 * @return
	 */
	@RequestMapping("/updateInnerPunish")
	@ResponseBody
	public HandlerResult updateInnerPunish(HttpServletRequest request, InnerPunishBill innerBill) {
		HandlerResult result = HandlerResult.newInstance();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ipbId", innerBill.getId());
			int basisCount = basisService.getPunishBasisIsExist(map);
			if (basisCount < 1) {
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("请填写处罚依据");
				return result;
			}
			
			User user = (User) request.getSession().getAttribute("loginUser");
			innerService.updateInnerPunish(innerBill, user);
			
			result.setRetCode(Constant.SYS_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());

		}
		return result;
	}

	/**
	 * 
	 * @param innerBill
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/addScore")
	@ResponseBody
	public HandlerResult addScore(HttpServletRequest request, InnerPunishBill innerBill) {
		HandlerResult result = HandlerResult.newInstance();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ipbId", innerBill.getId());
			int basisCount = basisService.getPunishBasisIsExist(map);
			if (basisCount < 1) {
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("请填写处罚依据");
				return result;
			}
			
			User user = (User) request.getSession().getAttribute("loginUser");
			innerBill.setAddPerson(user.getRealName());
			innerBill.setUpdatePerson(user.getRealName());

			QcBill qcBill = qcService.getById(innerBill.getQcId());
			qcBill.setOrdId(innerBill.getOrdId());
			qcBill.setPrdId(innerBill.getPrdId());
			qcBill.setDelFlag(Constant.NO);
			qcBill.setFinishTime(new Date());
			QcType qcType = qcTypeService.getTypeByFullName(innerBill.getQcTypeName());
			if (null != qcType) {
				qcBill.setQcTypeId(qcType.getId());
			}
			qcService.update(qcBill);
			Department dep = depService.getDepByFullName(innerBill.getDepName());
			innerBill.setDepId(dep.getId());
			Integer jobId = jobService.getJobIdByName(innerBill.getJobName());
			innerBill.setJobId(jobId);
			innerBill.setDelFlag(Integer.parseInt(QcConstant.DEL_FLAG_FALSE));
			innerService.update(innerBill);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());

		}
		return result;
	}*/

	/**
	 * 更新内部处罚单
	 * 
	 * @param request
	 * @param innerBill
	 * @return
	 *//*
	@RequestMapping("/updateScore")
	@ResponseBody
	public HandlerResult updateScore(HttpServletRequest request, InnerPunishBill innerBill) {
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		innerBill.setUpdatePerson(user.getRealName());
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ipbId", innerBill.getId());
			int basisCount = basisService.getPunishBasisIsExist(map);
			if (basisCount < 1) {
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("请填写处罚依据");
				return result;
			}
			
			Department dep = depService.getDepByFullName(innerBill.getDepName());
			innerBill.setDepId(dep.getId());
			Integer jobId = jobService.getJobIdByName(innerBill.getJobName());
			innerBill.setJobId(jobId);
			innerService.update(innerBill);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());

		}
		return result;
	}*/

	/**
	 * 查询内部处罚单详情
	 * 
	 * @param request
	 * @param innerId
	 * @return
	 */
	@RequestMapping("/{innerId}/{useFlag}/getDetail")
	public String getDetail(HttpServletRequest request, @PathVariable("innerId") Integer innerId, @PathVariable("useFlag") Integer useFlag) {

		InnerPunishBill punishBill = innerService.get(innerId);
		Integer depId = punishBill.getDepId();
		String fullName = depService.getDepFullNameById(depId);
		punishBill.setDepName(fullName);

		InnerPunishBasisDto dto = new InnerPunishBasisDto();
		dto.setIpbId(innerId);
		List<InnerPunishBasis> basisList = basisService.list(dto);
		request.setAttribute("punishBill", punishBill);
		request.setAttribute("basisList", basisList);
		request.setAttribute("useFlag", useFlag);
		return "qc/qc_punish/innerPunishDetail";

	}

	@RequestMapping("/deleteInnerPunish")
	@ResponseBody
	public HandlerResult deleteInnerPunish(HttpServletRequest request, @RequestParam("innerId") Integer innerId) {
		HandlerResult result = HandlerResult.newInstance();
		try {

			innerService.deletePunish(innerId);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());

		}
		return result;
	}

	/*@RequestMapping("/deletePunishById")
	@ResponseBody
	public HandlerResult deletePunishById(HttpServletRequest request, @RequestParam("innerId") Integer innerId) {
		HandlerResult result = HandlerResult.newInstance();
		try {

			InnerPunishBill bill = innerService.get(innerId);
			QcBill qcBill = qcService.get(bill.getQcId());
			if (null != qcBill) {

				if (qcBill.getGroupFlag() != 2) {

					result.setRetCode(Constant.SYS_FAILED);
					result.setResMsg("不能删除运营质检以外的处罚单");
					return result;
				}

			}
			innerService.detetePunishById(bill);
			result.setRetCode(Constant.SYS_SUCCESS);

		} catch (Exception e) {

			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());

		}
		return result;
	}*/

	/**
	 * 内部处罚单详情
	 * 
	 * @param request
	 * @param qcId
	 * @return
	 */
	@RequestMapping("/{innerId}/getPunishDetail")
	public String getPunishDetail(HttpServletRequest request, @PathVariable("innerId") Integer innerId) {

		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		InnerPunishBill punishBill = innerService.get(innerId);
		Integer qcTypeId = punishBill.getQcTypeId();
		String qtFullName = qcTypeService.getQtFullNameById(qcTypeId, qtList);
		punishBill.setQcTypeName(qtFullName);

		Integer depId = punishBill.getDepId();
		String depFullName = depService.getDepFullNameById(depId);
		punishBill.setDepName(depFullName);

		InnerPunishBasisDto dto = new InnerPunishBasisDto();
		dto.setIpbId(innerId);
		List<InnerPunishBasis> basisList = basisService.list(dto);
		request.setAttribute("punishBill", punishBill);
		request.setAttribute("basisList", basisList);
		return "qc/qc_punish/innerPunishFormDetail";
	}
}
