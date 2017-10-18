package com.tuniu.gt.complaint.dao.impl;

import com.tuniu.gt.complaint.entity.QualityToolEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQualityToolMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-quality_tool")
public class QualityToolDao extends DaoBase<QualityToolEntity, IQualityToolMap> {
	public QualityToolDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "quality_tool";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-quality_tool")
	public void setMapper(IQualityToolMap mapper) {
		this.mapper = mapper;
	}
	
}
