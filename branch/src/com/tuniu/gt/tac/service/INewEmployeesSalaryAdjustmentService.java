package com.tuniu.gt.tac.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;

import tuniu.frm.core.IServiceBase;

public interface INewEmployeesSalaryAdjustmentService extends IServiceBase
{

    /**
     * @param paramMap
     * @return
     */
    int count(Map<String, Object> paramMap);
	
}
