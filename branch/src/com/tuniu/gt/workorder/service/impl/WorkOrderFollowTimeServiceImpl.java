package com.tuniu.gt.workorder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.workorder.dao.impl.WorkOrderFollowTimeDao;
import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;
import com.tuniu.gt.workorder.service.IWorkOrderFollowTimeService;

import tuniu.frm.core.ServiceBaseImpl;
@Service("wo_service_complaint_follow_time_impl-follow_time")
public class WorkOrderFollowTimeServiceImpl extends ServiceBaseImpl<WorkOrderFollowTimeDao> implements IWorkOrderFollowTimeService {
	@Autowired
	@Qualifier("wo_dao_impl-follow_time")
	public void setDao(WorkOrderFollowTimeDao dao) {
		this.dao = dao;
	}

	@Override
	public List<WorkOrderFollowTimeEntity> getExpireFollowList() {
		return dao.getExpireFollowList();
	};
}
