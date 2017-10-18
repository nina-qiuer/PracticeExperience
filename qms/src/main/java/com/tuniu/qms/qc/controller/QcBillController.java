package com.tuniu.qms.qc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.access.client.PgaClient;
import com.tuniu.qms.common.dto.OperationLogDto;
import com.tuniu.qms.common.dto.TalkConfigDto;
import com.tuniu.qms.common.model.AgencyPayout;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.ComplaintSolution;
import com.tuniu.qms.common.model.FollowUpRecord;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.TalkConfig;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.service.TalkConfigService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.Page;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.dto.QcSameGroupDto;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcNote;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.model.QcSameGroup;
import com.tuniu.qms.qc.model.QcSameGroupBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcNoteService;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.service.RespBillService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.service.CmpImproveService;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;
import com.tuniu.qms.qs.service.QualityCostLedgerService;
import com.tuniu.qms.report.service.QualityProblemReportService;

@Controller
@RequestMapping("/qc/qcBill")
public class QcBillController {
	
	private final static Logger logger = LoggerFactory.getLogger(QcBillController.class);
			
	@Autowired
	private QcBillService service;
	
	@Autowired
	private RespBillService respBillService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private QcNoteService qcNoteService;
	
    @Autowired
    private QualityProblemService qualityProblemService;

    @Autowired
    private InnerPunishBillService innerPunishService;
    
    @Autowired
    private InnerRespBillService innerRespService;
    
    @Autowired
    private OuterPunishBillService outerPunishService;
    
    @Autowired
    private ProductService prdService;
    
	@Autowired
	private DepartmentService  depService;
	
	 @Autowired
	 private OrderBillService orderBillService;
	 
	 @Autowired
	 private QcPointAttachService qcPointAttachService;
	 
	 @Autowired
	 private ComplaintBillService complaintService;
	 
	 @Autowired
	 private QualityCostLedgerService ledgerService;
	 
	 @Autowired
	 private QualityCostAuxAccountService  auxService;
	 
	 @Autowired
	 private JobService  jobService;
	
	 @Autowired
	 private OperationLogService  operLogService;
		
	 @Autowired
	 private QualityProblemReportService  qpReportService;

	  @Autowired
	  private QualityProblemTypeService qptService;
	  
	  @Autowired
	  private TalkConfigService talkService;
	  
	 @Autowired
	 private QcPunishSegmentTaskService qcPunishSegmentTaskService;
	 
	 @Autowired
	 private CmpImproveService cmpImproveService;
	 
	 @Autowired
	 private RespPunishRelationService respPunishRelationService;
	  
