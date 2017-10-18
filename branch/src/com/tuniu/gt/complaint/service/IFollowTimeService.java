package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.FollowTimeEntity;

import tuniu.frm.core.IServiceBase;
public interface IFollowTimeService extends IServiceBase {
	
	/**
	 * 查询所有到时跟进提醒记录
	 * @return
	 */
	List<FollowTimeEntity> getExpireFollowList();

}
