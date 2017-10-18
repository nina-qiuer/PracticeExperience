package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.TableEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.ITableMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-table")
public class TableDao extends DaoBase<TableEntity, ITableMap>  implements ITableMap {
	public TableDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "table";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-table")
	public void setMapper(ITableMap mapper) {
		this.mapper = mapper;
	}
}
