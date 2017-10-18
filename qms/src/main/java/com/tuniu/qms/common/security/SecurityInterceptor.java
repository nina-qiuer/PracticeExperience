package com.tuniu.qms.common.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.common.model.BusinessLog;
import com.tuniu.qms.common.model.Resource;
import com.tuniu.qms.common.model.Role;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.BusinessLogService;
import com.tuniu.qms.common.service.ResourceService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;

public class SecurityInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resService;
	
	@Autowired
	private BusinessLogService logService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String requestUri = request.getRequestURI().replace(request.getContextPath() + "/", "");
		User user = null;
		if (requestUri.startsWith("access")) { // 对外接口
			return true;
		} else {
			/* 重复提交校验Bgn */
			if (isRepeatSubmit(request)) {
				System.out.println("重复提交！~");
				request.getRequestDispatcher("/authInsufficient.jsp").forward(request, response);
				return false;
			}
			/* 重复提交校验End */
			
			user = getLoginUser(request);
		}
		
		Resource res = getRequestRes(requestUri, request);
		if (null == res) { // 如果访问的是非注册资源（给外系统提供的接口等），则不鉴权
			return true;
		} else {
    		/* 鉴权 */
    		if (null != user && null != user.getRole()) {
    			List<Resource> resList = user.getRole().getResourceList();
    			for (Resource r : resList) {
    				if (res.getId().equals(r.getId())) {
    					return true;
    				}
    			}
    		}
		}
        
        request.getRequestDispatcher("/authInsufficient.jsp").forward(request, response);
		return false;
	}
	
	/** 重复提交校验 */
	private boolean isRepeatSubmit(HttpServletRequest request) {
		String clinetTokenKey = request.getParameter("tokenKey");
        if (null != clinetTokenKey) { // 客户端未传tokenKey则不校验
        	String clinetTokenValue = request.getParameter("tokenValue");
        	String serverTokenValue = (String) request.getSession().getAttribute(clinetTokenKey);
            if (null == serverTokenValue) { // 如果服务端tokenValue为空，则之前已经有请求匹配成功，本次请求为重复提交
                return true;
            }
            if (!serverTokenValue.equals(clinetTokenValue)) { // 如果服务端tokenValue不等于客户端tokenValue，则客户端tokenValue被修改过，很可能是恶意重复提交
                return true;
            }
        }
        request.getSession().removeAttribute(clinetTokenKey);
        return false;
    }
	
	/** 查询当前登录用户及其权限 */
	private User getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (null == loginUser) {
        	Subject subject = SecurityUtils.getSubject();
            if (null != subject) {
                String userName = subject.getPrincipal().toString();
                if (StringUtils.isNoneBlank(userName)) {
                	loginUser = userService.getUserByUserName(userName); // 查询用户及其权限
                	if (null != loginUser) {
                		if (7579 == loginUser.getId()) { // wangmingfang
            				Role role = new Role();
            				role.setName("Super Manager");
            				role.setType(Constant.ROLE_ADMINISTRATOR);
            				List<Resource> resList = resService.getResDataCache();
            				role.setResourceList(resList);
            				loginUser.setRole(role);
            			}
                		session.setAttribute("loginUser", loginUser); // 缓存权限
                		
                		List<Resource> resList = loginUser.getRole().getResourceList();
                		List<String> widgetCodes = new ArrayList<String>();
                		for (Resource res : resList) {
                			if (StringUtils.isNotBlank(res.getWidgetCode())) {
                				widgetCodes.add(res.getWidgetCode());
                			}
                		}
                		session.setAttribute("loginUser_WCS", widgetCodes); // 缓存控件编码
                	}
                }
            }
        }
        return loginUser;
	}
	
	/** 查找访问的资源 */
	private Resource getRequestRes(String requestUri, HttpServletRequest request) {
		Resource requestRes = null;
		List<Resource> resList = resService.getResDataCache();
    	for (Resource res : resList) {
    		String url = res.getUrl();
    		if (requestUri.matches(url)) { // common/role/[0-9]*/authConfig
    			requestRes = res;
    			request.setAttribute("requestRes", res);
    		}
    	}
		return requestRes;
	}

	/* 在业务处理请求执行完成后，生成视图之前执行的动作 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {
		HttpSession session = request.getSession();
		if (null != session) {
			String tokenKey = UUID.randomUUID().toString();
			String tokenValue = UUID.randomUUID().toString();
			session.setAttribute("tokenKey", tokenKey);
			session.setAttribute(tokenKey, tokenValue);
			request.setAttribute("tokenValue", tokenValue);
			
			Resource res = (Resource) request.getAttribute("requestRes");
			if (null != res && Constant.CUD_OPERATE == res.getOperType()) { // 如果是增删改操作，则记录业务操作日志
				User user = (User) session.getAttribute("loginUser");
				
				String requestUri = request.getRequestURI().replace(request.getContextPath(), ""); // 访问路径
				Map argsMap = request.getParameterMap(); // 访问参数
				
				BusinessLog log = new BusinessLog();
				log.setClientIP(request.getRemoteAddr()); // 由于服务器配置了负载均衡，IP可能并不是客户端的真实IP
				log.setAddPerson(user.getRealName());
				log.setResName(res.getName());
				log.setRequestUri(requestUri);
				log.setRequestArgs(JSON.toJSONString(argsMap));
				logService.add(log);
			}
		}
	}
	
	/* 在DispatcherServlet完全处理完请求后被调用，当有拦截器抛出异常时，会从当前拦截器往回执行所有的拦截器的afterCompletion() */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) throws Exception {
		
	}

}
