package com.tuniu.gt.workorder.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;

import tuniu.frm.core.IMapBase;

@Repository("wo_dao_sqlmap-follow_time")
public interface IWorkOrderFollowTimeMap extends IMapBase { 
	
	/**
	 * 查询所有到时跟进提醒记录
	 * @return
	 */
	List<WorkOrderFollowTimeEntity> getExpireFollowList();

}
