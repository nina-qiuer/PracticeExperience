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
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.InnerPunishBasisDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.service.InnerPunishBasisService;


@Controller
@RequestMapping("/qc/innerPunishBasis")
public class InnerPunishBasisController {

	
    @Autowired
    private InnerPunishBasisService basisService ;
    
	@RequestMapping("/{punishId}/{useFlag}/toInnerPunishBasis")
	public String getInnerPunishBasis(@PathVariable("punishId") Integer punishId,@PathVariable("useFlag") Integer useFlag,HttpServletRequest request){
		
		  InnerPunishBasisDto innerPunish =new InnerPunishBasisDto();
		  innerPunish.setIpbId(punishId);
		  innerPunish.setPageSize(1000);
		  List<InnerPunishBasis> basisList = basisService.list(innerPunish);//获取内部处罚单对应的处罚依据
		  request.setAttribute("basisList",  basisList);
		  request.setAttribute("punishId",  punishId);
		  request.setAttribute("useFlag",  useFlag);
		  return "qc/qc_punish/innerPunishBasisForm";
		
	}
	
	
	/**
	 * 设置执行标记
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/addPunishBasis")
	@ResponseBody
	public HandlerResult addPunishBasis(HttpServletRequest request,InnerPunishBasisDto dto){
		
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		InnerPunishBasis basisBean =new InnerPunishBasis();
		basisBean.setUpdatePerson(user.getRealName());
		basisBean.setId(dto.getBasisId());
		basisBean.setIpbId(dto.getIpbId());
		try {
			
			basisService.updatePunish(basisBean);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
            e.printStackTrace();
            result.setResMsg(e.getMessage());
			result.setRetCode(Constant.SYS_FAILED);
		}
		
		return result;
		
	}
	
	
	/**
	 * 删除处罚依据
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/deleteBasis")
	@ResponseBody
	public HandlerResult deleteBasis(HttpServletRequest request,@RequestParam("id") Integer id){
		
		HandlerResult result = HandlerResult.newInstance();
		try {
			
			basisService.delete(id);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
            e.printStackTrace();
            result.setResMsg(e.getMessage());
			result.setRetCode(Constant.SYS_FAILED);
		}
		
		return result;
		
	}
}
