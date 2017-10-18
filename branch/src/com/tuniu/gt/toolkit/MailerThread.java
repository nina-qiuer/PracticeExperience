package com.tuniu.gt.toolkit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.frm.entity.UserEntity;

public class MailerThread {
	
	
	private String[] recipientsArr; // 收件人
	
	private String[] ccArr; // 抄送人
	
	private String title; // 邮件标题
	
	private String content; // 邮件内容

	private IAppointManagerService appointManagerService;
	
	private HttpServletRequest request;
	
	private UserEntity user;
	
	private boolean isTestEnv; // 测试模式下不真实发送邮件
	
	
	public String[] getRecipientsArr() {
		return recipientsArr;
	}


	public void setRecipientsArr(String[] recipientsArr) {
		this.recipientsArr = recipientsArr;
	}


	public String[] getCcArr() {
		return ccArr;
	}


	public void setCcArr(String[] ccArr) {
		this.ccArr = ccArr;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public IAppointManagerService getAppointManagerService() {
		return appointManagerService;
	}


	public void setAppointManagerService(
			IAppointManagerService appointManagerService) {
		this.appointManagerService = appointManagerService;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}


	public boolean isTestEnv() {
		return isTestEnv;
	}


	public void setTestEnv(boolean isTestEnv) {
		this.isTestEnv = isTestEnv;
	}


	public MailerThread(String[] recipientsArr, String[] ccArr, String title, String content) {
		this.recipientsArr = recipientsArr;
		this.ccArr = ccArr;
		this.title = title;
		this.content = content;
		
		appointManagerService = SpringContextUtil.getBean("complaint_service_complaint_impl-appoint_manager");
		
		ServletRequestAttributes  ra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if(ra != null){
		    request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		    user = (UserEntity) request.getSession().getAttribute("user");
		}
		
		if(user == null) { //无登陆用户时基本都是对外提供接口服务触发，此时只看config.properties中的sendFlag配置
		    isTestEnv = "0".equals(Constant.CONFIG.getProperty("sendFlag"));
		}else{
		    isTestEnv =  "0".equals(Constant.CONFIG.getProperty("sendFlag")) || appointManagerService.isMemberOfType(user.getId()==null?0:user.getId(), AppointManagerEntity.DEV_OFFICER);
		}
	}
	
	/*@Override
	public void run() {
		logger.info("Send Email " + title + " Bgn...");
		EmailData data  = null;
		if (isTestEnv) {
		    String newContent = "主送：" + CommonUtil.arrToStr(recipientsArr) + "<br>" + "抄送：" + CommonUtil.arrToStr(ccArr) + "<br><br>" 
	                + "---------------------------------------------------------------------------------------------" 
	                + "-----------------------------------------------------------------------------<br><br>" + content;
	            data = new EmailData("robot@tuniu.com", 
	                    new String[]{"jiangye@tuniu.com","zhanchengzong@tuniu.com"}, 
	                    new String[]{"jiangye@tuniu.com"}, title, newContent, "html");
		} else {
		    data = new EmailData("robot@tuniu.com", recipientsArr, ccArr, title, content, "html");
		}
		Mailer.sendEmail(data);
		logger.info("Send Email " + title + " End...");
	}*/


}
