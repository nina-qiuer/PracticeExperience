package com.tuniu.gt.toolkit;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SendRtxListener implements ServletContextListener{
	
	private Timer timer = null;
	
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		//设置任务计划
		//启动时间 服务启动后10秒
		//间隔时间 60秒执行一次
		timer.schedule(new SendRtxTask(), 10000, 60000);
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}
}