	@RequestMapping("/list")
	public String list(QcBillDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if(Constant.ROLE_EMPLOYEE == user.getRole().getType() && dto.getState()!=QcConstant.QC_BILL_STATE_AUDIT){
			dto.setQcPerson(user.getRealName());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qc/qcBillList";
	}

	@RequestMapping("/qcList")
	public String qcList(QcBillDto dto, HttpServletRequest request) {
		dto.setState(QcConstant.QC_BILL_STATE_FINISH);
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qc/qcList";
	}
	
	/**
	 * 已取消 公司损失大于0
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/qcCancelList")
	public String qcCancelList(QcBillDto dto, HttpServletRequest request) {
		dto.setState(QcConstant.QC_BILL_STATE_CANCEL);
		dto.setCmpState(null);
		dto.setCancelFlag(1);
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qc/qcCancelList";
	}
	
	
	/**
	 * 分配质检单
	 * 经理角色可以在待分配和质检中状态下分配;
	 * 普通员工角色可以在质检中状态下分配给其他组员;
	 * @author jiangye
	 */
	@RequestMapping("/assignQcPerson")
	public String assignQcPerson(QcBillDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		service.updateQcPerson(dto.getQcBillIds(), dto.getAssignTo(), user);
		return list(dto,request);
	}
	
	
	/**
	 * 质检单状态返回质检中【从已完成状态返回】
	 * @author jiangye
	 */
	@RequestMapping("{id}/back2Processing")
	public String back2Processing(@PathVariable("id")Integer id ,QcBillDto dto, HttpServletRequest request) {
		//执行返回质检中逻辑
		User user = (User) request.getSession().getAttribute("loginUser");
		service.backToProcessing(id);
		//pushPunishDataToCmp(id);
		deleteQualityCost(id);//删除质量成本
		deleteQpReport(id);//删除质量问题切片
		
		//添加处罚单数据添加切片任务
        qcPunishSegmentTaskService.builTask(id, 2);
		
		operLogService.addQcOperationLog(id, user, "返回质检中", "质检单："+id+"，返回质检中");
		return list(dto,request);
	}

	private void deleteQualityCost(Integer id){
		
		ledgerService.deleteByQcId(id);
		auxService.deleteByQcId(id);
		
	}
	
	private void deleteQpReport(Integer id){
		
		qpReportService.deleteByQcId(id);
		
	}
	
	/**
	 * 获取对缓存中用户进行处理后的用户列表
	 * @return List<Map<String, Object>> 用户列表，map结构为label和realName,label由用户名字的汉字和拼音组成，作为自动补全匹配。 
	 * @author jiangye
	 */
	@RequestMapping("/getUserNamesInJSON")
	@ResponseBody
	public List<Map<String, Object>> getUserNamesInJSON() {
		return CommonUtil.getUserNames(userService.getAllUserData());
	}

	
	@RequestMapping("/getQcUserNames")
	@ResponseBody
	public List<Map<String, Object>> getQcUserNames() {
		
		List<User> list = userService.listQcUser();
		return CommonUtil.getUserNames(list);
	}
	
	/**
	 * 获取对缓存中组织
	 * @return
	 */
	@RequestMapping("/getDepNames")
	@ResponseBody
	public List<String> getDepNames() {
		return CommonUtil.getDepNames(depService.getDepDataCache());
	}
	
	
	/**
	 * 获取缓存岗位
	 */
	@RequestMapping("/getJobNames")
	@ResponseBody
	public List<String> getJobNames() {
		return CommonUtil.getJobNames(jobService.getJobDataCache());
	}
	
	
	@RequestMapping("/{id}/toBill")
    public String toBill(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		User user = (User) request.getSession().getAttribute("loginUser");
		Integer qcNoteCount = getQcNoteCount(id);
		QcBill qcBill = service.get(id);
		request.setAttribute("qcBill", qcBill);
		request.setAttribute("qcNoteCount", qcNoteCount);
		request.setAttribute("qcBillId", id);
		request.setAttribute("userRole", user.getRole().getType());
		return "qc/qcBill";
    }
	
	/**
	 * 质检单信息
	 * @param request
	 * @param qcBillId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/queryList/{qcBillId}")
	public String queryList(@PathVariable("qcBillId") Integer qcBillId,HttpServletRequest request) {

		// 质检单信息
		QcBill qcBill = service.get(qcBillId);
		QcPointAttachDto  dto  = new QcPointAttachDto();
		dto.setQcId(qcBill.getId());
		dto.setBillType(1);
		List<QcPointAttach> attachList = qcPointAttachService.list(dto);
		request.setAttribute("qcBill", qcBill);
		request.setAttribute("attachList", attachList);
		return "qc/report";
	}

	/**
	 * 撤销质检单
	 * @param model
	 * @param qcId
	 * @param remark
	 * @return
	 */
	@RequestMapping("/revokeQcBill")
	@ResponseBody
	public HandlerResult revokeQcBill(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("remark") String remark) {

		User user = (User) request.getSession().getAttribute("loginUser");
		HandlerResult result = HandlerResult.newInstance();
		QcBill qcBill =new QcBill();
		qcBill.setId(qcId);
		qcBill.setRemark(remark);
		qcBill.setState(QcConstant.QC_BILL_STATE_CANCEL);//撤销状态
		qcBill.setUpdatePerson(user.getRealName());
		qcBill.setFinishTime(new Date());
		qcBill.setCancelTime(new Date());
		try {
			
			service.deleteQcBill(qcBill);
			OperationLog log = new OperationLog();
			log.setDealPeople(user.getId());
			log.setDealPeopleName(user.getRealName());
			log.setQcId(qcId);
			log.setDealDepart(user.getRole().getName());
			log.setFlowName("质检已撤销");
			log.setContent("质检单："+qcId+"，已撤销");
			operLogService.add(log);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("撤销失败!");
		}
			
		return result;
	}
	
    /**
     * 更新质检单重要标记
     */
    @RequestMapping("{qcBillId}/updateImportantFlag")
    @ResponseBody
    public String updateImportantFlag(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request) {

        QcBill qcBill = new QcBill();
        qcBill.setId(qcBillId);
        qcBill.setImportantFlag(Integer.valueOf(request.getParameter("importantFlag")));
        service.update(qcBill);

        return "success";
    }

	

	/** 质检-辅助信息页面 */
	@RequestMapping("/{qcBillId}/toAuxiliaryInfo")
	public String toAuxiliaryInfo(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request) {
		
		QcBill qcBill = service.get(qcBillId);
		
		//订单信息
		OrderBill orderBill = orderBillService.get(qcBill.getOrdId()) ;//3955881
		if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
			
			int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
			orderBill.setDepartDay(day+1);
		}
	
		//产品信息
		Product product =  prdService.get(qcBill.getPrdId());  
		
		//投诉信息
		ComplaintBill complaintBill = CmpClient.getComplaintInfo(qcBill.getCmpId());
		double companyLose  = getCompanyLose(complaintBill);  
		complaintBill.setCompanyLose(companyLose);
		
		ComplaintBill cmpBill  = complaintService.get(qcBill.getCmpId());
		if(null!=cmpBill){
			
			complaintBill.setQcFlag(cmpBill.getQcFlag());
		}
		//供应商赔付信息
		List<AgencyPayout> agencyList = CmpClient.getAgencyPayoutInfo(qcBill.getCmpId());//102629
		
		//投诉处理跟进记录
		List<FollowUpRecord> recordList = CmpClient.getRecordInfo(qcBill.getCmpId());
		
		//投诉改进报告
		List<CmpImprove> cmpImproveList = cmpImproveService.listByCmpId(qcBill.getCmpId());
		
		request.setAttribute("qcBillId", qcBillId);
		request.setAttribute("orderBill", orderBill);
		request.setAttribute("product", product);
		request.setAttribute("complaintBill", complaintBill);
		request.setAttribute("agencyList", agencyList);
		request.setAttribute("recordList", recordList);
		request.setAttribute("cmpImproveList", cmpImproveList);
		return "qc/auxiliaryInfo";

	}
	

