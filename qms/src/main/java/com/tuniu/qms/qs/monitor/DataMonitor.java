package com.tuniu.qms.qs.monitor;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.service.SyncyTaskService;
import com.tuniu.qms.qs.service.CompSatisRateMonitorService;
import com.tuniu.qms.qs.service.SubstdContractAppendAmtService;
import com.tuniu.qms.qs.service.SubstdOrderAmtService;
import com.tuniu.qms.qs.service.SubstdPurchaseAmtService;
import com.tuniu.qms.qs.service.SubstdRefundAmtService;

public class DataMonitor {
	
	@Autowired
	private SubstdPurchaseAmtService purchaseService;
	
	@Autowired
	private SubstdOrderAmtService ordService;
	
	@Autowired
	private SubstdRefundAmtService refundService;
	
	@Autowired
	private SubstdContractAppendAmtService caService;
	
	@Autowired
	private CompSatisRateMonitorService compStaisService;
	
	@Autowired
	private SyncyTaskService syncyTaskService;
	
	/** 创建不合格采购单快照 */
	public void createSubstdPurchaseAmtSnapshot() {
		purchaseService.createSubstdPurchaseAmtSnapshot();
	}
	
	/** 创建不合格订单快照 */
	public void createSubstdOrderAmtSnapshot() {
		ordService.createSubstdOrderAmtSnapshot();
	}
	
	/** 创建不合格退款单快照 */
	public void createSubstdRefundAmtSnapshot() {
		refundService.createSubstdRefundAmtSnapshot();
	}
	
	/** 创建不合格合同增补单快照 */
	public void createSubstdContractAppendAmtSnapshot() {
		caService.createSubstdContractAppendAmtSnapshot();
	}
	
	/**综合满意度达成率监控快照**/
	public void createCompStaisRateSnapshot(){
		compStaisService.createCompStaisRateSnapshot();
	}
	
	/**综合满意度全量数据**/
	public void createCompSatisfyAll(){
		compStaisService.createCompSatisfyAll();
	}
	
	/** 供应商责任赔款查漏监控提醒*/
	public void createSupplierRespMonitor(){
		syncyTaskService.createSupplierRespMonitor();
	}
}
