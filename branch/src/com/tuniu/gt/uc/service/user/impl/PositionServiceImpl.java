package com.tuniu.gt.uc.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.uc.dao.impl.PositionDao;
import com.tuniu.gt.uc.service.user.IPositionService;
@Service("uc_service_user_impl-position")
public class PositionServiceImpl extends ServiceBaseImpl<PositionDao> implements IPositionService {
	@Autowired
	@Qualifier("uc_dao_impl-position")
	public void setDao(PositionDao dao) {
		this.dao = dao;
	};
}
