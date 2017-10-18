package com.tuniu.gt.workorder.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.workorder.dao.sqlmap.imap.IWorkOrderFollowTimeMap;
import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;

import tuniu.frm.core.DaoBase;

@Repository("wo_dao_impl-follow_time")
public class WorkOrderFollowTimeDao extends DaoBase<WorkOrderFollowTimeEntity, IWorkOrderFollowTimeMap>  implements IWorkOrderFollowTimeMap {
	public WorkOrderFollowTimeDao() {  
		tableName = "wo_follow_time";		
	}
	
	@Autowired
	@Qualifier("wo_dao_sqlmap-follow_time")
	public void setMapper(IWorkOrderFollowTimeMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<WorkOrderFollowTimeEntity> getExpireFollowList() {
		return mapper.getExpireFollowList();
	}
}
