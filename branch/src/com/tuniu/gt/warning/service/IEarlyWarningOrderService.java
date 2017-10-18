package com.tuniu.gt.warning.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;

public interface IEarlyWarningOrderService extends IServiceBase {
	
	EarlyWarningOrderPage getEwoPage(EarlyWarningOrderPage ewoPage);
	
	List<EarlyWarningOrderEntity> getEwoList(EarlyWarningOrderPage ewoPage);
	
	void toggleEwo(String idsStr, int toggleFlag);
	
	void insertFlight(EwOrderFlightEntity flight);
	
	void insertAgency(EwOrderAgencyEntity agency);
	
}
