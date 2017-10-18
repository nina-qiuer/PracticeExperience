package com.tuniu.gt.satisfaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.SignSatisfactionMsgSendDao;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionMsgSendService;

@Service("satisfaction_service_satisfaction_impl-signSatisfactionMsgSend")
public class SignSatisfactionMsgSendServiceImpl extends ServiceBaseImpl<SignSatisfactionMsgSendDao> implements ISignSatisfactionMsgSendService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-signSatisfactionMsgSend")
	public void setDao(SignSatisfactionMsgSendDao dao) {
		this.dao = dao;
	}

}
