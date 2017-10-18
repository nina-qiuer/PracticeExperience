package com.tuniu.qms.qs.monitor;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.qs.service.SubstdProductSnapshotService;

public class ProductMonitor {
	
	@Autowired
	private SubstdProductSnapshotService productService;
	
	/** 创建不合格产品快照 */
	public void addSubstdProductSnapshot() {
		productService.syncProductExt();
		
		productService.createSubstdProductSnapshot();
		
		productService.createDepPrdNumSnapshot();
	}
	
}
