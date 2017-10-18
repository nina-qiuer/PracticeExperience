package com.tuniu.gt.frm.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.LogDao;
import com.tuniu.gt.frm.service.log.ILogService;
@Service("frm_service_log_impl-log")
public class LogServiceImpl extends ServiceBaseImpl<LogDao> implements ILogService {
	@Autowired
	@Qualifier("frm_dao_impl-log")
	public void setDao(LogDao dao) {
		this.dao = dao;
	};
}
