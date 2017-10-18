package com.tuniu.qms.qc.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.DevFaultBillDto;
import com.tuniu.qms.qc.dto.DevRespBillDto;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.DevFaultBill;
import com.tuniu.qms.qc.model.DevRespBill;
import com.tuniu.qms.qc.model.JiraProject;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.AssignConfigDevService;
import com.tuniu.qms.qc.service.DevFaultBillService;
import com.tuniu.qms.qc.service.DevRespBillService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.util.QcConstant;
/**
 *研发故障单
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/devFault")
public class DevFaultBillController {

	@Autowired
	private DevFaultBillService service;
	
	@Autowired
	private QualityProblemTypeService qpTservice;
	
	@Autowired
	private DevRespBillService respService;

	@Autowired
	private AssignConfigDevService assignService;
	/**
	 * 初始化故障问题页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String queryQuestion(@RequestParam("qcBillId") Integer qcBillId, HttpServletRequest request){
		DevFaultBillDto dto =new DevFaultBillDto();
		dto.setQcId(qcBillId);
	    List<DevFaultBill> list = service.listFault(dto);
		request.setAttribute("devList", list);
		request.setAttribute("qcBillId", qcBillId);
		return "qc/qc_development/devFaultBillList";
		
	}
	
	/**
	 * 添加故障单初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{iqcId}/addDevFault")
	public String addDevFault( HttpServletRequest request,@PathVariable("iqcId") Integer iqcId) {
		
		DevFaultBill dev =new DevFaultBill();
		dev.setQcId(iqcId);
		dev.setUseFlag(QcConstant.USE_FLAG_DEV);
		QualityProblemTypeDto dto = new QualityProblemTypeDto();
		dto.setDevQcUse(Constant.QC_USE);//研发质检
		List<QualityProblemType> qpTypeList = qpTservice.list(dto);
		List<QualityProblemType> qpList = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		List<JiraProject> projectList =  assignService.getProjectFromJira();//到jira系统获取系统名称
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qpTypeList", qpTypeList);
		request.setAttribute("dev", dev);
		request.setAttribute("qcFlag", Constant.QC_DEVELOP);
		request.setAttribute("projectList", projectList);
		return "qc/qc_development/devFaultForm";

	}
	
	
	/**
	 * 添加测试故障单初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{iqcId}/addTestFault")
	public String addTestFault( HttpServletRequest request,@PathVariable("iqcId") Integer iqcId) {
		
		DevFaultBill dev =new DevFaultBill();
		dev.setQcId(iqcId);
		dev.setUseFlag(QcConstant.USE_FLAG_TEST);
		QualityProblemTypeDto dto = new QualityProblemTypeDto();
		dto.setDevQcUse(Constant.QC_USE);//研发质检
		List<QualityProblemType> qpTypeList = qpTservice.list(dto);
		List<QualityProblemType> qpList = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qpTypeList", qpTypeList);
		request.setAttribute("dev", dev);
		request.setAttribute("qcFlag", Constant.QC_DEVELOP);
		return "qc/qc_development/testFaultForm";

	}
	
	
	
	
	/**
	 * 新增故障单
	 * @param request
	 * @param qpBill
	 * @return
	 */
	@RequestMapping("/addNewFault")
	@ResponseBody
	public HandlerResult addNewQp(HttpServletRequest request,DevFaultBill devBill) {
		
		HandlerResult result = new HandlerResult();
		User user = (User) request.getSession().getAttribute("loginUser");
		devBill.setAddPerson(user.getRealName());
		if(devBill.getUseFlag() ==0 ){
			
			if(devBill.getOnLine() == Constant.YES){
				
				devBill.setJiraAddress("");
			}
		}
		if(devBill.getUseFlag() ==1 ){
			
			devBill.setTreMeasures("");
			devBill.setJiraAddress("");
		}
		try {
			
			service.add(devBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("保存失败");
		}
		
		return result;
	}
	
	/**
	 * 更新故障单
	 * @return
	 */
	@RequestMapping("/updateFault")
	@ResponseBody
	public HandlerResult updateFault(HttpServletRequest request,DevFaultBill devBill) {
		
		HandlerResult result = new HandlerResult();
		User user = (User) request.getSession().getAttribute("loginUser");
		devBill.setUpdatePerson(user.getRealName());
		devBill.setDelFlag(0);
		if(devBill.getUseFlag() == 0){
			if(devBill.getOnLine() == Constant.YES){
				
				devBill.setJiraAddress("");
			}
		}
		try {
			
			service.update(devBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
		
		return result;
	}
	
	/**
	 * 修改开发故障单初始化页面
	 * @param request
	 * @param qpId
	 * @return
	 */
	@RequestMapping("/{devId}/updateDevFault")
	public String updateDevFault(@PathVariable("devId") Integer devId,HttpServletRequest request) {
		
		DevFaultBill dev =  service.get(devId);
		List<QualityProblemType> list = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		String fullName =  qpTservice.getQpFullNameById(dev.getQptId(),list);
		dev.setQptName(fullName);
		List<QualityProblemType> qpTypeList = qpTservice.list(new QualityProblemTypeDto());
		List<QualityProblemType> qpList = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		List<JiraProject> projectList =  assignService.getProjectFromJira();//到jira系统获取系统名称
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qpTypeList", qpTypeList);
		request.setAttribute("dev", dev);
		request.setAttribute("qcFlag", Constant.QC_DEVELOP);
		request.setAttribute("projectList", projectList);
		return "qc/qc_development/devFaultForm";

	}
	/**
	 * 修改测试故障单初始化页面
	 * @param request
	 * @param qpId
	 * @return
	 */
	@RequestMapping("/{devId}/updateTestFault")
	public String updateTestFault(@PathVariable("devId") Integer devId,HttpServletRequest request) {
		
		DevFaultBill dev =  service.get(devId);
		List<QualityProblemType> list = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		String fullName =  qpTservice.getQpFullNameById(dev.getQptId(),list);
		dev.setQptName(fullName);
		List<QualityProblemType> qpTypeList = qpTservice.list(new QualityProblemTypeDto());
		List<QualityProblemType> qpList = qpTservice.getQpTypeDataCache(Constant.QC_DEVELOP);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qpTypeList", qpTypeList);
		request.setAttribute("dev", dev);
		request.setAttribute("qcFlag", Constant.QC_DEVELOP);
		return "qc/qc_development/testFaultForm";

	}
	/**
	 * 删除某一个故障单
	 * @param request
	 * @param qpId
	 * @return
	 */
	@RequestMapping("/deleteFault")
	@ResponseBody
	public HandlerResult deleteFault(HttpServletRequest request,@RequestParam("devId") Integer devId) {
		
		HandlerResult result = new HandlerResult();
		
		try {
			
			service.deleteFault(devId);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}
		
		return result;
	}
	
	/**
	 * 初始化责任单页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	@RequestMapping("/devResp")
	public String devResp(@RequestParam("devId") Integer devId, HttpServletRequest request){
		
		DevRespBillDto respDto =new DevRespBillDto();
		respDto.setDevId(devId);
		List<DevRespBill> respList = respService.listResp(respDto);
		request.setAttribute("devId", devId);
		request.setAttribute("respList", respList);
		return "qc/qc_development/devRespList";
		
	}
}
