package com.tuniu.qms.qs.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.OrderUncollectionMapper;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.SendMailConfigService;
import com.tuniu.qms.qs.model.OrderUncollection;
import com.tuniu.qms.qs.model.OrderUncollectionMonitor;
import com.tuniu.qms.qs.service.OrderUncollectionService;

/**
 * 签约后出游前未收齐团款的订单发送监控
 * @author zhangsensen
 * @date 2017/4/27
 */
@Service
public class OrderUncollectionServiceImpl implements OrderUncollectionService {
	
	private final static Logger logger = LoggerFactory.getLogger(OrderUncollectionServiceImpl.class);
	
	@Autowired
	private OrderUncollectionMapper orderUncollectionMapper;
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private SendMailConfigService sendMailConfigService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void sendMonitorEmail() {
		logger.info("订单未收齐团款监控  start");
		long startTime=System.currentTimeMillis();
		
		// 查到所有符合条件的OrderUncollectionMonitor
		List<OrderUncollectionMonitor> dataList = orderUncollectionMapper.getUncollectionList(queryCondition("yyyy-MM-dd", true));
		
		if(null != dataList && dataList.size() > 0){
			buildMonitorEmailDetail(dataList);
		}
		long endTime=System.currentTimeMillis();
		
		logger.info("运行时间  " + (endTime - startTime) + "ms");
		logger.info("订单未收齐团款监控  end");
	}
	
	/**
	 * 构建监控邮件
	 * @param dataList
	 */
	private void buildMonitorEmailDetail(List<OrderUncollectionMonitor> dataList) {
		Map<String, Object> dateMap = queryCondition("MM月dd日", false);
		final StringBuilder subject = new StringBuilder("【质监报告】临近出游（").append(dateMap.get("dateStart"))
				.append(" - ").append(dateMap.get("dateEnd")).append("）未收齐钱款订单监控报告");
		
		//获取发送类型与发送人
		SendMailConfig config = sendMailConfigService.getByType("未收齐钱款订单监控抄送人");
		//设置发送人
		final String ccAddrsConfig = (null != config) ?  config.getSendAddrs() : "";
		
		//线程池
		ExecutorService executors = new ThreadPoolExecutor(8, 8, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		final CountDownLatch latch = new CountDownLatch(dataList.size());
		
		logger.info("订单未收齐团款监控 邮件个数 ： " + dataList.size());
		try{
			for(final OrderUncollectionMonitor data : dataList){
				executors.execute(new Runnable() {
					
					@Override
					public void run() {
						MailTaskDto mailTaskDto = new MailTaskDto();
						
						/** 邮件模板名称 */
						mailTaskDto.setTemplateName("uncollectionOrderMonitor.ftl");
						/** 邮件主题 */
						mailTaskDto.setSubject(subject.toString());
						/** 收件人 */
						mailTaskDto.setReAddrs(getReAddrs(data));
						  /** 抄送人 */
						mailTaskDto.setCcAddrs(getCcAddrs(data, ccAddrsConfig));
						 /** 邮件模板数据 */
						mailTaskDto.setDataMap(getDataMap(data));
						
						mailTaskService.addTask(mailTaskDto);
						
						latch.countDown();
					}
				});
			}
		}catch(Exception e){
			logger.info("线程池执行失败", e, e.getMessage());
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.info("线程池等待失败", e, e.getMessage());
		}
	}
	
	/**
	 * 获得邮件内容
	 * @param data
	 * @return
	 */
	private Map<String, Object> getDataMap(OrderUncollectionMonitor data) {
		BigDecimal moneySum = BigDecimal.ZERO;
		for(OrderUncollection orders : data.getOrderList()){
			moneySum = moneySum.add(orders.getOrderLostAmount());
		}
		/*data.setOrderAmount(data.getOrderList().size());
		data.setUncollectionMoneys(moneySum);*/
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", data);
		dataMap.put("orderAmount", data.getOrderList().size());
		dataMap.put("uncollectionMoneys", moneySum);
		
		return dataMap;
	}
	
	/**
	 * 抄送人  客服经理上级   配置人员
	 * @param data
	 * @param ccAddrs
	 * @return
	 */
	private String[] getCcAddrs(OrderUncollectionMonitor data, String ccAddrs) {
		StringBuffer sb = new StringBuffer();
		
		String manageName = data.getOrderList().get(0).getSalerManagerName();
		if(null != manageName && !("").equals(manageName)){
			User manageUser =  OaClient.getReporter(manageName);
			if(null != manageUser && null != manageUser.getUserName()){
				sb.append(manageUser.getUserName()).append("@tuniu.com").append(";");
			}
		}
		
		sb.append(ccAddrs);
		return sb.toString().split(";");
	}
	
	/**
	 * 收件人   客服专员、客服专员经理
	 * @param data
	 * @return
	 */
	private String[] getReAddrs(OrderUncollectionMonitor data) {
		StringBuffer sb = new StringBuffer();
		
		User user = userService.getUserByRealName(data.getSalerName());
		if(null != user){
			sb.append(user.getEmail()).append(";");
			
			User manageUser =  OaClient.getReporter(user.getRealName());
			if(null != manageUser && null != manageUser.getUserName()){
				sb.append(manageUser.getUserName()).append("@tuniu.com");
			}else{
				User manageUsers = userService.getUserByRealName(data.getOrderList().get(0).getSalerManagerName());
				if(null != manageUsers){
					sb.append(manageUsers.getEmail());
				}
			}
		}
		return sb.toString().split(";");
	}

	/**
	 * 获得当天、未来四天 日期查询条件
	 * @param formatType 日期格式化方式
	 * @param isDateKey 是否需要dateKey
	 * @return
	 */
	private Map<String, Object> queryCondition(String formatType, boolean isDateKey){
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		
		SimpleDateFormat df = new SimpleDateFormat(formatType);
		Calendar calendar = Calendar.getInstance();
		
		map.put("dateStart", df.format(calendar.getTime()));
		
		calendar.add(Calendar.DATE, 4);
		map.put("dateEnd", df.format(calendar.getTime()));
		
		if(isDateKey){
			SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
			calendar.add(Calendar.DATE, -5);
			map.put("datekey", df2.format(calendar.getTime()));
		}
		
		/**
		map.put("dateStart", "20170207");
		map.put("dateEnd","20170209");
		map.put("datekey", "20170831");
	    */
		
		return map;
	}

}
