package com.tuniu.gt.frm.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

@Service("frm-login")
@Scope("prototype")
public class LoginAction extends FrmBaseAction<IUserService, UserEntity> implements ServletResponseAware {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String errorMsg;
	
	@Override
	public void setServletRequest(HttpServletRequest httpservletrequest) {
		this.request = httpservletrequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * 重定向的url地址
	 */
	private String redirctUrl = "";

	public String getRedirctUrl() {
		return redirctUrl;
	}

	public String doLogin() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", user.getUserName());
		paramMap.put("password", Common.encodePassword(user.getPassword()));
		
		String system = request.getParameter("system");
		if(StringUtils.isNotEmpty(system)){
			session.put("system", system);
		}
		
		if (!userService.login(paramMap, request, response)) {
			errorMsg = "用户名/密码错误";
			return "login";
		}
		
		if (session.get("referer") != null) { // 转至之前记忆的页面
			redirctUrl = session.get("referer").toString();
		}
		
		if (redirctUrl == null || redirctUrl.equals("") || redirctUrl.indexOf("login") != -1) {
			redirctUrl = Constant.CONFIG.getProperty("app_url").toString() + "frm/action/index";
		}
		
		
		return "redirect";
	}

	public String doLogout() {
		Common.setCookie("sid", "", 0, response);
		Common.setCookie("uid", "0", 0, response);
		Common.setCookie("pwd", "", 0, response);
		redirctUrl = Constant.CONFIG.getProperty("app_url").toString()
				+ "frm/action/index";
		return "redirect";
	}

}
