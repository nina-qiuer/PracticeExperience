package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.TableFieldEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.ITableFieldMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-table_field")
public class TableFieldDao extends DaoBase<TableFieldEntity, ITableFieldMap>  implements ITableFieldMap {
	public TableFieldDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "table_field";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-table_field")
	public void setMapper(ITableFieldMap mapper) {
		this.mapper = mapper;
	}
}
