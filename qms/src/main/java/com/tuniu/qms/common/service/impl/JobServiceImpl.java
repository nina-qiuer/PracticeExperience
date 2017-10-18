package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.JobMapper;
import com.tuniu.qms.common.dto.JobDto;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.DateUtil;
import com.whalin.MemCached.MemCachedClient;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobMapper mapper;
	
	@Autowired
	private MemCachedClient memCachedClient;

	@Override
	public void add(Job job) {
		mapper.add(job);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Job job) {
		mapper.update(job);
	}

	@Override
	public Job get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Job> list(JobDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(JobDto dto) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Job> getJobDataCache() {
		List<Job> jobList = (List<Job>) memCachedClient.get(CacheConstant.JOB_DATA);
		if (null == jobList || jobList.isEmpty()) {
			jobList = mapper.list(new JobDto());
			memCachedClient.set(CacheConstant.JOB_DATA, jobList, DateUtil.getTodaySurplusTime());
		}
		return jobList;
	}

	@Override
	public Integer getJobIdByName(String jobName) {
		
		return mapper.getJobIdByName(jobName);
	}

	@Override
	public Job getJobByName(String jobName) {
		
		return mapper.getJobByName(jobName);
	}

}
