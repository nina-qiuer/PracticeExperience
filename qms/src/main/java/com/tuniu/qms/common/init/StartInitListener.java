package com.tuniu.qms.common.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class StartInitListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private final static Logger logger = LoggerFactory.getLogger(StartInitListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null == event.getApplicationContext().getParent()) { // root application context 没有parent，他就是老大.
			logger.info("【系统初始化开始】...");
			
			logger.info("初始化配置参数...");
			Config.init();
			
			// 启动Monitor（守护线程）
	        logger.info("【系统初始化结束】...");
		}
	}

}
