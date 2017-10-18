package com.tuniu.gt.frm.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IEmailService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

@Service
public class CustManagerEmailService implements IEmailService {

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	@Override
	public List<String> getReceiverEmails(ComplaintEntity complaint) {
		List<String> emails = new ArrayList<String>();
		Integer custSalerId = ComplaintRestClient.getCustSaler(complaint.getCustId());
		UserEntity custSaler = userService.getUserByID(custSalerId);
		if (custSaler != null && StringUtil.isNotEmpty(custSaler.getRealName())) {
			UserEntity custSalerManager = ComplaintRestClient.getReporter(custSaler.getRealName());
			if (custSalerManager != null && StringUtil.isNotEmpty(custSalerManager.getEmail())) {
				emails.add(custSalerManager.getEmail());
			}
		}
		return emails;
	}

}
