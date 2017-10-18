package com.tuniu.gt.uc.dao.impl;

import com.tuniu.gt.uc.entity.JobEntity;
import com.tuniu.gt.uc.dao.sqlmap.imap.IJobMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("uc_dao_impl-job")
public class JobDao extends DaoBase<JobEntity, IJobMap> {
	public JobDao() {  
		tableName = Constant.appTblPreMap.get("uc") + "job";		
	}
	
	@Autowired
	@Qualifier("uc_dao_sqlmap-job")
	public void setMapper(IJobMap mapper) {
		this.mapper = mapper;
	}
}
