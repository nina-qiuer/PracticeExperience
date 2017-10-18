package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.ICustAskConcessionMap;
import com.tuniu.gt.tac.entity.CustAskConcession;

@Repository("tac_dao_impl-custAskConcession")
public class CustAskConcessionDao extends DaoBase<CustAskConcession, ICustAskConcessionMap>  implements  ICustAskConcessionMap
{
	public CustAskConcessionDao() {  
		tableName ="tac_cust_ask_concession";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-custAskConcession")
	public void setMapper(ICustAskConcessionMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
