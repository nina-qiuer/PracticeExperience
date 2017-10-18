package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.ConcessionalBiddingAgainstRivalDao;
import com.tuniu.gt.tac.dao.impl.PrdProcurementIssuesFeedbackDao;
import com.tuniu.gt.tac.service.IConcessionalBiddingAgainstRivalService;
@Service("tac_service_impl-concessionalBiddingAgainstRival")
public class ConcessionalBiddingAgainstRivalServiceImpl extends ServiceBaseImpl<ConcessionalBiddingAgainstRivalDao> implements IConcessionalBiddingAgainstRivalService
{
	private static Logger logger = Logger.getLogger(ConcessionalBiddingAgainstRivalServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-concessionalBiddingAgainstRival")
	public void setDao(ConcessionalBiddingAgainstRivalDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
