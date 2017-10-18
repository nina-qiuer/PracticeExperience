package com.tuniu.qms.qc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CellDataMapperHandler;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.POIUtil;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.model.DevFaultBill;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.model.JiraProject;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcBillRelation;
import com.tuniu.qms.qc.model.QcInfluenceSystem;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.model.ResDevExportBill;
import com.tuniu.qms.qc.service.AssignConfigDevService;
import com.tuniu.qms.qc.service.DevFaultBillService;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.JiraRelationService;
import com.tuniu.qms.qc.service.QcBillRelationService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcNoteService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.service.ResDevQcBillService;
import com.tuniu.qms.qc.util.FaultSourceEnum;
import com.tuniu.qms.qc.util.QcConstant;


/**
 * 研发质检
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/resDevQcBill")
public class ResDevQcBillController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ResDevQcBillService devBillService;
    
    @Autowired 
    private QcNoteService qcNoteService;
    
    @Autowired
    private InnerPunishBillService  innerPunishService;
    
	@Autowired
	private ComplaintBillService complaintService;
	 
    @Autowired
    private JiraRelationService jiraService;
    
    @Autowired
    private QcBillService qcBillService;
    
    @Autowired
    private DepartmentService  depService;
    
    @Autowired
    private AssignConfigDevService assignService;
    
    @Autowired
    private QcTypeService qcTypeService;
    
	@Autowired
    private DevFaultBillService devFaultService;
    
	 @Autowired
	 private MailTaskService mailTaskService;
	 
	@Autowired
	private QualityProblemTypeService qptService;

	@Autowired
	private QcBillRelationService relationService;
	
	@Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
	
    private static String serverURL = Config.get("qms.url");
    
	private final static Logger logger = LoggerFactory.getLogger(ResDevQcBillController.class);

    /**
     * 初始化研发质检列表
     * @param dto
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(QcBillDto dto,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("loginUser");
        if(Constant.ROLE_EMPLOYEE == user.getRole().getType()){
            dto.setQcPerson(user.getRealName());
        }
        devBillService.loadPage(dto);// 查询研发质检单列表
        request.setAttribute("dto", dto);
		request.setAttribute("userRealName", user.getRealName());
        return "qc/qc_development/qcBillList";
    }
    
    /**
     * 导出个数限制
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/checkExport")
    @ResponseBody
    public HandlerResult checkExport(QcBillDto dto,HttpServletRequest request) {
        
        HandlerResult result = new HandlerResult();
      
        try {
            
        	Integer count = devBillService.exportCount(dto);
        	if(count<=5000){
        		
        		  result.setRetCode(Constant.SYS_SUCCESS);
        		  
        	}else{
        		
        		 result.setRetCode(Constant.SYS_FAILED);
                 result.setResMsg("导出数量不能超过五千条");
        	}
          
            
        } catch (Exception e) {
            
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("校验导出数量失败");
        }
        
        return result;
    }
    
    
    @RequestMapping("/exports")
    public String exports(QcBillDto dto,HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("loginUser");
        if(Constant.ROLE_EMPLOYEE == user.getRole().getType()){
            dto.setQcPerson(user.getRealName());
        }
      
        List<ResDevExportBill> qcList = devBillService.exportList(dto);
        
    	List<QualityProblemType> list = qptService.getQpTypeDataCache(Constant.QC_DEVELOP);
        for(ResDevExportBill qcBill : qcList){
        	
    	   if(null!= qcBill.getDepId()){
    		   
    		   String depName =depService.getDepFullNameById(qcBill.getDepId());
    		   qcBill.setDepName(depName);
    	   }
    	   if(null!=qcBill.getQptId()){
    		   
    	     String fullName =  qptService.getQpFullNameById(qcBill.getQptId(),list);
       		 qcBill.setQptName(fullName);
    	   }
    		
        	qcBill.setWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
        	qcBill.setMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
        	//（S级加权25、A级加权25、B级加权5，C级1、非研发故障0） * 影响时长）
        	if(QcConstant.QUALITY_PROBLEM_S.equals(qcBill.getQualityEventClass())){//S级
        		
        		qcBill.setFailureScore(25*qcBill.getInfluenceTime());
        		
        	}
			if(QcConstant.QUALITY_PROBLEM_A.equals(qcBill.getQualityEventClass())){//A级
			        		
				qcBill.setFailureScore(25*qcBill.getInfluenceTime());
			}
			if(QcConstant.QUALITY_PROBLEM_B.equals(qcBill.getQualityEventClass())){//B级
				
				qcBill.setFailureScore(5*qcBill.getInfluenceTime());
			}
			if(QcConstant.QUALITY_PROBLEM_C.equals(qcBill.getQualityEventClass())){//C级
        		
				qcBill.setFailureScore(1*qcBill.getInfluenceTime());
        	}
			if(QcConstant.NO_QUALITY_PROBLEM.equals(qcBill.getQualityEventClass())){//非研发故障
        		
				qcBill.setFailureScore(0);
        	}
        }
       //导出数据
        exportData(qcList,response);
        request.setAttribute("dto", dto);
        return null;
    }
    
    private void  exportData( List<ResDevExportBill> qcList,HttpServletResponse response){
    	
    		 
         	try {
         		
                 String[] headers = new String[]{"周数","质检单号", "质检人", "故障来源", "质检事宜概述", "故障级别", "状态", "影响时长(分钟)",
                 		"影响系统","影响结果","故障类型","责任人","责任部门","原因分析","责任系统","改进措施","月份","故障得分"};
                 String[] exportsFields = new String[]{"week","qcId", "qcPerson", "faultSource", "qcAffairSummary", "qualityEventClass",
                 		"state","influenceTime","influenceSystem","influenceResult","qptName","respPersonName","depName","causeAnalysis",
                 		"respSystem","impMeasures","month","failureScore"};
                 
                 String fileName = "list"+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
                 new POIUtil<ResDevExportBill>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields),fileName, qcList,
                 	     new CellDataMapperHandler() {
 							@Override
 							public String mapToCell(String fieldName,
 									Object value) {
 								return value==null?"":value+"";
 							}},
 							response);
             } catch(Exception e) {
             	
             	logger.error(e.getMessage(), e);
             }
    	
    }
    
    
    
	@RequestMapping("/devList")
	public String devList(QcBillDto dto, HttpServletRequest request) {
		dto.setState(QcConstant.QC_BILL_STATE_FINISH);
		devBillService.loadQueryPage(dto);
		request.setAttribute("dto", dto);
		return "qc/qc_development/devList";
	}
    
    
    /**
     * 发起质检初始化
     * @param request
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request){
    	request.setAttribute("falutSourceList", FaultSourceEnum.getSourceList());
    
        return "qc/qc_development/qcBillForm";
    }
    
    
    /**
     * 新增研发质检
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/addResDev")
    @ResponseBody
    public HandlerResult addResDev(HttpServletRequest request,QcBill qcBill) {
        
        HandlerResult result = new HandlerResult();
        User user = (User) request.getSession().getAttribute("loginUser");
        qcBill.setAddPerson(user.getRealName());
        qcBill.setAddPersonId(user.getId());
        qcBill.setQcPersonId(user.getId());
        qcBill.setQcPerson(user.getRealName());
        qcBill.setGroupFlag(Constant.QC_DEVELOP);//研发质检
        qcBill.setState(QcConstant.QC_BILL_STATE_QCBEGIN);//质检中
        qcBill.setVerification("");
      
        try {
            
            QcType qcType = qcTypeService.getTypeName(QcConstant.DEV_TYPE);
        	if(null!= qcType){
        		
        		  qcBill.setQcTypeId(qcType.getId());
        	}
            devBillService.add(qcBill);
            result.setRetCode(Constant.SYS_SUCCESS);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("保存失败");
        }
        
        return result;
    }
    

    /**
     * 新增研发质检-投诉质检
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/addDevRelation")
    @ResponseBody
    public HandlerResult addDevRelation(HttpServletRequest request,QcBill qcBill) {
        
        HandlerResult result = new HandlerResult();
        User user = (User) request.getSession().getAttribute("loginUser");
        qcBill.setAddPerson(user.getRealName());
        qcBill.setAddPersonId(user.getId());
        qcBill.setQcPersonId(user.getId());
        qcBill.setQcPerson(user.getRealName());
        qcBill.setGroupFlag(Constant.QC_DEVELOP);//研发质检
        qcBill.setState(QcConstant.QC_BILL_STATE_QCBEGIN);//质检中
        qcBill.setVerification("");
      
        try {
            
            QcType qcType = qcTypeService.getTypeName(QcConstant.DEV_TYPE);
        	if(null!= qcType){
        		
        		  qcBill.setQcTypeId(qcType.getId());
        	}
            devBillService.addRelationDev(qcBill);
            result.setRetCode(Constant.SYS_SUCCESS);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("保存失败");
        }
        
        return result;
    }

    /**
     * 初始化质检报告
     * @param request
     * @param qcBillId
     * @param orderId
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/{id}/toBill")
    public String toBill(@PathVariable("id") Integer id, HttpServletRequest request) {
        List<String>  loginUser_WCS =  (List<String>)request.getSession().getAttribute("loginUser_WCS");
        Integer qcNoteCount = getQcNoteCount(id);
        request.setAttribute("qcNoteCount", qcNoteCount);
        request.setAttribute("qcBillId", id);
        request.setAttribute("loginUser_WCS", loginUser_WCS);
        return "qc/qc_development/qcDevBill";
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
        List<Department> depList = depService.getDepDataCache();
        request.setAttribute("depNames", CommonUtil.getDepNames(depList));
        List<User> userList = userService.getUserDataCache();
        request.setAttribute("userNames", CommonUtil.getUserNames(userList));
        request.setAttribute("dto", dto);
        return "qc/qc_development/devNoteList";
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
     * 质检单信息
     * @param request
     * @param qcBillId
     * @param orderId
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/devReport")
    public String devReport(HttpServletRequest request ,@RequestParam("qcBillId") Integer qcBillId) {

        List<String>  loginUser_WCS =  (List<String>)request.getSession().getAttribute("loginUser_WCS");
        // 质检单信息
        QcBill qcBill = devBillService.get(qcBillId);
        List<User> userList = userService.getUserDataCache();
        
        //构建邮件标题  【研发质检报告】[A级][质检单号XXXX][一级部门][二级部门][研发总监姓名]-发送自石小筱
        //设置邮件主题
        StringBuilder emailTitle = new StringBuilder("[研发质检报告]");
        if(qcBill.getQualityEventClass().equals(QcConstant.NO_QUALITY_PROBLEM)){
        	
            emailTitle.append("[").append(qcBill.getQualityEventClass()).append("]");
            
        }else{
        	
           emailTitle.append("[").append(qcBill.getQualityEventClass()).append("级]");
        }
        emailTitle.append("[").append(DateUtil.formatAsDefaultDate(qcBill.getAddTime())).append("]");
        emailTitle.append("[ID:").append(qcBill.getId()).append("]");
        emailTitle.append("[").append("责任系统").append("]");//责任系统
        emailTitle.append("[").append("责任部门").append("]");//责任部门
        emailTitle.append("[").append(qcBill.getQcAffairSummary()).append("]");//质检事宜概述
        User user = (User) request.getSession().getAttribute("loginUser");
        emailTitle.append("-发送自").append(user.getRealName());
        
        request.setAttribute("userNames", CommonUtil.getUserNames(userList));
        request.setAttribute("qcBill", qcBill);
        request.setAttribute("loginUser_WCS", loginUser_WCS);
        request.setAttribute("emailTitle", emailTitle);
        return "qc/qc_development/devReport";
    }

    
    /**
     * 辅助信息页面
     * @param qcBillId
     * @param request
     * @return
     */
    @RequestMapping("/toAuxiliaryInfo")
    public String toAuxiliaryInfo(@RequestParam("qcBillId") Integer qcBillId,HttpServletRequest request) {
        
        List<ComplaintBill> cmpList =new ArrayList<ComplaintBill>();
    	QcBillRelationDto qcDto = new QcBillRelationDto();
    	qcDto.setDevId(String.valueOf(qcBillId));
        List<QcBillRelation> relationList = relationService.listByDevId(qcDto);
        for(QcBillRelation bill : relationList){
        	
        	ComplaintBill complaintBill = CmpClient.getComplaintInfo(bill.getCmpId());
        	double companyLose  = getCompanyLose(complaintBill);  
    		complaintBill.setCompanyLose(companyLose);
    		ComplaintBill cmpBill  = complaintService.get(bill.getCmpId());
    		if(null!=cmpBill){
    			
    			complaintBill.setQcFlag(cmpBill.getQcFlag());
    		}
        	cmpList.add(complaintBill);
        	
        }
    	
        JiraBillDto dto =new JiraBillDto();
        dto.setQcId(qcBillId);
        List<JiraBill> list  = jiraService.listByQcId(dto);//获取质检单关联的jira单
        request.setAttribute("list", list);
        request.setAttribute("cmpList", cmpList);
        request.setAttribute("qcBillId", qcBillId);
        return "qc/qc_development/devAuxiliaryInfo";

    }
    
    /**
     * 初始化处罚单列表
     * @param qpId
     * @param request
     * @return
     */
    @RequestMapping("/toPunishBill")
    public String toPunishBill(@RequestParam("qcBillId") Integer qcBillId, HttpServletRequest request){
        try {
            List<InnerPunishBill> innerList = innerPunishService.listInnerPunish(qcBillId);//处罚单
//          //删除垃圾数据------begin---------------
            
            devBillService.deleteInner(qcBillId);
            
//          //---------------end-----------
            request.setAttribute("punishList", innerList);
            request.setAttribute("qcId", qcBillId);
        } catch (Exception e) {
            
        }
    
        return "qc/qc_development/devPunishList";
        
    }
    
    /**
     * 撤销质检单
     * @param model
     * @param qcId
     * @param remark
     * @return
     */
    @RequestMapping("/revokeDevBill")
    @ResponseBody
    public HandlerResult revokeQcBill(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("remark") String remark) {

        User user = (User) request.getSession().getAttribute("loginUser");
        HandlerResult result = HandlerResult.newInstance();
        QcBill qcBill =new QcBill();
        qcBill.setId(qcId);
        qcBill.setRemark(remark);
        qcBill.setState(QcConstant.QC_BILL_STATE_CANCEL);//撤销状态
    	qcBill.setFinishTime(new Date());
        qcBill.setUpdatePerson(user.getRealName());
        qcBill.setCancelTime(new Date());
        try {
            
            devBillService.deleteDevBill(qcBill);
            result.setRetCode(Constant.SYS_SUCCESS);
            
        } catch (Exception e) {
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("撤销失败!");
        }
            
        return result;
    }

    /**
     * 分配研发处理人
     * 
     * @return
     */
    @RequestMapping("/assignPerson")
    @ResponseBody
    public HandlerResult assignPerson(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("devProPeople") String devProPeople) {

        HandlerResult result = HandlerResult.newInstance();
        try {
            
            QcBill qcBill = devBillService.get(qcId);
            User user = (User) request.getSession().getAttribute("loginUser");
            User devUser =  userService.getUserByRealName(devProPeople);
            String url = serverURL +"/qc/resDevQcBill/"+qcId+"/toBill";
            Map<String, Object> map =new HashMap<String, Object>();
            map.put("url", url);
            map.put("qcId", qcId);
            map.put("qcBill", qcBill);
            MailTaskDto mt = new MailTaskDto();
            mt.setReAddrs(new String[]{devUser.getEmail()});
            mt.setCcAddrs(new String[]{"shixiaoxiao@tuniu.com"});
            mt.setTemplateName("devFaultLink.ftl");
            mt.setAddPerson(user.getRealName());
            mt.setSubject(getSubject(qcId));
            mt.setDataMap(map);
            mailTaskService.addTask(mt);
            result.setRetCode(Constant.SYS_SUCCESS);
            
        } catch (Exception e) {
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("分配失败!");
        }
            
        return result;
    }
    
    private String getSubject(Integer qcId ) {
        StringBuffer sb = new StringBuffer();
        sb.append("请填写研发质检故障单["+qcId+"],谢谢配合");
        return sb.toString();
    }
    
    /**
     * 查看质检报告 
     * @param qcId
     * @param request
     * @return
     */
    @RequestMapping("/{qcId}/qcReport")
    public  String qcReport(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
        
        
        QcBill qcBill = devBillService.get(qcId);//质检单
        
        List<DevFaultBill> devFaultList = devFaultService.listDevFaultDetail(qcId);//质量问题
        
        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);
        
        //根据研发质检单号找到关联对应的投诉单号
        List<Integer> complaintIdList = devBillService.getComplainIdByDevId(qcId);
        
        List<ComplaintBill> complaintBillList = new ArrayList<ComplaintBill>();
        if(complaintIdList != null && complaintIdList.size() > 0){
        	//投诉信息
        	ComplaintBill complaintBill = new ComplaintBill();
        	for(Integer id : complaintIdList){
        		complaintBill = CmpClient.getComplaintInfo(id);
        		complaintBillList.add(complaintBill);
        	}
        }
        
        //关联jira列表
        JiraBillDto jiraDto =new JiraBillDto();
        jiraDto.setQcId(qcId);
        List<JiraBill>  jiraList = jiraService.listByQcId(jiraDto);
        request.setAttribute("devFaultList", devFaultList);
        request.setAttribute("jiraList", jiraList);
        request.setAttribute("qcBill", qcBill);
        request.setAttribute("innerPunishList", innerPunishList);
        //request.setAttribute("reasonList", complaintBill.getReasonList());
        request.setAttribute("complaintBillList", complaintBillList);
    
        return "qc/qc_development/qcReport";
        
    }
    
    /**
     * 查看质检报告 
     * @param qcId
     * @param request
     * @return
     */
    @RequestMapping("/{qcId}/updateQcBill")
    public  String updateQcBill(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
        QcBill qcBill = devBillService.get(qcId);//质检单
        request.setAttribute("qcBill", qcBill);
        
        request.setAttribute("falutSourceList", FaultSourceEnum.getSourceList());
    
        return "qc/qc_development/qcBillUpdateForm";
        
    }
    
    /**
     * 修改质检单
     * @return
     */
    @RequestMapping("/updateDev")
    @ResponseBody
    public HandlerResult updateDev(HttpServletRequest request,QcBill qcBill) {
            
            HandlerResult result = new HandlerResult();
            User user = (User) request.getSession().getAttribute("loginUser");
            qcBill.setUpdatePerson(user.getRealName());
            qcBill.setVerification("");
            try {
                
                devBillService.update(qcBill);
                result.setRetCode(Constant.SYS_SUCCESS);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("保存失败");
            }
            
            return result;
        }
    
    
    
    
    /**
     * 质检单状态返回质检中【从已完成状态返回】
     * @author jiangye
     */
    @RequestMapping("{id}/back2Processing")
    public String back2Processing(@PathVariable("id")Integer id ,QcBillDto dto, HttpServletRequest request) {
        //执行返回质检中逻辑
        qcBillService.backToProcessing(id);
        
        //添加处罚单数据添加切片任务
        qcPunishSegmentTaskService.builTask(id, 2);
        return list(dto,request);
    } 

    
    /**
     * 删除影响系统
     * @return
     */
    @RequestMapping("/deleteSystem")
    @ResponseBody
    public HandlerResult deleteSystem(HttpServletRequest request,@RequestParam("id") Integer id) {
            
            HandlerResult result = new HandlerResult();
            
            try {
                
                devBillService.deleteSystem(id);
                result.setRetCode(Constant.SYS_SUCCESS);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("删除失败");
            }
            
            return result;
        }
    
    /**
     * 初始化添加影响系统页面
     * @param qcId
     * @param request
     * @return
     */
    @RequestMapping("/{qcId}/toAddSystem")
    public  String toAddSystem(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
        
        
        List<JiraProject> projectList =  assignService.getProjectFromJira();//到jira系统获取系统名称
        request.setAttribute("projectList", projectList);
        request.setAttribute("qcId", qcId);
        return "qc/qc_development/qcInfluenceSystemAdd";
        
    }
    
    
    /**
     * 删除影响系统
     * @return
     */
    @RequestMapping("/addSystem")
    @ResponseBody
    public HandlerResult addSystem(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("influenceSystem") String influenceSystem ) {
            
            HandlerResult result = new HandlerResult();
            User user = (User) request.getSession().getAttribute("loginUser");
            QcInfluenceSystem qcInfo = new QcInfluenceSystem();
            
            try {
                
                qcInfo.setQcId(qcId);
                qcInfo.setInfluenceSystem(influenceSystem);
                qcInfo.setAddPerson(user.getRealName());
                devBillService.addSystem(qcInfo);
                result.setRetCode(Constant.SYS_SUCCESS);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("保存失败");
            }
            
            return result;
        }
    
    @RequestMapping(value = "/{id}/sendEmail")
    @ResponseBody
    public HandlerResult sendEmail(@PathVariable("id") Integer id, HttpServletRequest request) {
    	
     HandlerResult result = HandlerResult.newInstance();
      try {
    	  
    	  QcBill qcBill = devBillService.get(id);
    	  User user = (User) request.getSession().getAttribute("loginUser");
    	  String reEmails = request.getParameter("reEmails");
          String ccEmails = request.getParameter("ccEmails");
          String subject = request.getParameter("title");
          
          if("".equals(qcBill.getVerification())){
		    	
		    	result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("核实情况不能为空");
			    return result;
		    }
          int count = devBillService.getInfluenceSystemCount(qcBill.getId());
          if(count<=0){
        	  
        		result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("影响系统不能为空");
			    return result;
          }
          devBillService.updateQcReportEmail(qcBill,user,reEmails,ccEmails,subject);
          
          //添加处罚单数据添加切片任务
          qcPunishSegmentTaskService.builTask(qcBill.getId(), 1);
        
          result.setRetCode(Constant.SYS_SUCCESS);

	} catch (Exception e) {
		
		e.printStackTrace();
		result.setRetCode(Constant.SYS_FAILED);
		result.setResMsg("发送邮件失败");
	}
       
        return result;
    }
    

	
	 /**
     * 重新分配质检人
     * @return
     */
    @RequestMapping("/assignDelPerson")
    @ResponseBody
    public HandlerResult assignDelPerson(HttpServletRequest request,QcBillDto dto) {
            
            HandlerResult result = new HandlerResult();
            User user = (User) request.getSession().getAttribute("loginUser");
          
            try {
            	
            	qcBillService.updateQcPerson(dto.getQcBillIds(), dto.getAssignTo(), user);
                result.setRetCode(Constant.SYS_SUCCESS);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("分配失败");
            }
            
            return result;
        }
    

	private double getCompanyLose(ComplaintBill complaintBill){
		
		BigDecimal b1 = new BigDecimal(complaintBill.getIndemnifyAmount()==null?0:complaintBill.getIndemnifyAmount());
		BigDecimal b2 = new BigDecimal(complaintBill.getClaimAmount()==null?0:complaintBill.getClaimAmount());
		BigDecimal b3 = new BigDecimal(complaintBill.getQualityToolAmount()==null?0:complaintBill.getQualityToolAmount());
		
	   return (b1.subtract(b2).subtract(b3)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
	}
}
