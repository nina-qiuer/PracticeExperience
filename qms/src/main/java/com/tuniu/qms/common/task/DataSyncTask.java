package com.tuniu.qms.common.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;

/**
 * 数据同步任务，每10秒执行一次
 */
public class DataSyncTask {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderBillService orderBillService;
	
	@Autowired
	private ComplaintBillService complaintBillService;
	
	public void syncProduct() {
		productService.syncProduct();
	}
	
	public void syncOrder() {
		orderBillService.syncOrder();
	}
	
	public void syncComplaint() {
		complaintBillService.syncComplaint();
	}
	
	public void addComplaintSyncTasks(){
		complaintBillService.addCmpSyncTasks();
	}
}
