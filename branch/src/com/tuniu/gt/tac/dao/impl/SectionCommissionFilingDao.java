package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.ICommissionSummaryMap;
import com.tuniu.gt.tac.dao.sqlmap.imap.ISectionCommissionFilingMap;
import com.tuniu.gt.tac.entity.SectionCommissionFiling;

@Repository("tac_dao_impl-sectionCommissionFiling")
public class SectionCommissionFilingDao extends DaoBase<SectionCommissionFiling, ISectionCommissionFilingMap>  implements  ISectionCommissionFilingMap
{
	public SectionCommissionFilingDao() {  
		tableName ="tac_section_commission_filing";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-sectionCommissionFiling")
	public void setMapper(ISectionCommissionFilingMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
