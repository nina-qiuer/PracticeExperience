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
import com.tuniu.qms.qc.dto.OuterPunishBasisDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.service.OuterPunishBasisService;


@Controller
@RequestMapping("/qc/outerPunishBasis")
public class OuterPunishBasisController {

	
    @Autowired
    private OuterPunishBasisService basisService ;
    
	@RequestMapping("/{punishId}/{useFlag}/toOuterPunishBasis")
	public String getOuterPunishBasis(@PathVariable("punishId") Integer punishId,@PathVariable("useFlag") Integer useFlag,HttpServletRequest request){
		
		
		  OuterPunishBasisDto outerPunish =new OuterPunishBasisDto();
		  outerPunish.setOpbId(punishId);
		  List<OuterPunishBasis> basisList = basisService.list(outerPunish);//获取外部处罚单对应的处罚依据
		  request.setAttribute("basisList",  basisList);
		  request.setAttribute("punishId",  punishId);
		  request.setAttribute("useFlag",  useFlag);
		  return "qc/qc_punish/outerPunishBasisForm";
		
	}
	
	
	/**
	 * 设置执行标记并更新外部处罚单金额和分数
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/addPunishBasis")
	@ResponseBody
	public HandlerResult addPunishBasis(HttpServletRequest request,OuterPunishBasisDto dto){
		
		HandlerResult result = HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		OuterPunishBasis basisBean =new OuterPunishBasis();
		basisBean.setUpdatePerson(user.getRealName());
		basisBean.setId(dto.getBasisId());
		basisBean.setOpbId(dto.getOpbId());
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
