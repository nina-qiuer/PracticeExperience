package {?app_dot_dir}{?app_id}.dao.impl;

import {?app_dot_dir}{?app_id}.entity.{?ufirst_table_name}Entity;
import {?app_dot_dir}{?app_id}.dao.sqlmap.imap.I{?ufirst_table_name}Map; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("{?app_id}_dao_impl-{?table_name}")
public class {?ufirst_table_name}Dao extends DaoBase<{?ufirst_table_name}Entity, I{?ufirst_table_name}Map> {
	public {?ufirst_table_name}Dao() {  
		tableName = Constant.appTblPreMap.get("{?app_id}") + "{?table_name}";		
	}
	
	@Autowired
	@Qualifier("{?app_id}_dao_sqlmap-{?table_name}")
	public void setMapper(I{?ufirst_table_name}Map mapper) {
		this.mapper = mapper;
	}
}