package com.tuniu.qms.qs.service;

/**
 * 签约后出游前未收齐团款的订单发送监控
 * @author zhangsensen
 * @date 2017/4/27
 */
public interface OrderUncollectionService {
	
	/**
	 * 发送监控邮件
	 */
	void sendMonitorEmail();

}
