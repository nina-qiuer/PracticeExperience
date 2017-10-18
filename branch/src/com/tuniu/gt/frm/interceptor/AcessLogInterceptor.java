package com.tuniu.gt.frm.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tuniu.frm.service.Common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.tuniu.gt.complaint.entity.MonitorUsePointEntity;
import com.tuniu.gt.complaint.service.MonitorUsePointServce;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

/**
 * 访问日志拦截器（登录拦截器，登录及授权）
 */
public class AcessLogInterceptor  extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-monitor_use_point")
	private MonitorUsePointServce monitorUsePointServce;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		Map<String, Object> session = actionContext.getSession();
		
		List<String> authorityUserIds = DBConfigManager.getConfigAsList("sys.allowUrls");
		//请求路径
		String requestUrl = getRequestURL(invocation);
		if(!authorityUserIds.contains(requestUrl)) { // 如果不是登录页面请求，则进行登录校验
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
			try {
				List<String> sysUserIdAllowUrls = DBConfigManager.getConfigAsList("sys.userId.allowUrls");
				if(sysUserIdAllowUrls.contains(requestUrl)){
					Map<String, String[]> salreIdMap = request.getParameterMap();
					String[] salreIdArr = salreIdMap.get("salerId");
					String salreIdStr = salreIdArr[0];
					if(StringUtil.isNotEmpty(salreIdStr)){
						Integer salerId = Integer.valueOf(salreIdStr);
						if(uid!=salerId){
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("userName", userService.getUserByID(salerId).getUserName());
							paramMap.put("password", pwd);
							userService.login(paramMap, ServletActionContext.getRequest(), ServletActionContext.getResponse());
							logger.info("loginBySalerId success:" + requstURL);
							return invocation.invoke();
						}
					}
				}
			} catch (Exception e) {
				logger.error("loginBySalerId failed:" + requstURL);
			}
			if (0 == uid) {
				session.put("referer", requstURL); // 将访问的页面丢入session
				return "login";//访问登陆页面
			} else { // 在cookie中找到了uid，则用户已单点登录
				String userStr = MemcachesUtil.get("CMP_USERAUTH_USER_" + uid);
				JSONObject jsonObj = JSONObject.fromObject(userStr);
				//UserEntity user = (UserEntity)sessionHashMap.get("user");
				UserEntity user = (UserEntity) JSONObject.toBean(jsonObj, UserEntity.class);
				if (null == user) {
					// TODO 如果user为空，则调用登录方法登录并授权
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("userName", userService.getUserByID(uid).getUserName());
					paramMap.put("password", pwd);
					userService.login(paramMap, ServletActionContext.getRequest(), ServletActionContext.getResponse());
				} else {
					OgnlValueStack stack=(OgnlValueStack)request.getAttribute("struts.valueStack");
					stack.setValue("user", user);
					request.getSession().setAttribute("user", user);
				}
			}
			try
            {
			    MonitorUsePointEntity monitor = new MonitorUsePointEntity();
	            monitor.setViewUserId(uid);
	            monitor.setUrl(requestUrl);
	            Map param = new HashMap<String, Object>();
	            param.put("userId", uid);
	            String userDeptInfo = monitorUsePointServce.getUserDeptInfo(param);
	            String userName = userDeptInfo.substring(0,userDeptInfo.indexOf(";"));
	            String deptInfo = userDeptInfo.substring(userDeptInfo.indexOf(";")+1,userDeptInfo.length()-1);
	            monitor.setUserOrg(deptInfo);
	            monitor.setViewUserName(userName);
	            monitorUsePointServce.saveMonitorUsePoint(monitor);
            }
            catch(Exception e)
            {
                Log.error("Monitor use point error",e);
            }
		}
		
		return invocation.invoke();
	}
	
	private String getRequestURL(ActionInvocation invocation) {
		String namespace = invocation.getProxy().getNamespace();
		if(namespace != null) {
			namespace = namespace.trim();
			if(namespace.length() > 0 ) {
				if(!namespace.equals("/")) {
					namespace = namespace + "/";
				}
			}
		}
		String name = invocation.getInvocationContext().getName();
		return namespace + name;
	}
	
}
