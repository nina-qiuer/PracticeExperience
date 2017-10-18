package com.tuniu.gt.complaint.scheduling;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.service.ISupportShareService;

public class AgencyPayoutConfirmTask {
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-support_share")
	private ISupportShareService supportShareService;
	
	private static Logger logger = Logger.getLogger(AgencyPayoutConfirmTask.class); 
	
	public void confirmPayoutAuto() {
		supportShareService.confirmPayoutAuto();
		logger.info("AgencyPayoutConfirm Auto_Confirm：" + new java.util.Date());
	}

}
