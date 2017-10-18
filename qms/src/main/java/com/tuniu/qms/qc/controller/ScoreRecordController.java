package com.tuniu.qms.qc.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.CellDataMapperHandler;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.POIUtil;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.util.QcConstant;


@Controller
@RequestMapping("/qc/scoreRecord")
public class ScoreRecordController {

	@Autowired
	private InnerPunishBillService innerService;
	
	private final static Logger logger = LoggerFactory.getLogger(ScoreRecordController.class);

	/**
	 * 初始化记分管理列表
	 */
	@RequestMapping("/list")
	public String list(InnerPunishBillDto dto, HttpServletRequest request) {
		dto.setState(QcConstant.QC_BILL_STATE_FINISH);
		
		innerService.loadPage(dto);
		request.setAttribute("dto", dto);
		
		return "qc/qc_score/score_record_list";
	}
	
	@RequestMapping("/myList")
	public String myList(InnerPunishBillDto dto,HttpServletRequest request){

		User user = (User) request.getSession().getAttribute("loginUser");
		dto.setPunishPersonName(user.getRealName());
		dto.setState(QcConstant.QC_BILL_STATE_FINISH);
		
		if(null != dto.getPunishJobType() && dto.getPunishJobType() == 1){
			dto.setRespManagerName(user.getRealName());
		}
		if(null != dto.getPunishJobType() && dto.getPunishJobType() == 2){
			dto.setRespGeneralName(user.getRealName());
		}
		
		innerService.loadPage(dto);
		
		if(dto.getTotalRecords() > 0 ){
			dto.setTotalScores(innerService.getTotalScores(dto));
		}
		
		request.setAttribute("dto", dto);
		return "qc/qc_score/my_score_record";
	}
	
	/*@RequestMapping("/{useFlag}/toAdd")
	public String toAdd(@PathVariable("useFlag") Integer useFlag,HttpServletRequest request){
		
		  User user = (User) request.getSession().getAttribute("loginUser");
		  
		  QcBill qc = new QcBill();
		  qc.setQcAffairSummary("运营质检处罚");
		  qc.setGroupFlag(2); 
		  qc.setState(QcConstant.QC_BILL_STATE_FINISH);
		  qc.setUserFlag(QcConstant.USER_FLAG_TRUE);
		  qc.setDelFlag(Constant.YES);
		  qc.setQcPerson(user.getRealName());
		  qc.setQcPersonId(user.getId());
		  qc.setAddPerson(user.getRealName());
		  qc.setAddPersonId(user.getId());
		  qcService.addQcBill(qc);
		  
		 
		  innerPunish.setQcId(qc.getId());
		  innerPunish.setPunishReason("");
		  innerPunish.setAddPerson(user.getRealName());  
		  innerPunish.setDelFlag(Constant.YES);
		  innerPunish.setRelatedFlag(Constant.YES);
		  innerService.addPunish(innerPunish);
		  
		  InnerPunishBill innerPunish =new InnerPunishBill();
		  innerPunish.setEconomicPunish(0.00);
		  innerPunish.setScorePunish(0);
		  request.setAttribute("useFlag",  useFlag);
		  request.setAttribute("innerPunish",  innerPunish);
		  
		  List<Department> depList = depService.getDepDataCache();
		  request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		  List<Job> jobList = jobService.getJobDataCache();
		  request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		  List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		  request.setAttribute("qcTypeNames", CommonUtil.getQtNames(qtList));
		  List<User> userList = userService.getAllUserData();
		  request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		  
		  return "qc/qc_score/score_record_form";
		
	}*/
	
