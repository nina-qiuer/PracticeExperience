package com.tuniu.qms.common.service;

import com.tuniu.qms.common.model.MailTask;

public interface MailService {
	
	boolean sendEmail(MailTask mail);
	
}
