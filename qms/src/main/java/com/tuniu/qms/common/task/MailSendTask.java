package com.tuniu.qms.common.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.service.MailService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.TspService;

public class MailSendTask {
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private TspService tspService;
	
	private final static Logger logger = LoggerFactory.getLogger(MailSendTask.class);
	
	public void sendEmail() {
		List<MailTask> mtList = mailTaskService.list(new MailTaskDto());
		for (MailTask mail : mtList) {
			mailTaskService.increSendTimes(mail.getId());
			//mailService.sendEmail(mail);
			//调用TSP服务发送邮件
			boolean flag = tspService.sendMail(mail);
			if(flag){
				
				mailTaskService.delete(mail.getId());
				logger.info("sendMail success:"+mail.getId());
			}
			
		}
	}
	
}
