package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.ICommissionSummaryMap;
import com.tuniu.gt.tac.dao.sqlmap.imap.INewEmployeesSalaryAdjustmentMap;
import com.tuniu.gt.tac.entity.NewEmployeesSalaryAdjustment;

@Repository("tac_dao_impl-newEmployeesSalaryAdjustment")
public class NewEmployeesSalaryAdjustmentDao extends DaoBase<NewEmployeesSalaryAdjustment, INewEmployeesSalaryAdjustmentMap>  implements  ICommissionSummaryMap
{
	public NewEmployeesSalaryAdjustmentDao() {  
		tableName ="tac_new_employees_salary_adjustment";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-newEmployeesSalaryAdjustment")
	public void setMapper(INewEmployeesSalaryAdjustmentMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
