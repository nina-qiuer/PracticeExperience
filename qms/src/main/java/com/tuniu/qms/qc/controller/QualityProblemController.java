package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.UserExpandDto;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.CopyerUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.util.QcConstant;

@Controller
@RequestMapping("/qc/qualityProblem")
public class QualityProblemController {
	
	private final static Logger logger = LoggerFactory.getLogger(QualityProblemController.class);
	
	@Autowired
	private QualityProblemService service;
	
	@Autowired
	private QualityProblemTypeService qpTservice;
	
	@Autowired
	private InnerRespBillService  innerRespService;
	
	@Autowired
	private OuterRespBillService  outerRespService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InnerPunishBillService  innerPunishService;
	
	@Autowired
	private OuterPunishBillService   outerPunishService;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private TspService tspService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcBillService qcBillService;
	
	@RequestMapping("/list")
	public String list(QualityProblemDto dto, HttpServletRequest request) {
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qc/qualityProblemList";
	}
	
	@RequestMapping("/{id}/toAdd")
	public String toAdd(@PathVariable Integer id, HttpServletRequest request) {
		
		return "qc/qualityProblemTypeForm";
	}
	
	@RequestMapping("/add")
	public String add(QualityProblemType qp, HttpServletRequest request) {
		
		request.setAttribute("info", "Success");
		return "qc/qualityProblemTypeForm";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		return "qc/qualityProblemTypeForm";
	}
	
	@RequestMapping("/update")
	public String update(QualityProblemType qpType, HttpServletRequest request) {
		request.setAttribute("info", "Success");
		return "qc/qualityProblemTypeForm";
	}
	
	/**
	 * 初始化质量问题页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{userFlag}/{qcBillId}/toQuestion")
	public String toQuestion(@PathVariable("qcBillId") Integer qcBillId, @PathVariable("userFlag") Integer userFlag, HttpServletRequest request){
		QualityProblemDto dto =new QualityProblemDto();
		dto.setQcId(qcBillId);
		dto.setFlag(userFlag);
	    List<QualityProblem> list = service.listQp(dto);
	    
		request.setAttribute("qpList", list);
		request.setAttribute("qcBillId", qcBillId);
		request.setAttribute("userFlag", userFlag);
		
		return "qc/questionProblemList";
		
	}
	/**
	 * 初始化质量问题页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcBillId}/toOperQuestion")
	public String toOperQuestion(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request){
		QualityProblemDto dto =new QualityProblemDto();
		dto.setQcId(qcBillId);
		dto.setFlag(Constant.QC_OPERATE);
	    List<QualityProblem> list = service.listQp(dto);
		request.setAttribute("qpList", list);
		request.setAttribute("qcBillId", qcBillId);
		return "qc/qc_operate/questionProblemList";
		
	}
	
	/**
	 * 初始化质量问题页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/{qcBillId}/toInnerQuestion")
	public String toInnerQuestion(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request){
		QualityProblemDto dto =new QualityProblemDto();
		dto.setQcId(qcBillId);
		dto.setFlag(Constant.QC_INNER);
	    List<QualityProblem> list = service.listQp(dto);
		request.setAttribute("qpList", list);
		request.setAttribute("qcBillId", qcBillId);
		return "qc/qc_inner/questionProblemList";
		
	}*/
	
	
	/**
	 * 添加质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{userFlag}/{iqcId}/addQuestion")
	public String addQuestion( HttpServletRequest request, @PathVariable("iqcId") Integer iqcId, @PathVariable("userFlag") Integer userFlag) {
		
		QualityProblem qualityProblem =new QualityProblem();
		qualityProblem.setQcId(iqcId);
		
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(userFlag);
		
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", userFlag);
		
		return "qc/questionProblemForm";
	}
	
	/**
	 * 修改质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{userFlag}/{qpId}/updateQuestion")
	public String updateQuestion(@PathVariable("qpId") Integer qpId, @PathVariable("userFlag") Integer userFlag, HttpServletRequest request) {
		
		QualityProblem qualityProblem =  service.get(qpId);
		
		List<QualityProblemType> list = qpTservice.getQpTypeDataCache(userFlag);
		qualityProblem.setQptName(qpTservice.getQpFullNameById(qualityProblem.getQptId(),list));
		
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(userFlag);
		
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", userFlag);
		
		return "qc/questionProblemForm";
	}
	
	/**
	 * 添加质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{iqcId}/addOperQuestion")
	public String addOperQuestion( HttpServletRequest request,@PathVariable("iqcId") Integer iqcId) {
		
		QualityProblem qualityProblem =new QualityProblem();
		qualityProblem.setQcId(iqcId);
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(Constant.QC_OPERATE);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", Constant.QC_OPERATE);
		return "qc/qc_operate/questionProblemForm";

	}
	
	/**
	 * 修改质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{qpId}/updateOperQuestion")
	public String updateOperQuestion(@PathVariable("qpId") Integer qpId,HttpServletRequest request) {
		
		QualityProblem qualityProblem =  service.get(qpId);
		List<QualityProblemType> list = qpTservice.getQpTypeDataCache(Constant.QC_OPERATE);
		String fullName =  qpTservice.getQpFullNameById(qualityProblem.getQptId(),list);
		qualityProblem.setQptName(fullName);
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(Constant.QC_OPERATE);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", Constant.QC_OPERATE);
		return "qc/qc_operate/questionProblemForm";

	}
	
	/**
	 * 添加内部质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	/*@RequestMapping("/{iqcId}/addInnerQuestion")
	public String addInnerQuestion( HttpServletRequest request,@PathVariable("iqcId") Integer iqcId) {
		
		QualityProblem qualityProblem =new QualityProblem();
		qualityProblem.setQcId(iqcId);
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(Constant.QC_INNER);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", Constant.QC_INNER);
		return "qc/qc_inner/questionProblemForm";

	}*/
	
