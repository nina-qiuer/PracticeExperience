package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.PunishStandard;
import com.tuniu.qms.qc.service.InnerPunishBasisService;
import com.tuniu.qms.qc.service.OuterPunishBasisService;
import com.tuniu.qms.qc.service.PunishStandardService;
import com.tuniu.qms.qc.util.QcConstant;

@Controller
@RequestMapping("/qc/punishStandard")
public class PunishStandardController {
	
	@Autowired
	private PunishStandardService service;
	
	@Autowired
	private InnerPunishBasisService innerBasisService;
	
	@Autowired
	private OuterPunishBasisService outerBasisService;
	
	@RequestMapping("/list")
	public String list(PunishStandardDto dto, HttpServletRequest request) {
		if (null == dto.getLevel()) {
			dto.setLevel("");
		}
		if (null == dto.getRedLineFlag()) {
			dto.setRedLineFlag(-1);
		}
		if (null == dto.getPunishObj()) {
			dto.setPunishObj(-1);
		}
		if(dto.getQcFlag()==1){
					
			dto.setCmpQcUse(1);		
		}
		if(dto.getQcFlag()==2){
			
			dto.setDevQcUse(1);		
		}
		if(dto.getQcFlag()==3){
			
			dto.setOperQcUse(1);		
		}
		if(dto.getQcFlag()==4){
			
			dto.setInnerQcUse(1);		
		}
		List<PunishStandard> psList = service.list(dto);
		request.setAttribute("psList", psList);
		
		// 处罚等级、红线标识、处罚对象、使用方
		List<String> levelList = service.getLevel(dto);
		request.setAttribute("levelList", levelList);
		request.setAttribute("dto", dto);
		
		return "qc/punishStandardList";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		PunishStandard ps = new PunishStandard();
		ps.setRedLineFlag(0);
		ps.setPunishObj(1);
		request.setAttribute("ps", ps);
		return "qc/punishStatandForm";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(PunishStandard ps, HttpServletRequest request) {
		service.add(ps);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		PunishStandard ps = service.get(id);
		request.setAttribute("ps", ps);
		return "qc/punishStatandForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(PunishStandard ps, HttpServletRequest request) {
		if (null == ps.getCmpQcUse()) {
			ps.setCmpQcUse(Constant.NO);
		}
		if (null == ps.getOperQcUse()) {
			ps.setOperQcUse(Constant.NO);
		}
		if (null == ps.getDevQcUse()) {
			ps.setDevQcUse(Constant.NO);
		}
		if (null == ps.getInnerQcUse()) {
			ps.setInnerQcUse(Constant.NO);
		}
		service.update(ps);
		return "Success";
	}
	
	
	
    /**
     * 获取处罚依据
     * @param request
     * @param punishId
     * @param flag
     * @return
     */
/*	@RequestMapping(value = "/getPunishStandard")
	public String getPunishStandard(HttpServletRequest request,@RequestParam("punishId") Integer punishId,@RequestParam("punishObj") Integer punishObj){
		
		try {
			
			PunishStandardDto dto =new PunishStandardDto();
			dto.setPunishObj(punishObj);//1 内部员工 2 供应商
			List<PunishStandard> psList = service.list(dto);
			request.setAttribute("psList", psList);
			request.setAttribute("punishId", punishId);
			request.setAttribute("punishObj", punishObj);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		   return "qc/qc_punish/qc_punishStatand_form";
	}*/
	 
	
	
	  /**
     * 获取处罚依据
     * @param request
     * @param punishId
     * @param flag
     * @return
     */
	@RequestMapping("/getPunishStandard")
	public String getPunishStandard(HttpServletRequest request,@RequestParam("punishId") Integer punishId,
			@RequestParam("useFlag") Integer useFlag,@RequestParam("punishObj") Integer punishObj){
		
		try {
			
			PunishStandardDto dto =new PunishStandardDto();
			if(useFlag == Constant.QC_COMPLAINT){//投诉质检
				
				dto.setCmpQcUse(Constant.QC_USE);
				
			}else if(useFlag == Constant.QC_OPERATE){//运营质检
				
				dto.setOperQcUse(Constant.QC_USE);
				
			}else if(useFlag == Constant.QC_DEVELOP){//研发质检
				
				dto.setDevQcUse(Constant.QC_USE);
			}
			else if(useFlag == Constant.QC_INNER){//内部质检
				
				dto.setInnerQcUse(Constant.QC_USE);
			}
			dto.setPunishObj(punishObj);//1 内部员工 2 供应商
			request.setAttribute("punishId", punishId);
			request.setAttribute("punishObj", punishObj);
			List<String> levelList  =  service.getLevel(dto);
			List<List<PunishStandard>> list = new ArrayList<List<PunishStandard>>();
			for(int i =0;i<levelList.size();i++){
				dto.setLevel(levelList.get(i));
				List<PunishStandard> psList =service.list(dto);
				list.add(psList);
				//request.setAttribute("psList_"+i, psList);
			}
			request.setAttribute("levelSize", levelList.size());
			request.setAttribute("psList", list);
			request.setAttribute("levelList", levelList);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		   return "qc/qc_punish/punishStatandForm";
	}
	
	
	
	
	/**
	 * 
	 * @param ps
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPunish")
	@ResponseBody
	public HandlerResult  addPunish(PunishStandardDto ps,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		ps.setAddPerson(user.getRealName());
		try {
			if(ps.getPunishObj() == QcConstant.PUNISHOBJ_INNER){ //内部处罚
				
				innerBasisService.addInnerPunish(ps);
				
			}else if(ps.getPunishObj() == QcConstant.PUNISHOBJ_OUTER){//外部处罚
				
				outerBasisService.addOuterPunish(ps);
				
			}
			
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		
		return result;
		
	}
}
