package com.tuniu.qms.access.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.service.CmpImproveService;

/**
 * 投诉改进报告
 * @author zhangsensen
 *
 */

@Controller
@RequestMapping("/access/cmpImprve")
public class CmpImproveServer {
	
	private static final Logger logger = LoggerFactory.getLogger(CmpImproveServer.class);
	
	@Autowired
	private CmpImproveService cmpImproveService;
	
	@Autowired
	private QcPointAttachService qcPointAttachService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 接收cmp中客服填写数据，生成改进报告
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addImproveBill", method = RequestMethod.POST)
	public void addImproveBill(HttpServletRequest request, HttpServletResponse response){
		RestServer server = new RestServer(request, response);
		ResponseDto dto = new ResponseDto();//返回值
		try{
			CmpImprove cmpImprove = server.getRestData(CmpImprove.class);
			
			boolean successFlag = cmpImproveService.cmpSubmitImpBill(cmpImprove);
			if(successFlag){
				saveImproveAttach(cmpImprove); //OA单据生成成功，把附件存储了
				
				dto.setData(cmpImprove.getId());
			}else{//生成失败
				logger.info("改进报告生成失败，投诉单号: " + cmpImprove.getCmpId());
				dto.setSuccess(false);
				dto.setMsg("投诉改进报告生成失败！");
			}
		    
		}catch(Exception e){
			dto.setSuccess(false);
			dto.setMsg("投诉改进报告生成失败！");
			logger.error("投诉改进报告生成失败 ", e);
		}
		
		server.writeResponse(dto);
	}
	
	private void saveImproveAttach(CmpImprove cmpImprove) {
		
		for(Map<String, Object> data : cmpImprove.getAttachList()){
			QcPointAttach attach = new QcPointAttach();
			attach.setName(data.get("name").toString());
			attach.setPath(data.get("path").toString());
			attach.setType(data.get("type").toString());
			attach.setBillType(Constant.ATTACH_BILL_TYPE_IMPROVE);//改进报告
			attach.setQcId(cmpImprove.getId());//改进报告id
			
			qcPointAttachService.add(attach);
		}
	}

	/**
	 * 接收OA系统数据，更新改进报告
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateImproveBill", method = RequestMethod.POST)
	public void updateImproveBill(HttpServletRequest request, HttpServletResponse response){
		RestServer server = new RestServer(request, response);
		ResponseDto dto = new ResponseDto();//返回值
		try{
			CmpImprove cmpImprove = server.getRestData(CmpImprove.class);
			
			fillBillInfo(cmpImprove);
			
			cmpImproveService.update(cmpImprove);
			dto.setData("改进报告更新成功");
		}catch(Exception e){
			dto.setSuccess(false);
			dto.setMsg("投诉改进报告更新失败！");
			logger.error("投诉改进报告更新失败 ", e);
		}
		
		server.writeResponse(dto);
	}
	
	/**
	 * 改进报告回写信息填充
	 * @param cmpImprove
	 */
	private void fillBillInfo(CmpImprove cmpImprove) {
		
		if(null == cmpImprove.getOtherPersonCode() || cmpImprove.getOtherPersonCode() == 0){//没有其他责任人
			cmpImprove.setState(Constant.IMPROVE_STATE_FINISH);//已完结
		}else{
			cmpImprove.setState(Constant.IMPROVE_STATE_WAIT_CONFIRM);//待确认
			User user = userService.get(cmpImprove.getOtherPersonCode());//根据id查找员工，oa传的姓名没有数字
			if(user != null){
				cmpImprove.setOtherPerson(user.getRealName());
			}
			cmpImprove.setHandlePerson("");//待确认状态处理人为""
		}
		
		if(cmpImprove.getIsRespFlag() == 1){//是否有责，无责待确认
			cmpImprove.setState(Constant.IMPROVE_STATE_WAIT_CONFIRM);//待确认
			cmpImprove.setHandlePerson("");//待确认状态处理人为""
		}			
		
		cmpImprove.setId(cmpImprove.getImpId());
		cmpImprove.setOtherAgencyName(cmpImprove.getAgencyName());
		
	}
}
