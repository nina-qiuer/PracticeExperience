package com.tuniu.gt.complaint.scheduling;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IFinanceSubbankService;
import com.tuniu.gt.toolkit.DateUtil;

@Service("synchSubbankTask")
public class SynchSubbankTask {
	
	private static Logger logger = Logger.getLogger(SynchSubbankTask.class);

	
	@Autowired
	@Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-finance_subbank")
	private IFinanceSubbankService financeSubbankService;
	
	public void synchSubbank(){
	    Date maxLastUpdateDate = financeSubbankService.getMaxLastUpdateDate();
	    String maxLastUpdateDateStr = null;
	    maxLastUpdateDateStr = (maxLastUpdateDate==null?null:DateUtil.formatDateTime(maxLastUpdateDate));
	    tspService.syncSubBankInfo(maxLastUpdateDateStr);
	}
}
