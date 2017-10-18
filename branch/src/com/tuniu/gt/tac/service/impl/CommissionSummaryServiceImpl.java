package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.CommissionSummaryDao;
import com.tuniu.gt.tac.service.ICommissionSummaryService;
@Service("tac_service_impl-commissionSummary")
public class CommissionSummaryServiceImpl extends ServiceBaseImpl<CommissionSummaryDao> implements ICommissionSummaryService
{
	private static Logger logger = Logger.getLogger(CommissionSummaryServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-commissionSummary")
	public void setDao(CommissionSummaryDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
