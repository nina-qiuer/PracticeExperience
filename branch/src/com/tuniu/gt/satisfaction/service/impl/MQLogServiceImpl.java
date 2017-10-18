package com.tuniu.gt.satisfaction.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.MQLogDao;
import com.tuniu.gt.satisfaction.service.IMQLogService;

@Service("satisfaction_service_satisfaction_impl-mqLog")
public class MQLogServiceImpl extends ServiceBaseImpl<MQLogDao> implements IMQLogService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-mqLog")
	public void setDao(MQLogDao dao) {
		this.dao = dao;
	}

	@Override
	public List getFixData(Map map) {
		return this.dao.getFixData(map);
	}


}
