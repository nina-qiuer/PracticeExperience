package com.tuniu.gt.warning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.warning.dao.impl.EarlyWarningDao;
import com.tuniu.gt.warning.service.IEarlyWarningService;

@Service("warning_service_impl-early_warning")
public class EarlyWarningServiceImpl extends ServiceBaseImpl<EarlyWarningDao> implements IEarlyWarningService {
	
	@Autowired
	@Qualifier("warning_dao_impl-early_warning")
	public void setDao(EarlyWarningDao dao) {
		this.dao = dao;
	}
	
}
