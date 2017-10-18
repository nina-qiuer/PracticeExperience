package com.tuniu.qms.qs.monitor;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.qs.service.OrderUncollectionService;
import com.tuniu.qms.qs.service.SubstdOrderSnapshotService;

public class OrderMonitor {
	
	@Autowired
	private SubstdOrderSnapshotService orderService;
	
	@Autowired
	private OrderUncollectionService orderUncollectionService;
	
	/** 创建不合格订单快照 */
	public void addSubstdOrderSnapshot() {
		orderService.syncOrderExt();
		
		orderService.createSubstdOrderSnapshot();
		
		orderService.createDepOrdNumSnapshot();
	}
	
	/**
	 * 签约后出游前未收齐团款的订单发送监控
	 */
	public void orderUncollectionMonitor(){
		orderUncollectionService.sendMonitorEmail();
	}
	
}
