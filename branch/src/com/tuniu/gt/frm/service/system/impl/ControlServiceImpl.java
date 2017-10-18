package com.tuniu.gt.frm.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.ControlDao;
import com.tuniu.gt.frm.service.system.IControlService;
@Service("frm_service_system_impl-control")
public class ControlServiceImpl extends ServiceBaseImpl<ControlDao> implements IControlService {
	@Autowired
	@Qualifier("frm_dao_impl-control")
	public void setDao(ControlDao dao) {
		this.dao = dao;
	};
}
