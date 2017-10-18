package com.tuniu.gt.complaint.scheduling;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.tuniu.gt.toolkit.MemcachesUtil;

public class ComplaintSchedulerFactoryBean extends SchedulerFactoryBean {
	final static String flag_key="quartz_flag";
	public void start() throws SchedulingException {
		
		String value = MemcachesUtil.get(flag_key);
		if (StringUtils.isBlank(value)) {
			super.start();
			MemcachesUtil.set(flag_key, "true");
			System.out.println("started");
		} else {
			MemcachesUtil.delete(flag_key);
			System.out.println("not started");
		}

		System.out.println(this);

	}
}
