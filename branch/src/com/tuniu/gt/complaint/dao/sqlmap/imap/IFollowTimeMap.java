package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.FollowTimeEntity;

@Repository("complaint_dao_sqlmap-follow_time")
public interface IFollowTimeMap extends IMapBase { 
	
	/**
	 * 查询所有到时跟进提醒记录
	 * @return
	 */
	List<FollowTimeEntity> getExpireFollowList();

}
