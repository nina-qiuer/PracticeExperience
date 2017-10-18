package com.tuniu.qms.common.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.JobDto;
import com.tuniu.qms.common.model.Job;

public interface JobMapper extends BaseMapper<Job, JobDto> {
	
	
	/**
	 * 根据岗位名称查询岗位ID
	 * @param jobName
	 * @return
	 */
	Integer getJobIdByName(String jobName);
	
	Job getJobByName(String jobName);
}
