package com.tuniu.qms.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.model.User;

@Controller
@RequestMapping("/common/main")
public class MainController {
	
	@RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("menuList", getMenuList(request));
		
        return "common/index";
    }
	
	private List<Resource> getMenuList(HttpServletRequest request) {
		
		List<Resource> menuList = new ArrayList<Resource>();
		User user = (User) request.getSession().getAttribute("loginUser");
		if (null != user && null != user.getRole()) {
			List<Resource> resList = user.getRole().getResourceList();
			for (Resource res : resList) {
				if (1 == res.getMenuFlag()) {
					menuList.add(res);
				}
			}
		}
		return menuList;
	}
	
	@RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session会随之销毁
		}
		
        return "redirect:" + Config.get("shiro.logoutUrl");
    }

}
