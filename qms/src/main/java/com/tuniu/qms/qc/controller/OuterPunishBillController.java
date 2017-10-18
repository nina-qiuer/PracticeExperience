package com.tuniu.qms.qc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.tuniu.qms.qc.dto.OuterPunishBasisDto;
import com.tuniu.qms.qc.dto.OuterPunishBillDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.OuterPunishBasisService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.util.QcConstant;


@Controller
@RequestMapping("/qc/outerPunish")
public class OuterPunishBillController {

	@Autowired
	private OuterPunishBillService outerService;
	
	@Autowired
	private OuterPunishBasisService basisService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	
	/**
	 * 初始化外部处罚单
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(OuterPunishBillDto dto,HttpServletRequest request){
		
		
			
//			if (StringUtils.isBlank(dto.getAddPunishTimeBgn())  && StringUtils.isBlank( dto.getAddPunishTimeBgn())) {
//				
//				String beginTime = DateUtil.getMonthFirstDay();//添加开始时间
//				String endTime = DateUtil.getMonthEndDay();//添加结束时间
//				dto.setAddPunishTimeBgn(beginTime); 
//				dto.setAddPunishTimeEnd(endTime);
//				
//			}
			if(!StringUtils.isEmpty(dto.getQcTypeName())){
				
				 QcType qcType = qcTypeService.getTypeByFullName(dto.getQcTypeName());
				 dto.setQcTypeId(qcType.getId());
			}
			
		outerService.loadPage(dto);// 查询外部责任单数据
		request.setAttribute("dto", dto);
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		request.setAttribute("qcTypeNames", CommonUtil.getQtNames(qtList));
		return "qc/qc_punish/outerPunishBillList";
	}
	
	
	
	/**
	 * 初始化供应商处罚人页面
	 * @param qcId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcId}/{useFlag}/toAdd")
	public String toAdd(@PathVariable("qcId") Integer qcId,@PathVariable("useFlag") Integer useFlag,HttpServletRequest request){
		
		  User user = (User) request.getSession().getAttribute("loginUser");
		  OuterPunishBill outerPunish =new OuterPunishBill();
		  outerPunish.setQcId(qcId);
		  outerPunish.setPunishReason("");
		  outerPunish.setAddPerson(user.getRealName());
		  outerPunish.setDelFlag(Integer.parseInt(QcConstant.DEL_FLAG_TRUE));
		  outerService.addPunish(outerPunish);
		  
		  outerPunish.setEconomicPunish(0.00);
		  outerPunish.setScorePunish(0);
		  outerPunish.setRespId(0);
		  outerPunish.setRespType(2);
		  request.setAttribute("outerPunish",  outerPunish);
		  request.setAttribute("useFlag",  useFlag);
		  return "qc/qc_punish/outerPunishForm";
		
	}
	
	/**
	 * 新增供应商处罚人
	 * @param request
	 * @param outerBill
	 * @return
	 */
	@RequestMapping("/addOuterPunish")
	@ResponseBody
	public HandlerResult addInnerPunish(HttpServletRequest request,OuterPunishBill outerBill){
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		outerBill.setAddPerson(user.getRealName());  
		outerBill.setUpdatePerson(user.getRealName());
		try {
			
//			int count =  outerService.getOuterPunishIsExist(outerBill);
//			if(count>0){
//				result.setRetCode(Constant.SYS_FAILED);
//				result.setResMsg("处罚人已存在");
//				return result;
//			}
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("opbId", outerBill.getId());
			int basisCount = basisService.getPunishBasisIsExist(map);
			if(basisCount<1){
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("请填写处罚依据");
				return result;
			}
			outerBill.setDelFlag(Integer.parseInt(QcConstant.DEL_FLAG_FALSE));
			outerService.update(outerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			
		}
		   return result;
	}
	
	/**
	 * 查询外部处罚单详情
	 * @param request
	 * @param innerId
	 * @return
	 */
	@RequestMapping("/{outerId}/getDetail")
	public String getDetail(HttpServletRequest request,@PathVariable("outerId") Integer outerId ){
		
		OuterPunishBill punishBill =  outerService.get(outerId);
		OuterPunishBasisDto dto = new OuterPunishBasisDto();
		dto.setOpbId(outerId);
		List<OuterPunishBasis> basisList = basisService.list(dto);
		request.setAttribute("punishBill", punishBill);
		request.setAttribute("basisList", basisList);
		return "qc/qc_punish/outerPunishDetail";
		
	}
	
	/**
	 * 删除外部处罚单以及处罚依据
	 * @param request
	 * @param outerId
	 * @return
	 */
	@RequestMapping("/deleteOuterPunish")
	@ResponseBody
	public HandlerResult deleteOuterPunish(HttpServletRequest request,@RequestParam("outerId") Integer outerId){
		HandlerResult result =  HandlerResult.newInstance();
		
		try {
			
			outerService.deletePunish(outerId);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			
		}
		   return result;
	}
	
	/**
	 * 修改外部处罚单初始化
	 * @param request
	 * @param innerId
	 * @return
	 */
	@RequestMapping( "/{outerId}/{useFlag}/updateOuter")
	public String updateInner(HttpServletRequest request,@PathVariable("outerId") Integer outerId ,@PathVariable("useFlag") Integer useFlag){
		
		OuterPunishBill outerPunish =  outerService.get(outerId);
		request.setAttribute("outerPunish", outerPunish);
		request.setAttribute("useFlag", useFlag);
		return "qc/qc_punish/outerPunishUpdateForm";
	}
	

	/**
	 * 更新外部处罚单
	 * @param request
	 * @param innerBill
	 * @return
	 */
	@RequestMapping("/updateOuterPunish")
	@ResponseBody
	public HandlerResult updateOuterPunish(HttpServletRequest request,OuterPunishBill outerBill){
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		outerBill.setUpdatePerson(user.getRealName());
		try {
			
			outerService.update(outerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			
		}
		   return result;
	}
	
	/**
	 * 查询外部责任单详情
	 * @param request
	 * @param qcId
	 * @return
	 */
	@RequestMapping("/{outerId}/getPunishDetail")
	public String getPunishDetail(HttpServletRequest request,@PathVariable("outerId") Integer outerId){
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		 OuterPunishBill punishBill =  outerService.get(outerId);
		 Integer qcTypeId = punishBill.getQcTypeId();
		 String fullName = qcTypeService.getQtFullNameById(qcTypeId,qtList);
		 punishBill.setQcTypeName(fullName);
		OuterPunishBasisDto dto = new OuterPunishBasisDto();
		dto.setOpbId(outerId);
		List<OuterPunishBasis> basisList = basisService.list(dto);
		request.setAttribute("punishBill", punishBill);
		request.setAttribute("basisList", basisList);
		return "qc/qc_punish/outerPunishFormDetail";
		
	}
}
