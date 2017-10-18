package com.tuniu.gt.toolkit;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SendRtxListenerOfNotAssignDeal implements ServletContextListener{
	
	private Timer timer = null;
	
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		//设置任务计划
        //启动时间 每天早上10点
        //间隔时间 每天执行一次
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timer.scheduleAtFixedRate(new SendRtxTaskOfNotAssignDeal(), calendar.getTime(), 24 * 60 * 60000);
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}
}
