package com.tuniu.gt.frm.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IEmailService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

@Service
public class DeputyDeptMgrEmailService implements IEmailService {

	@Autowired
	@Qualifier("frm_service_system_impl-user")
    private IUserService userService;
	
	@Override
	public List<String> getReceiverEmails(ComplaintEntity entity) {
		List<UserEntity> deputyManagerList = new ArrayList<UserEntity>();
		List<String> receivers = new ArrayList<String>();
		if (StringUtil.isNotEmpty(entity.getOperateName())) {
			deputyManagerList = userService.getDeputyManagerByProducterName(entity.getProductLeader());
			for (UserEntity receiver : deputyManagerList) {
				receivers.add("'" + receiver.getRealName() + "'");
			}
		}
		
		List<UserEntity> recipients = new ArrayList<UserEntity>();
		String realNames = CommonUtil.arrToStr(receivers.toArray(new String[receivers.size()]));
		
		List<String> emails = new ArrayList<String>();
		if (StringUtil.isNotEmpty(realNames)) {
			recipients = userService.getUsers("realNames", realNames);
		}
		for (UserEntity userEntity : recipients) {
			emails.add(userEntity.getEmail());
		}
		
		return emails;
	}

}
