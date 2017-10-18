package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.dto.OperationLogDto;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;
import com.tuniu.qms.qs.service.QualityCostLedgerService;
import com.tuniu.qms.report.service.QualityProblemReportService;

/**
 * 无投诉质检单
 * @author zhangsensen
 *
 */

@Controller
@RequestMapping("/qc/qcBillWithoutCmp")
public class QcBillWithoutCmpController {
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
    private QualityProblemService qualityProblemService;

    @Autowired
    private InnerPunishBillService innerPunishService;
    
    @Autowired
    private OuterPunishBillService outerPunishService;
    
    @Autowired
    private ProductService prdService;
	
	@Autowired
	private OrderBillService orderBillService;
	 
	@Autowired
	private OperationLogService  operLogService;
	
	@Autowired
	 private QualityCostLedgerService ledgerService;
	 
	 @Autowired
	 private QualityCostAuxAccountService  auxService;
		
	 @Autowired
	 private QualityProblemReportService  qpReportService;
	 
	 @Autowired
	 private QcPunishSegmentTaskService qcPunishSegmentTaskService;
	
	@RequestMapping("list")
	public String list(QcBillDto dto, HttpServletRequest request){
		
		User user = (User) request.getSession().getAttribute("loginUser");
		if(Constant.ROLE_EMPLOYEE == user.getRole().getType() && dto.getState()!=QcConstant.QC_BILL_STATE_AUDIT){
			dto.setQcPerson(user.getRealName());
		}
		
		qcBillService.loadPageNoCmp(dto);
		request.setAttribute("dto", dto);
		return "qc/qc_noCmp/qcList";
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
		qcBillService.updateQcPerson(dto.getQcBillIds(), dto.getAssignTo(), user);
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
		qcBillService.backToProcessing(id);

		deleteQualityCost(id);//删除质量成本
		deleteQpReport(id);//删除质量问题切片
		
		//添加处罚单数据添加切片任务
        qcPunishSegmentTaskService.builTask(id, 2);
		
		OperationLog log = new OperationLog();
		log.setDealPeople(user.getId());
		log.setDealPeopleName(user.getRealName());
		log.setQcId(id);
		log.setDealDepart(user.getRole().getName());
		log.setFlowName("返回质检中");
		log.setContent("质检单："+id+"，返回质检中");
		operLogService.add(log);
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
	 * 查看质检报告 
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/qcReport")
	public  String qcReport(@PathVariable("qcId") Integer qcId,HttpServletRequest request){
			QcBill qcBill = qcBillService.get(qcId);//质检单
			
	        // 订单信息
	        OrderBill orderBill =orderBillService.get(qcBill.getOrdId());
	        if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
				
				int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
				orderBill.setDepartDay(day+1);
			}
	        // 产品信息
	        Product product = prdService.get(qcBill.getPrdId());
	        
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
			request.setAttribute("executeQpList", executeQpList);
			request.setAttribute("supplierQpList", supplierQpList);
			request.setAttribute("flowsQpList", flowsQpList);
			request.setAttribute("improveQpList", improveQpList);
			request.setAttribute("dateTime", new Date());
			request.setAttribute("qcPerson", qcBill.getQcPerson());
			request.setAttribute("operlist", operlist);
			return "qc/qc_noCmp/qcReport";
		
	}

}
