package com.tuniu.qms.common.service;

import java.util.List;

import com.tuniu.qms.common.dto.JobDto;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.service.base.BaseService;

public interface JobService extends BaseService<Job, JobDto> {
	
	List<Job> getJobDataCache();
	
	/**
	 * 根据岗位名称查询岗位ID
	 * @param jobName
	 * @return
	 */
	Integer getJobIdByName(String jobName);
	
	Job getJobByName(String jobName);
	
}
