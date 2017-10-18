package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.CommonTypeDto;
import com.tuniu.qms.common.model.CommonType;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.CommonTypeService;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.model.QcBillRelation;
import com.tuniu.qms.qc.service.QcBillRelationService;
import com.tuniu.qms.qc.service.ResDevQcBillService;
import com.tuniu.qms.qc.util.FaultSourceEnum;
import com.tuniu.qms.qc.util.QcConstant;


/**
 * 投诉单研发质检关联列表
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/qcBillRelation")
public class QcBillRelationController {

    
	@Autowired
	private QcBillRelationService service;

	@Autowired
	private ResDevQcBillService devBillService;
	
	@Autowired
	private OperationLogService operationLogService;
	
	@Autowired
	private CommonTypeService commonTypeService;
	
	private final static Logger logger = LoggerFactory.getLogger(QcBillRelationController.class);
	
	/**
	 * 初始化质检关联
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(QcBillRelationDto dto,HttpServletRequest request){
		
		service.loadPage(dto);
		
		CommonTypeDto typeDto = new CommonTypeDto();
		typeDto.setTypeFlag(1);
		List<CommonType> cmTypeList = commonTypeService.list(typeDto);//关闭原因类型
		
		request.setAttribute("cmTypeList", cmTypeList);
		request.setAttribute("dto", dto);
		
		return "qc/qcBillRelationList";
	}
	
	
	/**
	 *  双方质检、投诉转研发质检
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveCmpToDev")
	@ResponseBody
	public HandlerResult saveCmpToDev(@RequestParam("qcId") Integer qcId,
			@RequestParam("cmpId") Integer cmpId,@RequestParam("qcFlag") Integer qcFlag,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		try {
			
			QcBillRelation bill = new QcBillRelation();
			bill.setQcId(qcId);
			bill.setCmpId(cmpId);
			bill.setQcFlag(qcFlag);
			bill.setUpdatePerson(user.getRealName());
			service.saveCmpToDev(bill);
			result.setRetCode(Constant.SYS_SUCCESS);
			Integer devId= 0;
			// 添加操作日志
			addQcBillOperationLog(user, qcId, devId,qcFlag, true);
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		
		return result;
	}

	/**
	 *  双方质检、研发转投诉质检
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveDevToCmp")
	@ResponseBody
	public HandlerResult saveDevToCmp(@RequestParam("devId") Integer devId,
			@RequestParam("cmpId") Integer cmpId,@RequestParam("qcFlag") Integer qcFlag,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		try {
			
			QcBillRelation bill = new QcBillRelation();
			bill.setDevId(devId);
			bill.setCmpId(cmpId);
			bill.setQcFlag(qcFlag);
			bill.setUpdatePerson(user.getRealName());
			result.setRetCode(Constant.SYS_SUCCESS);
			Integer qcId = service.getQcIdByDevIdAndCmpId(devId, cmpId);
			// 先添加操作日志，再进行更新
			addQcBillOperationLog(user, qcId,devId, qcFlag, false);
			
			service.saveDevToCmp(bill);
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 添加质检单操作日志   双方质检  研发质检 投诉质检间转换
	 * @param user
	 * @param qcId 投诉单号
	 * @param qcFlag 转换标志 1：投诉质检 2：研发质检 3：双方质检
	 * @param b 是否是研发质检
	 */
	private void addQcBillOperationLog(User user, Integer qcId, Integer devId, Integer qcFlag,
			boolean b) {
		
		OperationLog log = new OperationLog();
		log.setDealPeople(user.getId());
		log.setDealPeopleName(user.getRealName());
		log.setDealDepart(user.getRole().getName());
		log.setQcId(qcId);
		
		if(b){
			log.setFlowName("增加研发质检");
		}else{
			log.setFlowName("增加投诉质检");
		}
		
		switch(qcFlag){
			case 1:
				log.setContent("投诉质检单：" + qcId + "，研发质检单："+devId+"，进入投诉质检");
				break;
			case 2:
				log.setContent("投诉质检单：" + qcId + "，进入研发质检");
				break;
			case 3:
				if(devId>0){
					
					log.setContent("投诉质检单：" + qcId + "，研发质检单："+devId+"，进入双方质检");
					
				}else{
					
					 log.setContent("投诉质检单：" + qcId + "，进入双方质检");
				}
				
				break;
			default:
				break;
		}
		
		operationLogService.add(log);
	}
	
	@RequestMapping("/toCloseRelation")
	public String toCloseRelationShip(@RequestParam("relationIds") Integer relationId, HttpServletRequest request){
		CommonTypeDto typeDto = new CommonTypeDto();
		typeDto.setTypeFlag(1);
		List<CommonType> cmTypeList = commonTypeService.list(typeDto);//关闭原因类型
		
		request.setAttribute("cmTypeList", cmTypeList);
		request.setAttribute("relationId", relationId);
		return "qc/closeRelationShip";
	}
	
	/**
	 * 关闭关联单
	 * @param request
	 * @return
	 */
	@RequestMapping("/closeRelation")
	@ResponseBody
	public HandlerResult closeRelation(QcBillRelationDto dto, HttpServletRequest request){
		HandlerResult result = HandlerResult.newInstance();
	
		try {
			QcBillRelation bill = new QcBillRelation();
			bill.setId(new Integer(dto.getId()));
			bill.setCloseType(dto.getCloseType());
			bill.setRemark(dto.getRemark());
			bill.setFlag(QcConstant.QC_CLOSED);//已关闭
			bill.setDevId(0);
			service.update(bill);
			result.setRetCode(Constant.SYS_SUCCESS);
		} catch (Exception e) {
			logger.error("关闭失败" , e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 关闭关联单
	 * @param request
	 * @return
	 */
	@RequestMapping("/closeByCmpId")
	@ResponseBody
	public HandlerResult closeByCmpId(@RequestParam("cmpId") String cmpId,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
	
		try {
			
			QcBillRelationDto bill =new QcBillRelationDto();
			bill.setCmpId(cmpId);
			bill.setFlag(QcConstant.QC_CLOSED);//已关闭
			service.updateByCmpAndDev(bill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 初始化关联研发质检单
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/relationQcId")
	public String relationQcId(HttpServletRequest request,@RequestParam("relationIds") String relationIds){
		
		request.setAttribute("relationIds", relationIds);
		return "qc/qcBillRelationForm";
		
	}
	
	/**
	 * 关联质检单
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateQcId")
	@ResponseBody
	public HandlerResult updateQcId(@RequestParam("relationId") String relationId,@RequestParam("qcId") Integer qcId,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
	
		try {
			
		   //校验研发质检单号存不存在
			int count =  devBillService.getQcBillIsExist(qcId);
			if(count < 1){
				
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("研发质检单号不存在");
				return result;
				
			}
		  
			  List<String> list =new ArrayList<String>();
			  String[] relationIds = relationId.split(",");
			  for(int i = 0;i<relationIds.length;i++){
					
					list.add(relationIds[i]);
			  }	
			  Map<String, Object> map =new HashMap<String, Object>();		
			  map.put("relationIds", list);
			  map.put("flag", QcConstant.QC_CONNECTED);
			  map.put("devId", qcId);
			  service.updateRelation(map);
			  result.setRetCode(Constant.SYS_SUCCESS);
			  return result;
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			return result;
		}
		
	}

  
	/**
	 * 初始化批量创建质检单页面
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/toAddQcBill")
	public String toAddQcBill(HttpServletRequest request,@RequestParam("relationIds") String relationIds){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relationIds", Arrays.asList(relationIds.split(",")));
		List<QcBillRelation> relationList = service.listRelation(map);
		
		StringBuffer sb = new StringBuffer();
		for (QcBillRelation bill : relationList) {
			sb.append(bill.getCmpId()).append(",");
		}
		
		request.setAttribute("cmpIds", sb.substring(0, sb.length()-1));
		request.setAttribute("relationIds", relationIds);
		request.setAttribute("falutSourceList", FaultSourceEnum.getSourceList());
		
		return "qc/batchRelationQcBillForm";
		
	}
   
	/**
	 * 待关联列表中质检单子返回投诉质检
	 * @param request
	 * @param relationId
	 * @return
	 */
	@RequestMapping("/backToCmpQcBill")
	public String backToCmpQcBill(HttpServletRequest request, @RequestParam("relationIds") Integer relationId){
		
		request.setAttribute("relationId", relationId);
		return "qc/backToCmpQcBill";
	}
	
	/**
	 * 待关联列表中质检单子返回投诉质检
	 * @param dto
	 * @return
	 */
	@RequestMapping("/submitBack")
	@ResponseBody
	public HandlerResult submitBack(QcBillRelationDto dto, HttpServletRequest request){
		HandlerResult handlerResult = HandlerResult.newInstance();
		
		try{
			User User = (User) request.getSession().getAttribute("loginUser");
			service.backToCmpQcBill(dto, handlerResult, User);
		}catch(Exception e){
			handlerResult.setRetCode(Constant.SYS_FAILED);
			handlerResult.setResMsg("返回失败");
			logger.error("返回失败，关联单号： " +dto.getId(), e);
		}
		
		return handlerResult;
	}
}
