package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;
public interface ICcEmailService extends IServiceBase {
	
	List<String> getCcEmails(Map<String, Object> params);
	
}
