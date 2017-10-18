package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionSocreEntity;

@Repository("satisfaction_dao_sqlmap-preSaleSatisfactionSocre")
public interface IPreSaleSatisfactionSocreMap extends IMapBase  {

	public int getSocreCountBySatisId(int satisId);
	
	public PreSaleSatisfactionSocreEntity getSocreEntityBySatisId(int satisId);
	
}
