package com.tuniu.qms.common.dao;

import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.dao.base.BaseMapper;

public interface MailTaskMapper extends BaseMapper<MailTask, MailTaskDto> {
	
	void increSendTimes(Integer mailId);
	
}