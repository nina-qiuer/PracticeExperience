package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.IConcessionalBiddingAgainstRivalMap;
import com.tuniu.gt.tac.entity.ConcessionalBiddingAgainstRival;

@Repository("tac_dao_impl-concessionalBiddingAgainstRival")
public class ConcessionalBiddingAgainstRivalDao extends DaoBase<ConcessionalBiddingAgainstRival, IConcessionalBiddingAgainstRivalMap>  implements  IConcessionalBiddingAgainstRivalMap
{
	public ConcessionalBiddingAgainstRivalDao() {  
		tableName ="tac_concessional_bidding_against_rival";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-concessionalBiddingAgainstRival")
	public void setMapper(IConcessionalBiddingAgainstRivalMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
