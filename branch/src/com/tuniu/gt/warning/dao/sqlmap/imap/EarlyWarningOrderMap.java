package com.tuniu.gt.warning.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;

@Repository("warning_dao_sqlmap-early_warning_order")
public interface EarlyWarningOrderMap extends IMapBase {
	
	int getEwoNum(EarlyWarningOrderPage ewoPage);
	
	List<EarlyWarningOrderEntity> getEwoList(EarlyWarningOrderPage ewoPage);
	
	void toggleEwo(Map<String, Object> params);
	
	void insertFlight(EwOrderFlightEntity flight);
	
	void insertAgency(EwOrderAgencyEntity agency);
	
	List<EwOrderFlightEntity> getFlightList(Integer ewOrderId);
	
	List<EwOrderAgencyEntity> getAgencyList(Integer ewOrderId);
	
}
