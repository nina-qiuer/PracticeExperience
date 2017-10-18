package com.tuniu.qms.qc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.SendMailConfigDto;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.SendMailConfigService;

@Controller
@RequestMapping("/qc/sendMailConfig")
public class SendMailConfigController {

	@Autowired
	private SendMailConfigService service;
	
	@Autowired
	private UserService  userService;;

	@RequestMapping("/list")
	public String list(SendMailConfigDto dto,HttpServletRequest request) {
		List<SendMailConfig> mailConfigList  = service.list(dto);
		request.setAttribute("dataList", mailConfigList);
		request.setAttribute("dto", dto);
		return "qc/sendMailConfigList";
	}
	
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		SendMailConfig mc = new SendMailConfig();
		mc.setId(0);
		request.setAttribute("mc", mc);
		return "qc/sendMailConfigForm";
	}

	@RequestMapping("/add")
	@ResponseBody
	public HandlerResult add(SendMailConfig model, HttpServletRequest request) {
		HandlerResult result = HandlerResult.newInstance();
		try {
			
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("mailType", model.getMailType());
			int count = service.getExistConfig(map);
			if(count>0){
				
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("已存在配置类型");
				return result;
			}
			service.add(model);
		    result.setRetCode(Constant.SYS_SUCCESS);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("新增失败");
		}
	    
	    return result;
		
	}

	@RequestMapping("/{id}/delete")
	@ResponseBody
	public HandlerResult delete(@PathVariable("id") Integer id) {
		
		HandlerResult result = HandlerResult.newInstance();
		
		try {
			
			service.delete(id);
		    result.setRetCode(Constant.SYS_SUCCESS);
			 
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}
		
		return result;
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		SendMailConfig mc = service.get(id);
		request.setAttribute("mc", mc);
		return "qc/sendMailConfigForm";
	}

	@RequestMapping("/update")
	@ResponseBody
	public HandlerResult update(SendMailConfig model, HttpServletRequest request) {
	
	HandlerResult result = HandlerResult.newInstance();
		
		try {
			
			service.update(model);
		    result.setRetCode(Constant.SYS_SUCCESS);
			 
		  } catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
	
		return result;
	}

}