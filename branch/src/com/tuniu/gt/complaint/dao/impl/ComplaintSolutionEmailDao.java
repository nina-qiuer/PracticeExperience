/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年7月24日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintSolutionEmailMap;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEmailEntity;

/**
 * ComplaintSolutionEmailDao.java
 *
 * @author fangyouming
 */
@Repository("complaint_dao_impl-complaint_solution_email")
public class ComplaintSolutionEmailDao extends DaoBase<ComplaintSolutionEmailEntity, IComplaintSolutionEmailMap>
        implements IComplaintSolutionEmailMap {

    public ComplaintSolutionEmailDao() {
        tableName = Constant.appTblPreMap.get("complaint") + "complaint_solution_email";
    }

    @Autowired
    @Qualifier("complaint_dao_sqlmap-complaint_solution_email")
    public void setMapper(IComplaintSolutionEmailMap mapper) {
        this.mapper = mapper;
    }

    @Override
    public ComplaintSolutionEmailEntity getBySolutionId(Integer solutionId) {
        return mapper.getBySolutionId(solutionId);
    }

    @Override
    public void updateById(Map<String, Object> map) {
        mapper.updateById(map);
    }

}
