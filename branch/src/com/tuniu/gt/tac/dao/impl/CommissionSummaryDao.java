package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.ICommissionSummaryMap;
import com.tuniu.gt.tac.dao.sqlmap.imap.ICustAskConcessionMap;
import com.tuniu.gt.tac.entity.CommissionSummary;

@Repository("tac_dao_impl-commissionSummary")
public class CommissionSummaryDao extends DaoBase<CommissionSummary, ICommissionSummaryMap>  implements  ICommissionSummaryMap
{
	public CommissionSummaryDao() {  
		tableName ="tac_commission_summary";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-commissionSummary")
	public void setMapper(ICommissionSummaryMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
