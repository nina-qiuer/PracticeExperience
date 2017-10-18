package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.CustAskConcessionDao;
import com.tuniu.gt.tac.service.ICustAskConcessionService;
@Service("tac_service_impl-custAskConcession")
public class CustAskConcessionServiceImpl extends ServiceBaseImpl<CustAskConcessionDao> implements ICustAskConcessionService
{
	private static Logger logger = Logger.getLogger(CustAskConcessionServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-custAskConcession")
	public void setDao(CustAskConcessionDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
