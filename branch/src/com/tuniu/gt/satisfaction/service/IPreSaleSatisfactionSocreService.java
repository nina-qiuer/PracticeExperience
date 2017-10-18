package com.tuniu.gt.satisfaction.service;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionSocreEntity;

public interface IPreSaleSatisfactionSocreService extends IServiceBase {
	
	public int getSocreCountBySatisId(int satisId);
	
	public PreSaleSatisfactionSocreEntity getSocreEntityBySatisId(int satisId);
	
}
