package com.tuniu.gt.satisfaction.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.IMQLogMap;
import com.tuniu.gt.satisfaction.entity.MQLogEntity;

@Repository("satisfaction_dao_impl-mqLog")
public class MQLogDao  extends DaoBase<MQLogEntity, IMQLogMap>  implements IMQLogMap {

	public MQLogDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "mq_log";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-mqLog")
	public void setMapper(IMQLogMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List getFixData(Map map) {
		return mapper.getFixData(map);
	}


	
	
}
