package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.NewEmployeesSalaryAdjustmentDao;
import com.tuniu.gt.tac.service.INewEmployeesSalaryAdjustmentService;
@Service("tac_service_impl-newEmployeesSalaryAdjustment")
public class NewEmployeesSalaryAdjustmentServiceImpl extends ServiceBaseImpl<NewEmployeesSalaryAdjustmentDao> implements INewEmployeesSalaryAdjustmentService
{
	private static Logger logger = Logger.getLogger(NewEmployeesSalaryAdjustmentServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-newEmployeesSalaryAdjustment")
	public void setDao(NewEmployeesSalaryAdjustmentDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
