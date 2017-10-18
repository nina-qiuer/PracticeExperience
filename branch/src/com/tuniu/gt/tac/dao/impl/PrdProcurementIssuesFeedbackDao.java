package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.tac.dao.sqlmap.imap.IPrdProcurementIssuesFeedbackMap;
import com.tuniu.gt.tac.entity.PrdProcurementIssusFeedbak;
import com.tuniu.gt.workorder.dao.sqlmap.imap.IWorkOrderMap;
import com.tuniu.gt.workorder.entity.WorkOrder;

@Repository("tac_dao_impl-prdProcurementIssuesFeedback")
public class PrdProcurementIssuesFeedbackDao extends DaoBase<PrdProcurementIssusFeedbak, IPrdProcurementIssuesFeedbackMap>  implements  IPrdProcurementIssuesFeedbackMap
{
	public PrdProcurementIssuesFeedbackDao() {  
		tableName ="tac_product_procurement_issues_feedback";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-prdProcurementIssuesFeedback")
	public void setMapper(IPrdProcurementIssuesFeedbackMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
