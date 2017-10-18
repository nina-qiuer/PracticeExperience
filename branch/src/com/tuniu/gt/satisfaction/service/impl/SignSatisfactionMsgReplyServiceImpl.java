package com.tuniu.gt.satisfaction.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.SignSatisfactionMsgReplyDao;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionMsgReplyService;

@Service("satisfaction_service_satisfaction_impl-signSatisfactionMsgReply")
public class SignSatisfactionMsgReplyServiceImpl extends ServiceBaseImpl<SignSatisfactionMsgReplyDao> implements ISignSatisfactionMsgReplyService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-signSatisfactionMsgReply")
	public void setDao(SignSatisfactionMsgReplyDao dao) {
		this.dao = dao;
	}

	@Override
	public void fixSatisfactionSocre(Map map) {
		dao.fixSatisfactionSocre(map);
		
	}

}
