package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import com.tuniu.gt.complaint.entity.FollowTimeEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IFollowTimeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-follow_time")
public class FollowTimeDao extends DaoBase<FollowTimeEntity, IFollowTimeMap>  implements IFollowTimeMap {
	public FollowTimeDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "follow_time";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-follow_time")
	public void setMapper(IFollowTimeMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<FollowTimeEntity> getExpireFollowList() {
		return mapper.getExpireFollowList();
	}
}
