package com.tuniu.gt.satisfaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.PreSaleSatisfactionSocreDao;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionSocreEntity;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionSocreService;

@Service("satisfaction_service_satisfaction_impl-preSaleSatisfactionSocre")
public class PreSaleSatisfactionSocreServiceImpl extends ServiceBaseImpl<PreSaleSatisfactionSocreDao> implements IPreSaleSatisfactionSocreService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-preSaleSatisfactionSocre")
	public void setDao(PreSaleSatisfactionSocreDao dao) {
		this.dao = dao;
	}

	@Override
	public int getSocreCountBySatisId(int satisId) {
		return this.dao.getSocreCountBySatisId(satisId);
	}

	@Override
	public PreSaleSatisfactionSocreEntity getSocreEntityBySatisId(int satisId) {
		return this.dao.getSocreEntityBySatisId(satisId);
	}

}
