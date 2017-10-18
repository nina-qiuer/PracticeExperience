package com.tuniu.qms.qc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.model.MailConfig;
import com.tuniu.qms.qc.service.MailConfigService;

@Controller
@RequestMapping("/qc/mailConfig")
public class MailConfigController {

	@Autowired
	private MailConfigService service;
	
	@Autowired
	private UserService  userService;;

	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		List<MailConfig> mailConfigList  = service.list(null);
		request.setAttribute("dataList", mailConfigList);
		return "qc/mailConfigList";
	}
	
	@RequestMapping("/{addPersonId}/toChoose")
	public String toChoose(HttpServletRequest request,@PathVariable("addPersonId") Integer addPersonId) {
		
	    User user = userService.get(addPersonId);
	    if(null!=user){
	    	
	    	  request.setAttribute("email", user.getEmail());
	    	  
	    }else{
	    	
	    	 request.setAttribute("email", "");
	    }
	    List<MailConfig> mailConfigList  = service.list(null);
        request.setAttribute("dataList", mailConfigList);
      
        return "qc/chooseMailConfig";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		MailConfig mc = new MailConfig();
		request.setAttribute("mc", mc);
		return "qc/mailConfigForm";
	}

	@RequestMapping("/add")
	@ResponseBody
	public String add(MailConfig model, HttpServletRequest request) {
		service.add(model);
		return "success";
	}

	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		MailConfig mc = service.get(id);
		request.setAttribute("mc", mc);
		return "qc/mailConfigForm";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String update(MailConfig model, HttpServletRequest request) {
		service.update(model);
		return "success";
	}

}