	/**
	 * 修改内部处罚单初始化
	 * @param request
	 * @param innerId
	 * @return
	 */
	/*@RequestMapping("/{innerId}/{useFlag}/updateScore")
	public String updateScore(HttpServletRequest request,@PathVariable("innerId") Integer innerId, @PathVariable("useFlag") Integer useFlag){
		
		 InnerPunishBill innerPunish =  innerService.get(innerId);
		 Integer depId = innerPunish.getDepId();
		 String fullName = depService.getDepFullNameById(depId);
		 innerPunish.setDepName(fullName);
		 QcBill bill = qcService.getById(innerPunish.getQcId());
		 List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		 String qtFullName = qcTypeService.getQtFullNameById(bill.getQcTypeId(),qtList);	 
		 innerPunish.setQcTypeName(qtFullName);
		 request.setAttribute("innerPunish", innerPunish);
		 request.setAttribute("useFlag", useFlag);
		 List<Department> depList = depService.getDepDataCache();
		  request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		  List<Job> jobList = jobService.getJobDataCache();
		  request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		 return "qc/qc_score/score_record_update_form";
	}*/
	
/*	@RequestMapping("/deleteInnerPunish")
	@ResponseBody
	public HandlerResult deleteInnerPunish(HttpServletRequest request,@RequestParam("innerId") Integer innerId){
		HandlerResult result =  HandlerResult.newInstance();
		try {
			
			innerService.deletePunish(innerId);
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
	 * @param request
	 * @param qcId
	 * @return
	 */
	/*@RequestMapping("/{innerId}/getPunishDetail")
	public String getPunishDetail(HttpServletRequest request,@PathVariable("innerId") Integer innerId){
		
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		InnerPunishBill punishBill =  innerService.get(innerId);
		Integer qcTypeId = punishBill.getQcTypeId();
		String qtFullName = qcTypeService.getQtFullNameById(qcTypeId,qtList);
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
		
	}*/
	
	/**
	 *  导入记分数据
	 * @param request
	 * @param innerId
	 * @return
	 */
	@RequestMapping("/toImport")
	public String toImport(HttpServletRequest request){
		
		 return "qc/qc_score/score_record_import";
	}
	
	/**
     * 导出个数限制
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/checkExport")
    @ResponseBody
    public HandlerResult checkExport(InnerPunishBillDto dto, HttpServletRequest request) {
		HandlerResult result = new HandlerResult();

		try {
			dto.setState(QcConstant.QC_BILL_STATE_FINISH);
			
			Integer count = innerService.exportCount(dto);
			
			if (count <= 5000) {
				result.setRetCode(Constant.SYS_SUCCESS);
			} else {
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("导出数量不能超过五千条");
			}
		} catch (Exception e) {
			logger.error("校验导出数量失败", e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("校验导出数量失败");
		}

		return result;
    }
    
    
    @RequestMapping("/exports")
    public String exports(InnerPunishBillDto dto, HttpServletRequest request, HttpServletResponse response){

		dto.setState(QcConstant.QC_BILL_STATE_FINISH);
		List<InnerPunishBill> innerList = innerService.exportList(dto);

		exportData(innerList, response);
		
		request.setAttribute("dto", dto);
		return null;
    }
    
    private void exportData(List<InnerPunishBill> innerList, HttpServletResponse response) {
		try {
			String[] headers = new String[] { "周数", "处罚单号", "质检单号", "订单号",
					"产品单号", "被处罚人姓名", "被处罚人工号", "事业部", "部门", "组", "关联岗位",
					"质检性质", "质检类型", "记分处罚", "经济处罚", "处罚事由", "质检人", "质检完成时间" };
			String[] exportsFields = new String[] { "week", "id", "qcId",
					"ordId", "prdId", "punishPersonName", "pubPersonId",
					"businessUnit", "dep", "team", "jobName", "qcTypeNature",
					"qcTypeName", "scorePunish", "economicPunish",
					"punishReason", "qcPerson", "qcFinishTime" };

			String fileName = "score_record" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
			
			new POIUtil<InnerPunishBill>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), fileName, innerList,
					new CellDataMapperHandler() {
						@Override
						public String mapToCell(String fieldName, Object value) {
							String textValue = "";
							
							if (value instanceof Date) {
								Date date = (Date) value;
								textValue = DateUtil.formatAsDefaultDateTime(date);
								
								return textValue;
							} else {
								
								return value == null ? "" : value + "";
							}
						}
					}, response);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