	/**
	 * 修改内部质量问题初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	/*@RequestMapping("/{qpId}/updateInnerQuestion")
	public String updateInnerQuestion(@PathVariable("qpId") Integer qpId,HttpServletRequest request) {
		
		QualityProblem qualityProblem =  service.get(qpId);
		List<QualityProblemType> list = qpTservice.getQpTypeDataCache(Constant.QC_INNER);
		String fullName =  qpTservice.getQpFullNameById(qualityProblem.getQptId(),list);
		qualityProblem.setQptName(fullName);
		List<QualityProblemType> qpList = qpTservice.getQpTypeByFlag(Constant.QC_INNER);
		request.setAttribute("qpTypeNames", CommonUtil.getQpTypeNames(qpList));
		request.setAttribute("qualityProblem", qualityProblem);
		request.setAttribute("qcFlag", Constant.QC_INNER);
		return "qc/qc_inner/questionProblemForm";
	}*/
	/**
	 * 新增质量问题
	 * @param request
	 * @param qpBill
	 * @return
	 */
	@RequestMapping("/addNewQp")
	@ResponseBody
	public HandlerResult addQualityProblem(HttpServletRequest request, QualityProblem qpBill) {
		
		HandlerResult result = new HandlerResult();
		User user = (User) request.getSession().getAttribute("loginUser");
		qpBill.setAddPerson(user.getRealName());
		qpBill.setUpdatePerson(user.getRealName());
		if(null==qpBill.getVerifyBasis()){
			
			qpBill.setVerifyBasis("");
		}
		
	   try {
			
			service.add(qpBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("保存失败");
		}
		
		return result;
	}

	/**
	 * 更新质量问题
	 * @return
	 */
	@RequestMapping("/updateQp")
	@ResponseBody
	public HandlerResult updateQualityProblem(HttpServletRequest request,QualityProblem qpBill) {
		
		HandlerResult result = new HandlerResult();
		User user = (User) request.getSession().getAttribute("loginUser");
		qpBill.setUpdatePerson(user.getRealName());
		qpBill.setDelFlag(0);
		if(null==qpBill.getLowSatisDegree()){
			
			qpBill.setLowSatisDegree(0);
		}
		try {
			
			service.update(qpBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
		
		return result;
	}
	
	
	/**
	 * 删除某一个质量问题
	 * @param request
	 * @param qpId
	 * @return
	 */
	@RequestMapping("/deleteQp")
	@ResponseBody
	public HandlerResult deleteQualityProblem(HttpServletRequest request,@RequestParam("qpId") Integer qpId) {
		HandlerResult result = new HandlerResult();
		
		try {
			
			service.deleteQp(qpId);
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
	@RequestMapping("/{qpId}/{qcId}/resp")
	public String queryResp(@PathVariable("qpId") Integer qpId, @PathVariable("qcId") Integer qcId ,HttpServletRequest request){
		InnerRespBillDto innerDto =new InnerRespBillDto();
		innerDto.setQpId(qpId);
		List<InnerRespBill> innerList = innerRespService.listInnerResp(innerDto);
		
		OuterRespBillDto outerDto =new OuterRespBillDto();
		outerDto.setQpId(qpId);
		List<OuterRespBill> outerList = outerRespService.listOuterResp(outerDto);
		
		QcBill qcBill = qcBillService.get(qcId);
	    if(qcBill != null){//质检单类型
	    	request.setAttribute("userFlag", qcBill.getGroupFlag());
	    }else{
	    	request.setAttribute("userFlag", Constant.QC_COMPLAINT);
	    }
		
		request.setAttribute("innerList", innerList);
		request.setAttribute("outerList", outerList);
		request.setAttribute("qpId", qpId);
		request.setAttribute("qcId", qcId);
		return "qc/respList";
		
	}
	
	
	/**
	 * 初始化运营责任单页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qpId}/{qcId}/operResp")
	public String operResp(@PathVariable("qpId") Integer qpId, @PathVariable("qcId") Integer qcId ,HttpServletRequest request){
		InnerRespBillDto innerDto =new InnerRespBillDto();
		innerDto.setQpId(qpId);
		List<InnerRespBill> innerList = innerRespService.listInnerResp(innerDto);
		request.setAttribute("innerList", innerList);
		request.setAttribute("qpId", qpId);
		request.setAttribute("qcId", qcId);
		return "qc/qc_operate/respList";
		
	}
	
	/**
	 * 初始化内部质检责任单页面
	 * @param qcBillId
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/{qpId}/{qcId}/innerResp")
	public String innerResp(@PathVariable("qpId") Integer qpId, @PathVariable("qcId") Integer qcId ,HttpServletRequest request){
		InnerRespBillDto innerDto =new InnerRespBillDto();
		innerDto.setQpId(qpId);
		List<InnerRespBill> innerList = innerRespService.listInnerResp(innerDto);
		request.setAttribute("innerList", innerList);
		request.setAttribute("qpId", qpId);
		request.setAttribute("qcId", qcId);
		return "qc/qc_inner/respList";
		
	}*/
	
	/**
	 * 根据用户名字查询用户明细
	 * @param respPersonName
	 * @return
	 */
	@RequestMapping("/getUserDetailSelf")
	@ResponseBody
	public HandlerResult getUserDetailSelf(@RequestParam("respPersonName") String respPersonName) {
		QualityProblemDto qualityProblemDto = new QualityProblemDto();
		
		qualityProblemDto.setPersonName(respPersonName);
		qualityProblemDto.setLevel(0);
		
		return this.getUserDetails(qualityProblemDto);
	}
	
	/**
	 * 根据用户名字查询用户明细  -- 包含一级汇报人
	 * @param request
	 * @param respPersonName
	 * @return
	 */
	@RequestMapping("/getUserDetail")
	@ResponseBody
	public HandlerResult getUserDetail(@RequestParam("respPersonName") String respPersonName) {
		QualityProblemDto qualityProblemDto = new QualityProblemDto();
		
		qualityProblemDto.setPersonName(respPersonName);
		qualityProblemDto.setLevel(1);
		
		return this.getUserDetails(qualityProblemDto);
	}
	
	/**
	 * 根据用户名字查询用户明细 -- 包含二级汇报人(汇报人的汇报人)
	 * @param request
	 * @param personName
	 * @return
	 */
	@RequestMapping("/getUserDetailSecondRep")
	@ResponseBody
	public HandlerResult getUserDetailSecondRep(@RequestParam("personName") String personName) {
		QualityProblemDto qualityProblemDto = new QualityProblemDto();
		
		qualityProblemDto.setPersonName(personName);
		qualityProblemDto.setLevel(2);
		
		return this.getUserDetails(qualityProblemDto);
	}
	
	/**
	 * 根据用户名字查询用户明细
	 * @param queryCondition  personName: 用户名    level：详情等级   0：自身  1：包含一级汇报人  2：包含二级汇报人 
	 * @return
	 */
	@RequestMapping("/getUserDetails")
	@ResponseBody
	public HandlerResult getUserDetails(QualityProblemDto qualityProblemDto) {
		HandlerResult result = new HandlerResult();
		
		try {
			 User user =  userService.getUserByRealName(qualityProblemDto.getPersonName());
			 
			 UserExpandDto userExpandDto = null;
			 if(user == null){
				 
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("用户不存在");
			 }else{
				 user.setDepName(depService.getDepFullNameById(user.getDepId()));
				 
				 userExpandDto = new UserExpandDto();
				 CopyerUtil.copy(user, userExpandDto);//将父类中的值装配到子类
				 
				 logger.info("userName: " + user.getRealName());
				 
				 Integer level = qualityProblemDto.getLevel();
				 if(level > 0){
					 User reportUser = OaClient.getReporter(user.getRealName());//调用接口获取报告人信息
					 
					 logger.info("reportUser: " + reportUser);
					 if(reportUser != null){//一级汇报人不为空
						 userExpandDto.setImpPersonName(reportUser.getRealName());
						 userExpandDto.setImpDepName(depService.getDepFullNameById(reportUser.getDepId() != null ? reportUser.getDepId() : 0));
						 
						 Job impJob = jobService.get(reportUser.getJobId());
						 userExpandDto.setImpPersonJob(impJob != null ? impJob.getName() : "");
						 
						 if(level > 1){//二级汇报人不为空
							 User secondReportUser = OaClient.getReporter(reportUser.getRealName());//调用接口获取报告人信息
							 
							 logger.info("secondReportUser: " + secondReportUser);
							 if(secondReportUser != null){//二级汇报人信息
								 userExpandDto.setSecondImpPersonName(secondReportUser.getRealName());
								 
								 Job job = jobService.get(secondReportUser.getJobId());
								 userExpandDto.setSecondImpPersonJob(job != null ? job.getName() : "");
							 }
						 }
					 }
				 }
				 
				 result.setRetObj(userExpandDto);
				 result.setRetCode(Constant.SYS_SUCCESS);
			 }
		} catch (Exception e) {
			logger.error("查询失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("查询失败");
		}
		
		return result;
	}
	
	/**
	 * 根据供应商名称查询供应商ID
	 * @param request
	 * @param respPersonName
	 * @return
	 */
	@RequestMapping("/getAgency")
	@ResponseBody
	public HandlerResult getAgency(HttpServletRequest request,@RequestParam("agencyName") String agencyName,@RequestParam("qcId") Integer qcId) {
		HandlerResult result = new HandlerResult();
		
		try {
			Map<String, Object> map =  tspService.getAgencyInfo(agencyName);
			
			if(Constant.YES != Integer.parseInt(map.get("flag").toString())){
				result.setRetCode(Constant.SYS_FAILED);
			}else{
				Map<String, Object> impMap = service.getImpPersonByQcId(qcId);
				
				OuterRespBill respBill = new OuterRespBill();
				respBill.setAgencyId(Integer.parseInt(map.get("agencyId").toString()) );
				
				if(null != impMap && !"".equals(impMap) ){
					
					respBill.setImpPersonId(Integer.parseInt(impMap.get("prdManagerId").toString()));
					respBill.setImpPersonName(impMap.get("prdManager").toString());
					
					User improveUser =  userService.getUserByRealName(respBill.getImpPersonName());//改进人
					if(null != improveUser && null != improveUser.getDepId()){
						respBill.setDepName(depService.getDepFullNameById(improveUser.getDepId()));
						
						User appealUser =  OaClient.getReporter(respBill.getImpPersonName());//调用接口获取报告人信息
						if(appealUser != null && appealUser.getRealName() != null){//异议提出人
							respBill.setAppealPersonName(appealUser.getRealName());
						}
					}
				}else{
					respBill.setImpPersonId(0);
					respBill.setImpPersonName("");
					respBill.setDepName("");
				}
				
				result.setRetObj(respBill);
				result.setRetCode(Constant.SYS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("供应商查询失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("查询失败");
		}
		
		return result;
	}
	
	
	/**
	 * 根据供应商名称查询供应商ID
	 * @param request
	 * @param respPersonName
	 * @return
	 */
	@RequestMapping("/getAgencySelf")
	@ResponseBody
	public HandlerResult getAgencySelf(HttpServletRequest request,@RequestParam("agencyName") String agencyName,@RequestParam("qcId") Integer qcId) {
		
		HandlerResult result = new HandlerResult();
		
		try {
			
			
			Map<String, Object> map =  tspService.getAgencyInfo(agencyName);
			
			OuterRespBill respBill = new OuterRespBill();
			if(Constant.YES ==Integer.parseInt(map.get("flag").toString())){
				
				respBill.setAgencyId(Integer.parseInt(map.get("agencyId").toString()) );
				result.setRetObj(respBill);
				result.setRetCode(Constant.SYS_SUCCESS);
			}else{
				
				result.setRetCode(Constant.SYS_FAILED);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("查询失败");
		}
		
		return result;
	}
	
	/**
	 * 获取质量问题类型(所有类型)
	 * @param request
	 * @return
	 */
	@RequestMapping("/getQpType")
	public String getQpType(HttpServletRequest request,@RequestParam("qcFlag") Integer qcFlag) {
		QualityProblemTypeDto dto =	new QualityProblemTypeDto();
		if(qcFlag == Constant.QC_COMPLAINT){//投诉质检
			   
			dto.setCmpQcUse(Constant.QC_USE);
			
		   }else if(qcFlag == Constant.QC_DEVELOP){//研发质检
			   
			dto.setDevQcUse(Constant.QC_USE);
			   
		   }else if(qcFlag == Constant.QC_OPERATE){//运营质检
			   
			 dto.setOperQcUse(Constant.QC_USE);
			 
		   }else if(qcFlag == Constant.QC_INNER){//内部质检
			   
			  dto.setInnerQcUse(Constant.QC_USE);
		   }
		List<QualityProblemType> qpTypeList = qpTservice.list(dto);
		List<QualityProblemType> notypeList  = getNoType();
	    for(int i =0 ;i<qpTypeList.size();i++){
	    	
	    	String name = qpTypeList.get(i).getName();
	    	for(int j = 0;j<notypeList.size();j++){
	    		String noName = notypeList.get(j).getName();
	    		if(name.equals(noName)){
	    			
	    			qpTypeList.remove(i);
	    			i--;
	    		}
	    		
	    	}
	    	
	    }
		
		request.setAttribute("qpTypeList", qpTypeList);
		request.setAttribute("qcFlag", qcFlag);
		return "qc/qptypeForm";

	}
	
	
	public List<QualityProblemType> getNoType(){
		

		List<QualityProblemType> qpList =new ArrayList<QualityProblemType>();
		QualityProblemTypeDto dto =	new QualityProblemTypeDto();
		dto.setCmpQcUse(Constant.QC_USE);
		dto.setName(QcConstant.QP_TYPE);//非质量问题
		List<QualityProblemType> qpTypeList = qpTservice.list(dto);//查询第一级
		QualityProblemType qpType = qpTypeList.get(0);
		qpList.add(qpType);
		QualityProblemTypeDto dto1 =	new QualityProblemTypeDto();
		dto1.setCmpQcUse(Constant.QC_USE);
		dto1.setPid(qpType.getId());
		List<QualityProblemType> list1 = qpTservice.list(dto1);//查询第二级
		for(QualityProblemType qp_type:list1){
			
			qpList.add(qp_type);
			QualityProblemTypeDto dto2 = new QualityProblemTypeDto();
			dto2.setCmpQcUse(Constant.QC_USE);
			dto2.setPid(qp_type.getId());
			List<QualityProblemType> list2 = qpTservice.list(dto2);//查询第三级
			for(QualityProblemType qp_type2:list2){
				
				qpList.add(qp_type2);
				
			}
		}
		return qpList;
	}
	
	/**
	 * 获取非质量问题类型（单指投诉质检非质量问题）
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcFlag}/getNoQpType")
	public String getNoQpType(HttpServletRequest request,@PathVariable("qcFlag") Integer qcFlag) {
		
		List<QualityProblemType> qpList =new ArrayList<QualityProblemType>();
		QualityProblemTypeDto dto =	new QualityProblemTypeDto();
		dto.setCmpQcUse(Constant.QC_USE);
		dto.setName(QcConstant.QP_TYPE);//质量问题
		List<QualityProblemType> qpTypeList = qpTservice.list(dto);//查询第一级
		QualityProblemType qpType = qpTypeList.get(0);
		
		QualityProblemTypeDto dto1 =	new QualityProblemTypeDto();
		dto1.setCmpQcUse(Constant.QC_USE);
		dto1.setPid(qpType.getId());
		List<QualityProblemType> list1 = qpTservice.list(dto1);//查询第二级
		for(QualityProblemType qp_type:list1){
			
			qpList.add(qp_type);
			QualityProblemTypeDto dto2 = new QualityProblemTypeDto();
			dto2.setCmpQcUse(Constant.QC_USE);
			dto2.setPid(qp_type.getId());
			List<QualityProblemType> list2 = qpTservice.list(dto2);//查询第三级
			for(QualityProblemType qp_type2:list2){
				
				qpList.add(qp_type2);
				
			}
		}
		request.setAttribute("qpTypeList", qpList);
		request.setAttribute("qcFlag", qcFlag);
		return "qc/noQpTypeForm";

	}
	
	
	@RequestMapping("/getRealType")
	@ResponseBody
	public HandlerResult getRealType(HttpServletRequest request,@RequestParam("qptName") String qptName) {
		
		HandlerResult result = new HandlerResult();
		
		try {
			
			QualityProblemType qpType =  qpTservice.getQpTypeByFullName(qptName);
			if(qpType!=null){
				
				result.setRetObj(qpType);
				result.setRetCode(Constant.SYS_SUCCESS);
				
			}else{
				result.setResMsg("质量问题类型不正确!");
				result.setRetCode(Constant.SYS_FAILED);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("质量问题类型不正确");
		}
		
		return result;
	}
	
	@RequestMapping("/getFullName")
	@ResponseBody
	public HandlerResult getFullName(HttpServletRequest request,@RequestParam("qptId") Integer qptId,@RequestParam("qcFlag") Integer qcFlag) {
		
		HandlerResult result = new HandlerResult();
		List<QualityProblemType> list = new ArrayList<QualityProblemType>();
		
		try {
			
			  if(qcFlag == Constant.QC_COMPLAINT){//投诉质检
				   
				 list = qpTservice.getQpTypeDataCache(qcFlag);
				
			   }else if(qcFlag == Constant.QC_DEVELOP){//研发质检
				   
				   list = qpTservice.getQpTypeDataCache(qcFlag);
				   
			   }else if(qcFlag == Constant.QC_OPERATE){//运营质检
				   
				   list = qpTservice.getQpTypeDataCache(qcFlag);
			   }else if(qcFlag == Constant.QC_INNER){//内部质检
				   
				   list = qpTservice.getQpTypeDataCache(qcFlag);
			   }
			
			String qpTypeName =  qpTservice.getQpFullNameById(qptId,list);
			if(!"".equals(qpTypeName)){
				
				result.setRetObj(qpTypeName);
				result.setRetCode(Constant.SYS_SUCCESS);
				
			}else{
				result.setResMsg("质量问题类型不正确!");
				result.setRetCode(Constant.SYS_FAILED);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("质量问题类型不正确");
		}
		
		return result;
	}
	
	/**
	 * 初始化处罚单列表
	 */
	@RequestMapping("/{qcBillId}/toPunishBill")
	public String toPunishBill(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request){
		service.deleteInnerAndOuter(qcBillId); // 删除垃圾数据
		
		QcBill qcBill = qcBillService.get(qcBillId);
		int groupFlag =(null != qcBill && qcBill.getGroupFlag() != null) ? qcBill.getGroupFlag() : 1;
		
		List<InnerPunishBill> innerList = innerPunishService.listInnerPunish(qcBillId);
		List<OuterPunishBill> outerList = outerPunishService.listOuterPunish(qcBillId);
		
		request.setAttribute("innerList", innerList);
		request.setAttribute("outerList", outerList);
		request.setAttribute("qcId", qcBillId);
		request.setAttribute("userFlag", groupFlag);
		return "qc/qc_punish/punishList";
	}
	
	
	/**
	 * 初始化处罚单列表
	 */
	@RequestMapping("/{qcBillId}/toOperPunishBill")
	public String toOperPunishBill(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request){
		service.deleteInnerAndOuter(qcBillId); // 删除垃圾数据
		
		List<InnerPunishBill> innerList = innerPunishService.listInnerPunish(qcBillId);
		
		request.setAttribute("innerList", innerList);
		request.setAttribute("qcId", qcBillId);
		return "qc/qc_operate/punishList";
	}

	/**
	 * 初始化处罚单列表
	 */
	/*@RequestMapping("/{qcBillId}/toInnerPunishBill")
	public String toInnerPunishBill(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request){
		service.deleteInnerAndOuter(qcBillId); // 删除垃圾数据
		
		List<InnerPunishBill> innerList = innerPunishService.listInnerPunish(qcBillId);
		
		request.setAttribute("innerList", innerList);
		request.setAttribute("qcId", qcBillId);
		return "qc/qc_inner/punishList";
	}*/
}
