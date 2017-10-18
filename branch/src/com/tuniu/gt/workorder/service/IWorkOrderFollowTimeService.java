package com.tuniu.gt.workorder.service;

import java.util.List;

import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;

import tuniu.frm.core.IServiceBase;
public interface IWorkOrderFollowTimeService extends IServiceBase {
	
	/**
	 * 查询所有到时跟进提醒记录
	 * @return
	 */
	List<WorkOrderFollowTimeEntity> getExpireFollowList();

}
