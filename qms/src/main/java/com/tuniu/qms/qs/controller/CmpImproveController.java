package com.tuniu.qms.qs.controller;


import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qs.dto.CmpImproveDto;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.service.CmpImproveService;

/**
 * 投诉改进报告
 * @author zhangsensen
 *
 */
@Controller
@RequestMapping("qs/cmpImprove")
public class CmpImproveController {
	
	private static final Logger logger = LoggerFactory.getLogger(CmpImproveController.class);
	
	@Autowired
	private CmpImproveService cmpImproveService;
	
	@Autowired
	private QcPointAttachService qcPointAttachService;
	
	/**
	 * 改进报告列表
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(CmpImproveDto dto, HttpServletRequest request){
		if(null == dto.getState() || 0 == dto.getState()){
			dto.setState(2);
		}
		cmpImproveService.loadPage(dto);
		
		request.setAttribute("dto", dto);
		return "qs/improve/list";
	}
	
	/**
	 * 根据id查看改进报告
	 * @param impId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{impId}/impReport",  method = RequestMethod.GET)
	public String impReportWithId(@PathVariable("impId") Integer impId, HttpServletRequest request){
		//当前id的改进报告
		CmpImprove cmpImprove = cmpImproveService.get(impId);
		
		List<QcPointAttach> attachList = qcPointAttachService.getAttachList(impId, Constant.ATTACH_BILL_TYPE_IMPROVE);
		
		request.setAttribute("cmpImprove", cmpImprove);
		request.setAttribute("attachList", attachList);
		return "qs/improve/reportWithId";
	}
	
	/**
	 * 查看该投诉单下的改进报告
	 * @param cmpId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{cmpId}/reportWithCmpId")
	public String impReportWithCmpId(@PathVariable("cmpId") Integer cmpId, HttpServletRequest request){
		List<CmpImprove> cmpImproveList = cmpImproveService.listByCmpId(cmpId);
		
		request.setAttribute("cmpImproveList", cmpImproveList);
		return "qs/improve/reportwithCmpId";
	}
	
	/**
	 * 新增投诉改进报告页面，初始化改进报告
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		CmpImprove cmpImprove = new CmpImprove();
		cmpImprove.setAddPerson(user.getRealName());
		cmpImprove.setDelFlag(Constant.YES);
		cmpImproveService.add(cmpImprove);
		
		request.setAttribute("cmpImprove", cmpImprove);
		request.setAttribute("addFlag", true);//新增标识位
		return "qs/improve/addForm";
	}
	
	/**
     * 初始化附件
     * @param request
     * @return
     */
    @RequestMapping("{id}/toShowAttach")
    public String toShowAttach(HttpServletRequest request, @PathVariable("id")Integer id){
    	List<QcPointAttach> attachList = qcPointAttachService.getAttachList(id, Constant.ATTACH_BILL_TYPE_IMPROVE);
    	
		request.setAttribute("list", attachList);		
		request.setAttribute("impId", id);		 
        return "qs/improve/attachList";
        
    }
	
	/**
	 * 更新改进报告（定责）
	 * @param impId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{impId}/toUpdate",  method = RequestMethod.GET)
	public String toUpdate(@PathVariable("impId") Integer impId, HttpServletRequest request){
		//当前id的改进报告
		CmpImprove cmpImprove = cmpImproveService.get(impId);
		
		List<QcPointAttach> attachList = qcPointAttachService.getAttachList(impId, Constant.ATTACH_BILL_TYPE_IMPROVE);
		
		request.setAttribute("cmpImprove", cmpImprove);
		request.setAttribute("attachList", attachList);
		request.setAttribute("addFlag", false);//新增标识位
		return "qs/improve/addForm";
	}
	
	/**
	 * 分配处理人
	 * @param obj
	 * @param request
	 * @return
	 */
	@RequestMapping("/assignDelPerson")
	@ResponseBody
	public HandlerResult assignDelPerson(CmpImproveDto dto, HttpServletRequest request){
		  HandlerResult result = new HandlerResult();
          User user = (User) request.getSession().getAttribute("loginUser");
          try {
        	  cmpImproveService.updateHandlePerson(dto.getImpBillIds(), dto.getAssignTo(), user);
              result.setRetCode(Constant.SYS_SUCCESS);
          } catch (Exception e) {
              result.setRetCode(Constant.SYS_FAILED);
              result.setResMsg("分配失败");
          }
          
          return result;
	}
	
	/**
	 * 保存改进报告
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/addImproveBill")
	@ResponseBody
	public HandlerResult saveImpBill(CmpImprove obj, HttpServletRequest request){
		HandlerResult result = HandlerResult.newInstance();
		try{
			fillImproveBillInfo(obj, result);//填充其他信息
			
			if(!(Constant.SYS_FAILED).equals(result.getRetCode())){//判断成功
				User user = (User) request.getSession().getAttribute("loginUser");
				obj.setAddPerson(user.getRealName());
				cmpImproveService.add(obj);
				result.setRetCode(Constant.SYS_SUCCESS);
			}
		}catch(Exception e){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("改进报告发起失败！");
			logger.error("改进报告发起失败！", e);
		}
		return result;
	}*/
	
	/**
	 * 提交改进报告
	 * @param request
	 * @return
	 */
	@RequestMapping("/submitImproveBill")
	@ResponseBody
	public HandlerResult submitImproveBill(HttpServletRequest request, CmpImprove obj){
		HandlerResult result = HandlerResult.newInstance();
		try{
			User user = (User) request.getSession().getAttribute("loginUser");
			obj.setUpdatePerson(user.getRealName());
			
			String errorMsg = cmpImproveService.localSubmitImpBill(obj);//提交
			
			if(!("").equals(errorMsg)){
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg(errorMsg);
			}else{
				result.setRetCode(Constant.SYS_SUCCESS);
			}
			
		}catch(Exception e){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("改进报告发起失败！");
			logger.error("改进报告发起失败！", e);
		}
		return result;
	}
	
	/**
	 * 改进报告完结
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping("finishImproveBill")
	@ResponseBody
	public HandlerResult finishImproveBill(HttpServletRequest request, CmpImprove obj){
		HandlerResult result = HandlerResult.newInstance();
		try{
			User user = (User) request.getSession().getAttribute("loginUser");
			obj.setUpdatePerson(user.getRealName());
			obj.setState(Constant.IMPROVE_STATE_FINISH);//完结
			
			cmpImproveService.update(obj);
			result.setRetCode(Constant.SYS_SUCCESS);
		}catch(Exception e){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("完结失败！");
			logger.error("完结失败！", e);
		}
		return result;
	}
	
}
