package com.tuniu.bi.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.qs.model.CompSatisRateMonitor;
import com.tuniu.qms.qs.model.SubstdContractAppendAmt;
import com.tuniu.qms.qs.model.SubstdOrderAmt;
import com.tuniu.qms.qs.model.SubstdPurchaseAmt;
import com.tuniu.qms.qs.model.SubstdRefundAmt;

public interface DataMapper {
	
	List<SubstdPurchaseAmt> listPurchaseAmt(Map<String, Object> map);
	
	List<SubstdContractAppendAmt> listContractAppendAmt(Map<String, Object> map);
	
	List<SubstdOrderAmt> listOrderAmt(Map<String, Object> map);
	
	List<SubstdRefundAmt> listRefundAmt(Map<String, Object> map);
	
	List<CompSatisRateMonitor> listCompStaisfy(Map<String, Object> map);
	
	List<CompSatisRateMonitor> listCompStaisfyAll(Map<String, Object> map);
}
