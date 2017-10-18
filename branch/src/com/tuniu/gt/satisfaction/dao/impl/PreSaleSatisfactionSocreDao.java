package com.tuniu.gt.satisfaction.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.IPreSaleSatisfactionSocreMap;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionSocreEntity;

@Repository("satisfaction_dao_impl-preSaleSatisfactionSocre")
public class PreSaleSatisfactionSocreDao  extends DaoBase<PreSaleSatisfactionSocreEntity, IPreSaleSatisfactionSocreMap>  implements IPreSaleSatisfactionSocreMap {

	public PreSaleSatisfactionSocreDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "pre_sale_satisfaction_socre";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-preSaleSatisfactionSocre")
	public void setMapper(IPreSaleSatisfactionSocreMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getSocreCountBySatisId(int satisId) {
		return mapper.getSocreCountBySatisId(satisId);
	}

	@Override
	public PreSaleSatisfactionSocreEntity getSocreEntityBySatisId(int satisId) {
		return mapper.getSocreEntityBySatisId(satisId);
	}
	
}
