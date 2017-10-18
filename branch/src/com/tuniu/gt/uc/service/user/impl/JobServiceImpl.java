package com.tuniu.gt.uc.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.uc.dao.impl.JobDao;
import com.tuniu.gt.uc.service.user.IJobService;
@Service("uc_service_user_impl-job")
public class JobServiceImpl extends ServiceBaseImpl<JobDao> implements IJobService {
	@Autowired
	@Qualifier("uc_dao_impl-job")
	public void setDao(JobDao dao) {
		this.dao = dao;
	};
}
