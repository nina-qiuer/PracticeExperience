package com.tuniu.gt.frm.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限拦截器，鉴权
 */
public class PrivilegeInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();  
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST); 
		Map<String, Object> sessionHashMap = actionContext.getSession();
		
		String url = request.getRequestURL().toString();
		
		// TODO 鉴权
		/*
		Map<String,List<Map<String, String>>> systemAllprivilegeList = (Map<String,List<Map<String, String>>>) ExEhcache.get("all_privileges");
		if(false) { //判断该url是否为权限的一部份
			 Map<String,List<Map<String, String>>>  userAllPrivleges = userService.loadUserPrivileges(userEntity);
			if(false) { //转到无限权页面
				return "no_privilege";
			}
		}*/
		
		return invocation.invoke();
	}
	
}
