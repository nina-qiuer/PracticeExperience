package com.tuniu.gt.uc.dao.impl;

import com.tuniu.gt.uc.entity.PositionEntity;
import com.tuniu.gt.uc.dao.sqlmap.imap.IPositionMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("uc_dao_impl-position")
public class PositionDao extends DaoBase<PositionEntity, IPositionMap> {
	public PositionDao() {  
		tableName = Constant.appTblPreMap.get("uc") + "position";		
	}
	
	@Autowired
	@Qualifier("uc_dao_sqlmap-position")
	public void setMapper(IPositionMap mapper) {
		this.mapper = mapper;
	}
}
