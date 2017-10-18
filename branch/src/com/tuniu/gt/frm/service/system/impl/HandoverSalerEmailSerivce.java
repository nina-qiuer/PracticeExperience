package com.tuniu.gt.frm.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IEmailService;
import com.tuniu.gt.frm.service.system.IUserService;

@Service
public class HandoverSalerEmailSerivce implements IEmailService {

	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
    private IUserService userService;
	
	@Override
	public List<String> getReceiverEmails(ComplaintEntity entity) {

		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("orderId", entity.getOrderId());
		JSONObject paramJson = JSONObject.fromObject(paramMap);
		String handoverSaler = tspService.getHandoverSaler(paramJson);
		List<UserEntity> recipients = new ArrayList<UserEntity>();
		if (!StringUtils.isEmpty(handoverSaler)) {
			recipients = userService.getUsers("realNames", "'" + handoverSaler + "'");
		}
		
		List<String> emails = new ArrayList<String>();
		for (UserEntity userEntity : recipients) {
			emails.add(userEntity.getEmail());
		}
		
		return emails;
	}

}
