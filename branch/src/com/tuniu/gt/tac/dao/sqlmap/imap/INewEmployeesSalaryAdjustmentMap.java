package com.tuniu.gt.tac.dao.sqlmap.imap;


import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("tac_dao_sqlmap-newEmployeesSalaryAdjustment")
public interface INewEmployeesSalaryAdjustmentMap extends IMapBase {
    /**
     * @param paramMap
     * @return
     */
    int count(Map<String, Object> paramMap);
}
