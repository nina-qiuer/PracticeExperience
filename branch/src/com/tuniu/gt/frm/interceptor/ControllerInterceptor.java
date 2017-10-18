package com.tuniu.gt.frm.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import net.sf.json.JSONObject;
import tuniu.frm.service.Common;




public class ControllerInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse responese,
			Object paramObject) throws Exception {
		int uid = 0;
		String pwd = "";
		// 系统整合至boss后，从boss cookie中取出当前登录用户id，密码
		Cookie[] cookies = request.getCookies();
		String tuniuCrm = Common.getCookie("tuniu_crm", cookies);
		if(tuniuCrm != null) { // 如果tuniu_crm非空，则为Boss单点登录
			byte[] tuniuCrmByte = Base64.decodeBase64(tuniuCrm);
			String tuniuCrmTemp = new String(tuniuCrmByte); 
			logger.info("tuniuCrm = " + tuniuCrmTemp);
			String[] userInfo = tuniuCrmTemp.split(",");
			uid = Common.intval(userInfo[0]);
			pwd = userInfo[2];
		} else { // 独立系统登录时从cookie获取用户名密码
			uid = Common.intval(Common.getCookie("uid", cookies));
			pwd = Common.getCookie("pwd", cookies);
		}
		
		String queryString = Common.httpBuildQuery(request.getParameterMap());
		String requstURL = request.getRequestURL() + (StringUtil.isEmpty(queryString) ? "" : "?" + queryString);
		logger.info("requstURL = " + requstURL);
		if (0 != uid) {
			String userStr = MemcachesUtil.get("CMP_USERAUTH_USER_" + uid);
			JSONObject jsonObj = JSONObject.fromObject(userStr);
			UserEntity user = (UserEntity) JSONObject.toBean(jsonObj, UserEntity.class);
			if (null == user) {
				// TODO 如果user为空，则调用登录方法登录并授权
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userName", userService.getUserByID(uid).getUserName());
				paramMap.put("password", pwd);
				userService.login(paramMap, ServletActionContext.getRequest(), ServletActionContext.getResponse());
			} else {
				request.getSession().setAttribute("user", user);
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			Object paramObject, ModelAndView paramModelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject, Exception paramException)
			throws Exception {
	}
	

}