	private double getCompanyLose(ComplaintBill complaintBill){
		
		BigDecimal b1 = new BigDecimal(complaintBill.getIndemnifyAmount()==null?0:complaintBill.getIndemnifyAmount());
		BigDecimal b2 = new BigDecimal(complaintBill.getClaimAmount()==null?0:complaintBill.getClaimAmount());
		BigDecimal b3 = new BigDecimal(complaintBill.getQualityToolAmount()==null?0:complaintBill.getQualityToolAmount());
		
	   return (b1.subtract(b2).subtract(b3)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
	}
	
	/**
	 * 初始化责任单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryRespBill")
	public String queryRespBill(HttpServletRequest request,HttpServletResponse response){
		 
		 return "qc/respBill";
		
	}

	/**
	 * 
	 * @param request
	 * @param page  取得当前页 这是jqgrid自身的参数
	 * @param rows 每页显示行数 这是jqgrid自身的参数
	 * @param complaintId
	 * @return
	 */
	@RequestMapping(value = "/queryRespList", method = RequestMethod.POST)
	@ResponseBody
	public String queryRespList(HttpServletRequest request,  @RequestParam("page") int page,@RequestParam("rows") int rows,
			@RequestParam("qpId") String qpId,@RequestParam("addTimeStart") String addTimeStart,@RequestParam("addTimeEnd") String addTimeEnd){
		
	      Page pageAll = new Page();
		  Map<String,Object> map =new HashMap<String, Object>();
	      map.put("qpId", qpId);
	      map.put("page", page);//取得当前页
	      map.put("rows", rows);//每页显示行数
	      pageAll = respBillService.queryRespBillList(map);
	      return JSON.toJSONString(pageAll);
		

	}

	/**
	 * 删除责任单(示例)
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteResp", method = RequestMethod.POST)
	@ResponseBody
	public HandlerResult deleteResp(@RequestParam("id") String id) {

		HandlerResult result = new HandlerResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		int flag = respBillService.deleteResp(map);
		if (flag == 0) {

			result.setRetCode(Constant.SYS_SUCCESS);
		} else {
			
			result.setRetCode(Constant.SYS_FAILED);
			result.setRetObj("删除失败");
		}
		return result;
	}

	/**
	 * 查询质检备忘列表
	 * @param request
	 * @param qcBillId
	 * @return
	 * @author jiangye
	 */
	@RequestMapping(value = "/listNote")
	public String queryNote(QcNoteDto dto,HttpServletRequest request) {
		dto.setDataList(qcNoteService.list(dto));
		request.setAttribute("dto", dto);
		return "qc/qcNoteList";
	}

	
	/**
	 * 添加备忘
	 * @author jiangye
	 */
	@RequestMapping(value = "/addNote")
	@ResponseBody
	public QcNote addNote(QcNoteDto dto) {
		QcNote note = new QcNote();
		note.setQcBillId(dto.getQcBillId());
		note.setContent(dto.getNewContent());
		note.setAddPerson(dto.getAddPerson());
		note.setDockingPeople(dto.getDockingPeople());
		note.setDockingDep(dto.getDockingDep());
		note.setAddTime(new Date());
		qcNoteService.add(note);
		return note;
	}
	

	/**
	 * 修改备忘详情
	 * @param id 备忘id
	 * @param newContent 新修改内容
	 * @author jiangye
	 */
	@RequestMapping(value = "/{id}/updateNote")
	@ResponseBody
	public String updateNote(@PathVariable Integer id, String newContent ) {
		QcNote note = new QcNote();
		note.setId(id);
		note.setContent(newContent);
		qcNoteService.update(note);
		return "success";
	}
	
	/**
	 * 删除备忘
	 * @param id 
	 * @author jiangye
	 */
	@RequestMapping(value = "/{id}/deleteNote")
	@ResponseBody
	public String deleteNote(@PathVariable Integer id) {
		qcNoteService.delete(id);
		return "";
	}
	
	/**
	 * 转到添加跟进提醒页面
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toAddRtxRemind")
	public String toAddRtxRemind(Integer qcId,HttpServletRequest request) {
		RtxRemind rtxRemind =new RtxRemind();
		rtxRemind.setTitle("质检单["+qcId+"]跟进提醒");
		request.setAttribute("rtxRemind", rtxRemind);
		return "common/rtxRemindForm";
	}
	
	/**
	 * 获取质检备忘条数
	 * @param id 质检单id
	 * @return 质检备忘条数
	 * @author jiangye
	 */
	private Integer getQcNoteCount(Integer id) {
		QcNoteDto dto = new QcNoteDto();
		dto.setQcBillId(id);
		return qcNoteService.count(dto);
	}
    
	/**
	 * 修改投诉等级初始化
	 * @return
	 */
	@RequestMapping("/toLevel")
	public String toLevel(HttpServletRequest request,@RequestParam("qcId") Integer qcId){
		
		QcBill  qcBill = service.getComplaintLevel(qcId);
		request.setAttribute("qcBill", qcBill);
		return "qc/levelForm";
		
	}	
	
	/**
	 * 修改投诉等级
	 * @return
	 */
	@RequestMapping("/updateLevel")
	@ResponseBody
	public HandlerResult updateLevel(HttpServletRequest request,QcBill qcBill) {
		
		HandlerResult reslut = HandlerResult.newInstance();
		try {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("level",qcBill.getCmpLevel());
			map.put("cmpId", qcBill.getCmpId());
			
		    int flag =	CmpClient.updateCmpLevel(map);
			if(flag == Constant.YES){
				
				service.updateCmpLevel(qcBill);	
				reslut.setRetCode(Constant.SYS_SUCCESS);
				
			}else{
				
				reslut.setRetCode(Constant.SYS_FAILED);
				reslut.setResMsg("调用投诉质检接口失败");
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			reslut.setRetCode(Constant.SYS_FAILED);
			reslut.setResMsg("更新投诉等级失败");
		}
		
		return reslut;
	
	}
	
	/**
	 * 更新备注
	 * @return
	 */
	@RequestMapping("/updateRemark")
	@ResponseBody
	public HandlerResult updateRemark(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("remark") String remark) {
		
		HandlerResult reslut = HandlerResult.newInstance();
		try {

			QcBill bill = new QcBill();
			bill.setId(qcId);
			bill.setRemark(remark);
			service.update(bill);
			reslut.setRetCode(Constant.SYS_SUCCESS);
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
			reslut.setRetCode(Constant.SYS_FAILED);
			reslut.setResMsg("更新备注失败");
		}
		
		return reslut;
	
	}
	
	/**
	 * 更新核实情况
	 * @return
	 */
	@RequestMapping("/updateVerific")
	@ResponseBody
	public HandlerResult updateVerific(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("verification") String verification) {
		
		HandlerResult reslut = HandlerResult.newInstance();
		try {

			QcBill bill = new QcBill();
			bill.setId(qcId);
			bill.setVerification(verification);
			service.update(bill);
			reslut.setRetCode(Constant.SYS_SUCCESS);
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
			reslut.setRetCode(Constant.SYS_FAILED);
			reslut.setResMsg("更新核实情况失败");
		}
		
		return reslut;
	
	}
	
	/**
	 * 查看质检报告 
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/qcReport")
	public  String qcReport(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
		
		
			QcBill qcBill = service.get(qcId);//质检单
			
	        // 订单信息
	        OrderBill orderBill =orderBillService.get(qcBill.getOrdId());
	        if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
				
				int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
				orderBill.setDepartDay(day+1);
			}
	        // 产品信息
	        Product product = prdService.get(qcBill.getPrdId());
	        // 投诉信息
	        ComplaintBill complaintBill = CmpClient.getComplaintInfo(qcBill.getCmpId());
	        
	        OperationLogDto dto = new OperationLogDto();
	        dto.setQcId(qcId);
	        List<OperationLog> operlist = operLogService.list(dto);
	        //质量问题单
	        List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcId,Constant.QC_COMPLAINT);
	        List<QualityProblem> executeQpList = new ArrayList<QualityProblem>();//执行问题
	        List<QualityProblem> supplierQpList = new ArrayList<QualityProblem>();//供应商问题
	        List<QualityProblem> flowsQpList = new ArrayList<QualityProblem>();//流程系统问题
	        List<QualityProblem> improveQpList = new ArrayList<QualityProblem>();//改进问题
	        for(QualityProblem qp : qualityProblemlist) {
	            if(qp.getQptName().startsWith("执行问题")) {
	                executeQpList.add(qp);
	            }else if(qp.getQptName().startsWith("供应商问题")) {
	                supplierQpList.add(qp);
	            }else if(qp.getQptName().startsWith("流程/系统问题")){
	            	flowsQpList.add(qp);
	            }else if(qp.getQptName().startsWith("改进问题")){
	            	improveQpList.add(qp);
	            }
	        }
	        //内部处罚单列表
	        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);//内部处罚单
	        //外部处罚单列表
	        List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(qcId);//外部处罚单
		
			request.setAttribute("qcBill", qcBill);
			request.setAttribute("outerPunishList", outerPunishList);
			request.setAttribute("innerPunishList", innerPunishList);
			request.setAttribute("orderBill", orderBill);
			request.setAttribute("product", product);
			request.setAttribute("complaintBill", complaintBill);
			request.setAttribute("executeQpList", executeQpList);
			request.setAttribute("supplierQpList", supplierQpList);
			request.setAttribute("flowsQpList", flowsQpList);
			request.setAttribute("improveQpList", improveQpList);
			request.setAttribute("dateTime", new Date());
			request.setAttribute("qcPerson", qcBill.getQcPerson());
			request.setAttribute("operlist", operlist);
			request.setAttribute("operlistSeeFlag", true);//是否可以看到操作日志
			return "qc/qcReport";
		
	}
	
	/**
	 * 查看质检报告 cmp系统查看
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{complaintId}/getReport")
	public  String getReport(@PathVariable("complaintId") Integer complaintId,HttpServletRequest request){
		
		QcBillDto dto = new QcBillDto();
	    dto.setCmpId(complaintId);
		QcBill qcBill = service.getQcBill(dto);//质检单
		  // 投诉信息
        ComplaintBill complaintBill = CmpClient.getComplaintInfo(complaintId);
		request.setAttribute("complaintBill", complaintBill);
		if(null!=qcBill){
	        // 订单信息
	        OrderBill orderBill =orderBillService.get(qcBill.getOrdId());
	        if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
				
				int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
				orderBill.setDepartDay(day+1);
			}
	        // 产品信息
	        Product product = prdService.get(qcBill.getPrdId());
	      
	        OperationLogDto oper = new OperationLogDto();
	        oper.setQcId(qcBill.getId());
	        List<OperationLog> operlist = operLogService.list(oper);
	        //质量问题单
	        List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcBill.getId(),Constant.QC_COMPLAINT);
	        List<QualityProblem> executeQpList = new ArrayList<QualityProblem>();//执行问题
	        List<QualityProblem> supplierQpList = new ArrayList<QualityProblem>();//供应商问题
	        List<QualityProblem> flowsQpList = new ArrayList<QualityProblem>();//流程系统问题
	        List<QualityProblem> improveQpList = new ArrayList<QualityProblem>();//改进问题
	        for(QualityProblem qp : qualityProblemlist) {
	            if(qp.getQptName().startsWith("执行问题")) {
	                executeQpList.add(qp);
	            }else if(qp.getQptName().startsWith("供应商问题")) {
	                supplierQpList.add(qp);
	            }else if(qp.getQptName().startsWith("流程/系统问题")){
	            	flowsQpList.add(qp);
	            }else if(qp.getQptName().startsWith("改进问题")){
	            	improveQpList.add(qp);
	            }
	        }
	        //内部处罚单列表
	        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcBill.getId());//内部处罚单
	        //外部处罚单列表
	        List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(qcBill.getId());//外部处罚单
		
			request.setAttribute("qcBill", qcBill);
			request.setAttribute("outerPunishList", outerPunishList);
			request.setAttribute("innerPunishList", innerPunishList);
			request.setAttribute("orderBill", orderBill);
			request.setAttribute("product", product);
			request.setAttribute("executeQpList", executeQpList);
			request.setAttribute("supplierQpList", supplierQpList);
			request.setAttribute("flowsQpList", flowsQpList);
			request.setAttribute("improveQpList", improveQpList);
			request.setAttribute("operlist", operlist);
			request.setAttribute("operlistSeeFlag", false);//是否可以看到操作日志
		}	
		return "qc/qcReport";
	}

	@RequestMapping("/listNormalQcJob")
	public String listNormalQcJob(QcBillDto dto, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if (Constant.ROLE_EMPLOYEE == user.getRole().getType()) {
			dto.setQcPersonId(user.getId()); // 普通员工只能看自己的，经理可以看所有的
		}
		service.listNormalQcJob(dto);
		request.setAttribute("dto", dto);
		return "qc/normalQcJobList";
	}
	
	
	
	/**
	 * 查看质检报告 
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/sendPreviewEmail")
	public  String sendPreviewEmail(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
		
		
			QcBill qcBill = service.get(qcId);//质检单
			
	        // 订单信息
	        OrderBill orderBill =orderBillService.get(qcBill.getOrdId());
	        if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
				
				int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
				orderBill.setDepartDay(day+1);
			}
	        // 产品信息
	        Product product = prdService.get(qcBill.getPrdId());
	        // 投诉信息
	        ComplaintBill complaintBill = CmpClient.getComplaintInfo(qcBill.getCmpId());
	        
	        TalkConfigDto dto =new TalkConfigDto();
	        
	        if(qcBill.getCmpId() > 0){
	        	dto.setType(QcConstant.TALK_TYPE);
	        }else{
	        	dto.setType(QcConstant.TALK_TYPE_NOCMP);
	        }
	        TalkConfig talkConfig  = talkService.getByType(dto);
	        //质量问题单
	        List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcId,Constant.QC_COMPLAINT);
	        List<QualityProblem> executeQpList = new ArrayList<QualityProblem>();//执行问题
	        List<QualityProblem> supplierQpList = new ArrayList<QualityProblem>();//供应商问题
	        List<QualityProblem> flowsQpList = new ArrayList<QualityProblem>();//流程系统问题
	        List<QualityProblem> improveQpList = new ArrayList<QualityProblem>();//改进问题
	        for(QualityProblem qp : qualityProblemlist) {
	            if(qp.getQptName().startsWith("执行问题")) {
	                executeQpList.add(qp);
	            }else if(qp.getQptName().startsWith("供应商问题")) {
	                supplierQpList.add(qp);
	            }else if(qp.getQptName().startsWith("流程/系统问题")){
	            	flowsQpList.add(qp);
	            }else if(qp.getQptName().startsWith("改进问题")){
	            	improveQpList.add(qp);
	            }
	        }
	        //内部处罚单列表
	        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);//内部处罚单
	        //外部处罚单列表
	        List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(qcId);//外部处罚单
		
	        //设置邮件主题
	        StringBuilder emailTitle ;
	        
	        emailTitle = new StringBuilder("[质检报告]");
	       
	        if(QcConstant.NIU_LINE_FLAG.equals(product.getBrandName())){
	        	
	        	  emailTitle.append("[").append(product.getBrandName()).append("]");
	        }
	        //无投诉质检主题
	        if(qcBill.getCmpId() > 0){
	        	 emailTitle.append("[").append(complaintBill.getCmpLevel()).append("级]");
	        }
	       
	        emailTitle.append("[订单号").append(qcBill.getOrdId()).append("]");
	      
	        Integer prdDepId = product.getPrdDepId(); //产品部id.
	        emailTitle.append("[").append(product.getBusinessUnitName()).append("]");//一级部门
	        if(prdDepId != null) {
	            Map<Integer,String>  depManagerMap = OaClient.getUpDirDepManagerByDepId(prdDepId);
	            emailTitle.append("[").append(depManagerMap.get(product.getBusinessUnitId())).append("]"); // 一级部门总经理
	            emailTitle.append("[").append(depManagerMap.get(prdDepId)).append("]"); //二级部门经理
	        }else {
	            emailTitle.append("[]"); // 一级部门总经理
	            emailTitle.append("[]"); //二级部门经理
	        }
	        //无投诉质检主题
	        if(qcBill.getCmpId() > 0){
	        	emailTitle.append("[投诉单号").append(qcBill.getCmpId()).append("]");
	        }
	       
	        User user = (User) request.getSession().getAttribute("loginUser");
	        emailTitle.append("-发送自").append(user.getRealName());
			request.setAttribute("emailTitle", emailTitle.toString());
			request.setAttribute("qcBill", qcBill);
			request.setAttribute("outerPunishList", outerPunishList);
			request.setAttribute("innerPunishList", innerPunishList);
			request.setAttribute("orderBill", orderBill);
			request.setAttribute("product", product);
			request.setAttribute("complaintBill", complaintBill);
			request.setAttribute("executeQpList", executeQpList);
			request.setAttribute("supplierQpList", supplierQpList);
			request.setAttribute("flowsQpList", flowsQpList);
			request.setAttribute("improveQpList", improveQpList);
			request.setAttribute("talkConfig", talkConfig);
			return "qc/qcPreviewEmail";
		
	}
	
	
	
	@RequestMapping(value = "/{id}/sendEmail")
    @ResponseBody
    public HandlerResult sendEmail(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
		try {
				QcBill bill = service.get(id);
				if(bill.getState() == QcConstant.QC_BILL_STATE_FINISH ){
					
					result.setRetCode(Constant.SYS_WARNING);
					result.setResMsg("质检单已完成,不能重复操作!");
				    return result;
					
				}
				
			    if("".equals(bill.getVerification())){
			    	
			    	result.setRetCode(Constant.SYS_FAILED);
					result.setResMsg("核实情况不能为空");
				    return result;
			    }
			   Double count = innerRespService.getRespRate(id);
			   if(null!=count){
				   
				   if(count !=100 &&count!=100.0 && count!=100.00  ){
					   
					    result.setRetCode(Constant.SYS_FAILED);
						result.setResMsg("责任占比总计应为100%!");
					    return result;
				   }
			   }
			    String qcState  = request.getParameter("qcState");
	            String reEmails  = request.getParameter("reEmails");
	            String ccEmails = request.getParameter("ccEmails");
	            String subject = request.getParameter("title");
	            User user = (User) request.getSession().getAttribute("loginUser");
	            if(bill.getState() != QcConstant.QC_BILL_STATE_AUDIT){ //审核中的单子 跳过判断
	            	
	            	int flag = checkImportQcBill(bill, reEmails, ccEmails);
		        	if(flag == 1){
		        		
		        		qcState = "7";//标记重要质检单
		        	}
	            }
			    service.updateCmpQcReportEmail(id, reEmails, ccEmails, subject, user,qcState);
			    result.setRetCode(Constant.SYS_SUCCESS);
			    if(qcState.equals("7")){
			    	
			    	result.setResMsg("该质检单进入审核列表!");
			    }else{
			    	
			    	result.setResMsg("发送成功");
			    }
			    
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("发送邮件失败");
		}
	  
	    
	    return result;
	}
	
    
	/**
	 * 退回质检单
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/returnBill")
    @ResponseBody
    public HandlerResult returnBill(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
	    User user = (User) request.getSession().getAttribute("loginUser");
		try {
				
			service.updateReturnQcBill(id,user);
		    result.setRetCode(Constant.SYS_SUCCESS);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("返回质检中失败");
		}
	    
	    return result;
	}
	 
	
	 private int checkImportQcBill(QcBill qcBill,String reEmails, String ccEmails){
	    	
	    	//收件人、抄送人中包含公司CEO的质检单
	    	if(reEmails.contains(QcConstant.CEO_EMAIL)==true || ccEmails.contains(QcConstant.CEO_EMAIL)==true ){  
	     		
	     		 return 1;
	     	}
	    	
	    	//公司损失金额大于等于2000的质检单（不包含质量工具）
	    	ComplaintBill complaintBill = CmpClient.getComplaintInfo(qcBill.getCmpId());
			double companyLose  = getCompanyLose(complaintBill); 
	    	if(companyLose>=2000){
	    		
	    		return 1;
	    	}
	    	if(null!=complaintBill && null!=complaintBill.getCmpLevel()){
	    		
	    		if(complaintBill.getCmpLevel()==1){
	    			return 1;
	    		}
	    	}
	    	//处罚单内的处罚依据为一级的质检单
	    	List<InnerPunishBill> list = innerPunishService.listInnerPunish(qcBill.getId());
	    	for(InnerPunishBill innerPunish :list){
	    		
	    		for(InnerPunishBasis basis :innerPunish.getIpbList() ){
	    			
	    			if(basis.getPunishStandard().getLevel().contains(QcConstant.STANDARD_LEVEL)){
	    				
	    				return 1;
	    			}
	    			
	    		}
	    		
	    	}
	    	//质量问题类型为红线问题、流程/系统问题的质检单
	    	 QualityProblemDto dto =new QualityProblemDto();
			 dto.setQcId(qcBill.getId());
			 List<QualityProblem> qplist = qualityProblemService.list(dto);
			 List<QualityProblemType> listType = new ArrayList<QualityProblemType>();
		     listType =  qptService.getQpTypeDataCache(Constant.QC_COMPLAINT);
		     for(QualityProblem qp: qplist){
				  
		    	   if(qp.getLowSatisDegree()==0 && qcBill.getCmpId() > 0){// 低满意度点评的单子不进行审核
		    		   
			    	   QualityProblemType qpType = qptService.getQpType(qp.getQptId(), listType);
					   if(qpType!=null){
						   
						   if(qpType.getFullName().startsWith("流程/系统问题") || qpType.getTouchRedFlag() ==1){
							   
							   return 1;
						   }
					   }
		    	   }
		      }
		 
	    	return 0 ;
	    }
	
	@RequestMapping(value = "/listOperLog")
	public String listOperLog(@RequestParam("qcBillId") Integer qcBillId,HttpServletRequest request) {
		
		OperationLogDto log  = new OperationLogDto();
		log.setQcId(qcBillId);
		List<OperationLog> list = operLogService.list(log);
		request.setAttribute("list" ,list);
		return "qc/operationLogList";
	}
	
   /**
    * 查看同团期投诉
    * @param dto
    * @param request
    * @return
    */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listSameGroup")
	public String listSameGroup(QcSameGroupDto dto,HttpServletRequest request) {
		
		QcBill qcBill = service.get(dto.getQcBillId());
		String endDate ="";
		String startDate = DateUtil.formatDate(qcBill.getGroupDate(), "yyyy-MM-dd");
		if(!"".equals(startDate)){
			
			 endDate = DateUtil.formatDate(DateUtil.addSqlDates(qcBill.getGroupDate(), 1), "yyyy-MM-dd");
		}
		Integer routeId = qcBill.getPrdId();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("productId", routeId);
		map.put("beginTime", startDate);
		map.put("endTime", endDate);
		map.put("page", dto.getPageNo());
		map.put("limit", 200);
		
		if(!"".equals(startDate)&&!"".equals(endDate)){
			
			Map<String, Object> resultMap = PgaClient.querySameGroupOrders(map);
			List<Integer> list =  (List<Integer>) resultMap.get("ordList");
			
			if(null != list && list.size()>0){

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderIds", list);
					List<QcSameGroupBill> listOrders = complaintService.queryHasCtOrders(params); // 已被投诉的订单
					Map<String, Object> ordMap =new HashMap<String, Object>();
					ordMap.put("ordList", list);
					List<ComplaintSolution> solutionList = CmpClient.getCmpSolution(ordMap);
					for(ComplaintSolution  solution :solutionList ){
						
							for(QcSameGroupBill bill : listOrders){
								
								if(bill.getCmpId().intValue() == solution.getCmpId().intValue()){
										
									    solution.setQcId(bill.getQcId());
									    solution.setQcPerson(bill.getQcPerson());
									    solution.setQcStateName(bill.getQcStateName());
									}
								}
								
							}
					List<QcSameGroup> listGroup = new ArrayList<QcSameGroup>();
					for(Integer bill :list){
						
						QcSameGroup qBill = new QcSameGroup();
						qBill.setOrderId(bill);
						listGroup.add(qBill);
					}
					for(QcSameGroup qcSameGroup :listGroup){
						
						List<ComplaintSolution> listBill =new ArrayList<ComplaintSolution>();
						for(ComplaintSolution cmpBill : solutionList){
							
							if(qcSameGroup.getOrderId().intValue() == cmpBill.getOrderId().intValue()){
								
								listBill.add(cmpBill);
							}
						}
						
						qcSameGroup.setList(listBill);
					}
					
					Collections.sort(listGroup, new Comparator<QcSameGroup>()
							{
		
								public int compare(QcSameGroup arg0,
										QcSameGroup arg1) {
									
									return arg1.getList().size() - arg0.getList().size();
								}
		
								
							});
					
					dto.setDataList(listGroup);
				}
		}
		request.setAttribute("dto", dto);
		return "qc/qcSameGroupList";
	}
    
	@RequestMapping(value = "{id}/getCopyQcBill")
	public String getCopyQcBill(@PathVariable("id") Integer id,HttpServletRequest request) {
		
		request.setAttribute("id" ,id);
		return "qc/qcCopyForm";
	}
	
	/**
	 * 复制质检单
	 * @param id
	 * @param copyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/copyQcBill")
    @ResponseBody
    public HandlerResult copyQcBill(@RequestParam("id") Integer id,@RequestParam("copyId") Integer copyId, HttpServletRequest request) {
		HandlerResult result = HandlerResult.newInstance();
	    
		User user = (User) request.getSession().getAttribute("loginUser");
		try {
			
			QcBill copyQcBill = service.get(copyId);
			if(null == copyQcBill){
				
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("质检单不存在");
				return result;
			}
			
			service.addCopyQcBill(id, copyQcBill, user);
		    result.setRetCode(Constant.SYS_SUCCESS);
		} catch (Exception e) {
			
			logger.error("一键复制失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("一键复制失败");
		}
	    
	    return result;
	}
	
	/**
	 *保存日均地接成本价
	 * @param id
	 * @param copyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveAvlGroundPrice")
    @ResponseBody
    public HandlerResult saveAvlGroundPrice(@RequestParam("price") Double price,@RequestParam("ordId") Integer ordId, HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
		try {
			
			OrderBill bill = new OrderBill();
			bill.setId(ordId);
			bill.setAvlGroundPrice(price);
			orderBillService.update(bill);
		    result.setRetCode(Constant.SYS_SUCCESS);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("保存失败");
		}
	    
	    return result;
	}
	
	/**
	 * 质检单状态返回质检中【从已撤销返回】
	 * @author chenhaitao
	 */
	@RequestMapping("{id}/backToProcessing")
	public String backToProcessing(@PathVariable("id")Integer id ,QcBillDto dto, HttpServletRequest request) {
		//执行返回质检中逻辑
		User user = (User) request.getSession().getAttribute("loginUser");
		service.backToProcessing(id);
		
		OperationLog log = new OperationLog();
		log.setDealPeople(user.getId());
		log.setDealPeopleName(user.getRealName());
		log.setQcId(id);
		log.setDealDepart(user.getRole().getName());
		log.setFlowName("返回质检中");
		log.setContent("质检单："+id+"，返回质检中");
		operLogService.add(log);
		return qcCancelList(dto,request);
	}
	
	/**
	 * 发起质检
	 * @return
	 */
	@RequestMapping("/addQcBill")
	public String addQcBill(HttpServletRequest request){
		
		return "qc/qcBillForm";
		
	}	
	
	/**
	 * 同步CMP里面的质检单
	 * @param cmpId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveQc")
    @ResponseBody
    public HandlerResult saveQc(@RequestParam("cmpId") Integer cmpId, HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
		try {
			
			 QcBillDto dto = new QcBillDto();
			 dto.setCmpId(cmpId);
		     QcBill bill = service.getQcBill(dto);//质检单
		     if(null!=bill){
		    	 
		    	result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("该投诉单已有质检单");
				return result;
		     }
			 QcBill qcBill = CmpClient.getQcInfo(cmpId);
			 if(null!=qcBill ){
				 
				 java.sql.Date groupDate = DateUtil.parseSqlDate("1970-01-01");
				 if(qcBill.getGroupDate().toString().equals(groupDate.toString())){
						
					 qcBill.setGroupDate(null);
				 }
				 qcBill.setState(QcConstant.QC_BILL_STATE_WAIT);
				 service.add(qcBill);
				 result.setRetCode(Constant.SYS_SUCCESS);
				 
			 }else{
				 result.setRetCode(Constant.SYS_FAILED);
				 result.setResMsg("该投诉单不存在");
				 return result;
			 }
			 
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("发起失败");
		}
	    
	    return result;
	}
	
	@RequestMapping("toImport")
	public String toImport(){
		return "qc/cmpQcImport";
	}
	
	/**
	 * 修改质检等级
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcIds}/toAlterQcLevel")
	public String toAlterQcLevel(@PathVariable("qcIds") String qcIds, HttpServletRequest request){
		
		request.setAttribute("qcIds",qcIds);
		return "qc/alterQcLevel";
	}
	
	/**
	 * 修改质检等级
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alterQcLevel")
	@ResponseBody
	public HandlerResult alterQcLevel(QcBillDto dto, HttpServletRequest request){
		HandlerResult result = new HandlerResult();
		
		try{
			User user = (User) request.getSession().getAttribute("loginUser");
			service.updateQcLevel(dto, user);
			result.setRetCode(Constant.SYS_SUCCESS);
		}catch(Exception e){
			logger.error("修改质检等级失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("修改质检等级失败");
		}
		return result;
	}
	
	/**
	 * 是否有权限修改已有二级的质检单
	 * @param qcIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/alterQcLevelAut")
	@ResponseBody
	public HandlerResult alterQcLevelAut(QcBillDto dto, HttpServletRequest request){
		HandlerResult result = new HandlerResult();
		
		try{
			@SuppressWarnings("unchecked")
			List<String> wcs =  (ArrayList<String>) request.getSession().getAttribute("loginUser_WCS");
			
			if(null != wcs && wcs.contains("QC_ALTER_SECOND_LEVEL")){//是否具有二级修改权限
				result.setRetObj(true);
			}else{
				Integer count = service.getQcLevelsById(dto);
				
				if(null != count && count > 0){//质检等级是否含有2级
					result.setRetObj(false);
				}else{
					result.setRetObj(true);
				}
			}
			
			result.setRetCode(Constant.SYS_SUCCESS);
		}catch(Exception e){
			logger.error("修改质检等级失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("修改质检等级失败");
		}
		return result;
	}
	
	/**
	 * 验证质检单中处罚单关联关系，是否都关联
	 * @param qcBillId
	 * @return
	 */
	@RequestMapping(value = "/checkPunishRelation")
	@ResponseBody
	public HandlerResult checkPunishRelation(@RequestParam("qcBillId") Integer qcBillId){
		HandlerResult result = new HandlerResult();
		
		try{
			boolean flag = respPunishRelationService.checkPunishRelation(qcBillId);
			
			if(flag){
				result.setRetCode(Constant.SYS_SUCCESS);
			}else{
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("质检单中包含未关联的处罚单！");
			}
		}catch(Exception e){
			logger.error("处罚单关联关系验证失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("处罚单关联关系验证失败");
		}
		
		return result;
	}
}
