package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.PrdProcurementIssuesFeedbackDao;
import com.tuniu.gt.tac.service.IPrdProcurementIssuesFeedbackService;
import com.tuniu.gt.workorder.dao.impl.WorkOrderDao;
import com.tuniu.gt.workorder.service.IWorkOrderService;
@Service("tac_service_impl-prdProcurementIssuesFeedback")
public class PrdProcurementIssuesFeedbackServiceImpl extends ServiceBaseImpl<PrdProcurementIssuesFeedbackDao> implements IPrdProcurementIssuesFeedbackService
{
	private static Logger logger = Logger.getLogger(PrdProcurementIssuesFeedbackServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-prdProcurementIssuesFeedback")
	public void setDao(PrdProcurementIssuesFeedbackDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
