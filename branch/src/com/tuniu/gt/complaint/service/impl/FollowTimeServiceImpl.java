package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.FollowTimeDao;
import com.tuniu.gt.complaint.entity.FollowTimeEntity;
import com.tuniu.gt.complaint.service.IFollowTimeService;
@Service("complaint_service_complaint_follow_time_impl-follow_time")
public class FollowTimeServiceImpl extends ServiceBaseImpl<FollowTimeDao> implements IFollowTimeService {
	@Autowired
	@Qualifier("complaint_dao_impl-follow_time")
	public void setDao(FollowTimeDao dao) {
		this.dao = dao;
	}

	@Override
	public List<FollowTimeEntity> getExpireFollowList() {
		return dao.getExpireFollowList();
	};
}
