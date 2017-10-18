package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.service.TalkConfigService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.QcAuxiliaryBillDto;
import com.tuniu.qms.qc.dto.QcAuxiliaryTemplateDto;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.service.AssignConfigDevService;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.OperateQcBillService;
import com.tuniu.qms.qc.service.QcAuxiliaryBillService;
import com.tuniu.qms.qc.service.QcAuxiliaryTemplateService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcNoteService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.util.QcConstant;


/**
 *运营质检 20151216
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/operateQcBill")
public class OperateQcBillController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OperateQcBillService service;
    
	@Autowired
	private QcTypeService qptService;
	
    @Autowired
    private MailTaskService mailTaskService;
    
    @Autowired 
    private QcNoteService qcNoteService;
    
    @Autowired
    private InnerPunishBillService  innerPunishService;
    
    @Autowired
    private QcBillService qcBillService;
    
    @Autowired
    private DepartmentService  depService;
    
    @Autowired
    private AssignConfigDevService assignService;
    
    @Autowired
    private TalkConfigService talkConfigService;
    
    @Autowired
    private QcTypeService qcTypeService;
    
    @Autowired
	private OrderBillService orderService;
    
    @Autowired
    private ProductService prdService;
    
    @Autowired
    private QualityProblemService qpService;
    
    @Autowired
    private QcAuxiliaryTemplateService qcTempService;
    
    @Autowired
    private QcAuxiliaryBillService qcAuxBillService;
    
    @Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
    /**
     * 初始化运营质检列表
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
        service.loadPage(dto);
        request.setAttribute("dto", dto);
		request.setAttribute("userRealName", user.getRealName());
        return "qc/qc_operate/qcBillList";
    }
    
    

    /**
     * 初始化质检报告
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/{id}/toBill")
    public String toBill(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		Integer qcNoteCount = getQcNoteCount(id);
		request.setAttribute("qcNoteCount", qcNoteCount);
		request.setAttribute("qcBillId", id);
		return "qc/qc_operate/qcBill";
    }
    
	
	/**
	 * 质检单信息
	 * @param request
	 * @param qcBillId
	 * @return
	 */
	@RequestMapping("/queryList/{qcBillId}")
	public String queryList(@PathVariable("qcBillId") Integer qcBillId,HttpServletRequest request) {

		// 质检单信息
		QcBill qcBill = service.get(qcBillId);
		//订单信息
		OrderBill orderBill = orderService.get(qcBill.getOrdId()) ;
		qcBill.setGroupDate(orderBill.getDepartDate());
		
		QcAuxiliaryTemplateDto dto = new QcAuxiliaryTemplateDto();
		dto.setQcType(qcBill.getQcTypeId());
		QcAuxiliaryTemplate template = qcTempService.getByQcType(dto);
		
		request.setAttribute("qcBill", qcBill);
		request.setAttribute("template", template);
		return "qc/qc_operate/report";
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
        return "qc/qc_operate/qcNoteList";
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
     * 辅助信息页面
     * @param qcBillId
     * @param request
     * @return
     */
    @RequestMapping("/{qcBillId}/toAuxiliaryInfo")
	public String toAuxiliaryInfo(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request) {

		QcBill qcBill = service.get(qcBillId);
		//订单信息
		OrderBill orderBill = orderService.get(qcBill.getOrdId()) ;
		Product product =  prdService.get(qcBill.getPrdId());  
		
		request.setAttribute("orderBill", orderBill);
		request.setAttribute("product", product);
    	return "qc/qc_operate/auxiliaryInfo";

    }
    
    
    /**
     * 撤销质检单
     * @param model
     * @param qcId
     * @param remark
     * @return
     */
    @RequestMapping("/revokeOperateBill")
    @ResponseBody
    public HandlerResult revokeQcBill(HttpServletRequest request,@RequestParam("qcId") Integer qcId,@RequestParam("remark") String remark) {

        User user = (User) request.getSession().getAttribute("loginUser");
        HandlerResult result = HandlerResult.newInstance();
        QcBill qcBill =new QcBill();
        qcBill.setId(qcId);
        qcBill.setRemark(remark);
        qcBill.setFinishTime(new Date());
        qcBill.setState(QcConstant.QC_BILL_STATE_CANCEL);//撤销状态
        qcBill.setUpdatePerson(user.getRealName());
        qcBill.setCancelTime(new Date());
        try {
            
            service.deleteOperateBill(qcBill);
            result.setRetCode(Constant.SYS_SUCCESS);
            
        } catch (Exception e) {
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("撤销失败!");
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

    
    @RequestMapping(value = "/{id}/sendEmail")
    @ResponseBody
    public HandlerResult sendEmail(@PathVariable("id") Integer id, HttpServletRequest request) {
    	
     HandlerResult result = HandlerResult.newInstance();
      try {
    	  
    	  String reEmails = request.getParameter("reEmails");
          String ccEmails = request.getParameter("ccEmails");
          String subject = request.getParameter("title");
          MailTaskDto mailTaskDto = new MailTaskDto();
          mailTaskDto.setTemplateName("XXX.ftl");
          mailTaskDto.setSubject(subject);
          mailTaskDto.setReAddrs(reEmails.split(";"));
          mailTaskDto.setCcAddrs(ccEmails.split(";"));
          mailTaskDto.setDataMap(getQcReportContentDataMap(id, subject));
          User user = (User) request.getSession().getAttribute("loginUser");
          mailTaskDto.setAddPersonRoleId(user.getRole().getId());
          mailTaskDto.setAddPerson(user.getRealName());
          mailTaskService.addTask(mailTaskDto);

          // 向投诉质检系统推送处罚单数据
        //  pushPunishDataToCmp(id);

          QcBill qcBill = service.get(id);
          qcBill.setState(4);
          qcBill.setFinishTime(new Date());
          service.update(qcBill);
          result.setRetCode(Constant.SYS_SUCCESS);

	} catch (Exception e) {
		e.printStackTrace();
		result.setRetCode(Constant.SYS_FAILED);
		result.setResMsg("发送邮件失败");
	}
       
        return result;
    }
    
    private Map<String, Object> getQcReportContentDataMap(Integer id, String subject) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        QcBill qcBill = service.get(id);
        // 质检员
        dataMap.put("qcPerson", qcBill.getQcPerson());
        // 日期
        dataMap.put("dateTime", new Date());
        return dataMap;
    }
    /**
	 *  推送记分数据到CMP
	 * @param qcId
	 * @return
	 */
	/*private int pushPunishDataToCmp(Integer qcId){
		
		QcBill  qcBill  =  service.get(qcId);
		InnerPunishBillDto dto = new InnerPunishBillDto();
		dto.setQcId(qcId);
		List<InnerPunishBill> list = innerPunishService.list(dto);
		List<Map<String, Object>> listMap =new ArrayList<Map<String,Object>>();
	    Map<String, Object> requestMap =new HashMap<String, Object>();
	    requestMap.put("cmpId", qcBill.getCmpId());
		for(InnerPunishBill innerBill : list){
			
		  if(innerBill.getScorePunish()!=0){
			 
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("qc_person", qcBill.getQcPerson());
			map.put("order_id", qcBill.getOrdId());
			map.put("route_id", qcBill.getPrdId());
			map.put("complaint_id", qcBill.getCmpId());
			map.put("responsible_person", innerBill.getPunishPersonName());
			map.put("position_id", innerBill.getJobId());//岗位ID
			map.put("score_target1", innerBill.getPunishPersonName());
			map.put("score_value1", innerBill.getScorePunish());
			map.put("description", qcBill.getQcAffairDesc());
			map.put("score_type_name", innerBill.getQcTypeName());
		     QcType qcType = qcTypeService.get(innerBill.getQcTypeId());
         	if(null!=qcType && qcType.getPid()>0){
         		
         		QcType fatherQcType = qcTypeService.get(qcType.getPid());
         		map.put("score_type_fatherName", fatherQcType.getName());
         		
         	}else{
         		
         		map.put("score_type_fatherName", "");
         	}
			Department dep1 = depService.getDepById(innerBill.getDepId());
			if(dep1!=null){
				if(dep1.getPid()==1){
					
					map.put("dep_id1", dep1.getId());
					map.put("dep_id2", 0);
					
				}else{
					
					Department dep2 = depService.getDepById(dep1.getPid());
					if(dep2!=null){
						if(dep2.getPid()==1){
							
							map.put("dep_id1", dep2.getId());
							map.put("dep_id2", dep1.getId());
						}else{
							
							map.put("dep_id1", dep2.getPid());
							map.put("dep_id2", dep1.getPid());
						}
					}else{
						
						map.put("dep_id1",dep1.getPid() );
						map.put("dep_id2",dep1.getId() );
					}
				}
			}else{
				
				map.put("dep_id1", 0);
				map.put("dep_id2", 0);
			}
			
			listMap.add(map);
		  }
		}
	    requestMap.put("list", listMap);
		return CmpClient.pushPunishData(requestMap);//1 是成功 0 是失败
		
	}*/
	
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
    
    
	 /**
     * 提交质检
     * @return
     */
    @RequestMapping("/commitBill")
    @ResponseBody
    public HandlerResult commitBill(HttpServletRequest request,@RequestParam("qcId") Integer qcId) {
            
            HandlerResult result = new HandlerResult();
          
            try {
            	
            	   QcBill qcBill = service.get(qcId);
                   qcBill.setState(4);
                   qcBill.setFinishTime(new Date());
                   service.update(qcBill);
                   result.setRetCode(Constant.SYS_SUCCESS);
                   //添加处罚单数据添加切片任务
                   qcPunishSegmentTaskService.builTask(qcId, 1);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("提交失败");
            }
            
            return result;
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
	        OrderBill orderBill = orderService.get(qcBill.getOrdId());
	        qcBill.setGroupDate(orderBill.getDepartDate());
	        // 产品信息
	        Product product = prdService.get(qcBill.getPrdId());
	        //质量问题单
	        List<QualityProblem> qualityProblemlist = qpService.listQpDetail(qcId,Constant.QC_OPERATE);
	        List<QualityProblem> executeQpList = new ArrayList<QualityProblem>();//执行问题
	        for(QualityProblem qp : qualityProblemlist) {
	            if(qp.getQptName().startsWith("操作问题")) {
	                executeQpList.add(qp);
	            }
	        }
	        //内部处罚单列表
	        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcId);//内部处罚单
		
			request.setAttribute("qcBill", qcBill);
			request.setAttribute("innerPunishList", innerPunishList);
			request.setAttribute("orderBill", orderBill);
			request.setAttribute("product", product);
			request.setAttribute("executeQpList", executeQpList);
			return "qc/qc_operate/qcReport";
		
	}
	
    /**
     * 辅助信息页面
     * @param qcBillId
     * @param request
     * @return
     */
    @RequestMapping("/toAuxiliaryBillTemplate")
	public String toAuxiliaryBillTemplate(HttpServletRequest request) {

    	List<QcType> qcTypeList = qptService.list(new QcTypeDto());
		request.setAttribute("qcTypeList", qcTypeList);
    	return "qc/qc_operate/auxiliaryTemplate";

    }
	
    
    /**
     * 初始化质检报告
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/{qcType}/authTemplate")
    public String authTemplate(@PathVariable("qcType") Integer qcType, HttpServletRequest request) {
		
		
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		String qtFullName = qcTypeService.getQtFullNameById(qcType,qtList);
		QcAuxiliaryTemplateDto dto =new QcAuxiliaryTemplateDto();
		dto.setQcType(qcType);
		QcAuxiliaryTemplate qcTemplate = qcTempService.getByQcType(dto);
		request.setAttribute("qcTemplate", qcTemplate);
		request.setAttribute("qcTypeId", qcType);
		request.setAttribute("qtFullName", qtFullName);
		return "qc/qc_operate/auxiliaryTemplateList";
    }
	
	 /**
     * 保存模板
     * @return
     */
    @RequestMapping("/saveTemplate")
    @ResponseBody
    public HandlerResult saveTemplate(HttpServletRequest request,QcAuxiliaryTemplate template) {
            
            HandlerResult result = new HandlerResult();
          
            try {
            	
            	   qcTempService.add(template);
                   result.setRetCode(Constant.SYS_SUCCESS);
                
            } catch (Exception e) {
                
                e.printStackTrace();
                result.setRetCode(Constant.SYS_FAILED);
                result.setResMsg("保存失败");
            }
            
            return result;
        }
    
	 /**
     * 保存模板
     * @return
     */
    @RequestMapping("/updateTemplate")
    @ResponseBody
    public HandlerResult updateTemplate(HttpServletRequest request,QcAuxiliaryTemplate template) {
            
            HandlerResult result = new HandlerResult();
          
            try {
            	
            	   qcTempService.update(template);
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
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/{id}/{qcId}/toAuxiliaryBill")
    public String toAuxiliaryBill(@PathVariable("id") Integer id, @PathVariable("qcId") Integer qcId,HttpServletRequest request) {
		
		QcAuxiliaryTemplate qcTemplate = qcTempService.get(id);
		QcAuxiliaryBillDto dto =new QcAuxiliaryBillDto();
		dto.setTemplateId(qcTemplate.getId());
		dto.setQcId(qcId);
		List<QcAuxiliaryBill> list = qcAuxBillService.getByTemplate(dto);
		request.setAttribute("qcTemplate", qcTemplate);
		request.setAttribute("list", list);
		request.setAttribute("qcId", qcId);
		return "qc/qc_operate/auxiliaryBillInfo";
    }
	
	 /**
     * 初始化辅助表信息页面
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/{id}/{qcId}/toAddAuxBill")
    public String addAuxBill(@PathVariable("id") Integer id,@PathVariable("qcId") Integer qcId, HttpServletRequest request) {
		
		QcAuxiliaryTemplate qcTemplate = qcTempService.get(id);
		QcAuxiliaryBill auxBill = new QcAuxiliaryBill();
		auxBill.setQcId(qcId);
		request.setAttribute("qcTemplate", qcTemplate);
		request.setAttribute("auxBill", auxBill);
		return "qc/qc_operate/auxiliaryBillForm";
    }
	
	 /**
    * 删除辅助表信息
    * @return
    */
   @RequestMapping("/delAuxBill")
   @ResponseBody
   public HandlerResult delAuxBill(HttpServletRequest request,QcAuxiliaryBillDto dto) {
           
           HandlerResult result = new HandlerResult();
         
           try {
        	   
        	   qcAuxBillService.deleteByIds(dto);
               result.setRetCode(Constant.SYS_SUCCESS);
               
           } catch (Exception e) {
               
               e.printStackTrace();
               result.setRetCode(Constant.SYS_FAILED);
               result.setResMsg("删除失败");
           }
           
           return result;
       }
	
   /**
    * 新增辅助信息
    * @return
    */
   @RequestMapping("/addAuxBill")
   @ResponseBody
   public HandlerResult addAuxBill(HttpServletRequest request,QcAuxiliaryBill bill) {
           
           HandlerResult result = new HandlerResult();
         
           try {
        	   
        	   qcAuxBillService.add(bill);
               result.setRetCode(Constant.SYS_SUCCESS);
               
           } catch (Exception e) {
               
               e.printStackTrace();
               result.setRetCode(Constant.SYS_FAILED);
               result.setResMsg("保存失败");
           }
           
           return result;
       }
   
	   /**
	    * 编辑辅助信息初始化页面
	    * @return
	    */
 	 @RequestMapping("/{id}/toUpdateAuxBill")
     public String toUpdateAuxBill(@PathVariable("id") Integer id, HttpServletRequest request) {
            
 		   QcAuxiliaryBill auxBill = qcAuxBillService.get(id);
 		   QcAuxiliaryTemplate qcTemplate = qcTempService.get(auxBill.getTemplateId());
           request.setAttribute("auxBill", auxBill);
           request.setAttribute("qcTemplate", qcTemplate);
           return "qc/qc_operate/auxiliaryBillForm";
       }
 	 
 	  /**
 	    * 更新辅助信息
 	    * @return
 	    */
 	   @RequestMapping("/updateAuxBill")
 	   @ResponseBody
 	   public HandlerResult updateAuxBill(HttpServletRequest request,QcAuxiliaryBill bill) {
 	           
 	           HandlerResult result = new HandlerResult();
 	         
 	           try {
 	        	   
 	        	   qcAuxBillService.update(bill);
 	               result.setRetCode(Constant.SYS_SUCCESS);
 	               
 	           } catch (Exception e) {
 	               
 	               e.printStackTrace();
 	               result.setRetCode(Constant.SYS_FAILED);
 	               result.setResMsg("更新失败");
 	           }
 	           
 	           return result;
 	       }
}
