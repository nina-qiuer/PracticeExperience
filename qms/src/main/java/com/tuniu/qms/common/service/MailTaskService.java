package com.tuniu.qms.common.service;

import java.util.Map;

import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.service.base.BaseService;

public interface MailTaskService extends BaseService<MailTask, MailTaskDto> {
	
	void addTask(MailTaskDto dto);
	
	void increSendTimes(Integer mailId);
	
	String getMailText(String templateName, Map<String, Object> dataMap);
	
}
