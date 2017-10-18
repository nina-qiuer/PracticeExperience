package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.LogEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.ILogMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-log")
public class LogDao extends DaoBase<LogEntity, ILogMap>  implements ILogMap {
	public LogDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "log";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-log")
	public void setMapper(ILogMap mapper) {
		this.mapper = mapper; 
	}


	
	
}
