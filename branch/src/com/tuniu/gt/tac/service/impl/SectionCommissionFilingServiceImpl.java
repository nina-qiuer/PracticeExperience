package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.SectionCommissionFilingDao;
import com.tuniu.gt.tac.service.ISectionCommissionFilingService;
@Service("tac_service_impl-sectionCommissionFiling")
public class SectionCommissionFilingServiceImpl extends ServiceBaseImpl<SectionCommissionFilingDao> implements ISectionCommissionFilingService
{
	private static Logger logger = Logger.getLogger(SectionCommissionFilingServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-sectionCommissionFiling")
	public void setDao(SectionCommissionFilingDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
