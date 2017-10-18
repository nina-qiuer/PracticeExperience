package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.ControlEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IControlMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-control")
public class ControlDao extends DaoBase<ControlEntity, IControlMap>  implements IControlMap {
	public ControlDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "control";	
		pk = "uid";
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-control")
	public void setMapper(IControlMap mapper) {
		this.mapper = mapper;
	}
}
