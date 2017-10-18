/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年8月9日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintEmailInfoMap;
import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;

/**
 * ComplaintEmailInfoDao.java
 *
 * @author fangyouming
 */
@Repository("complaint_dao_impl-complaint_email_info")
public class ComplaintEmailInfoDao extends DaoBase<ComplaintEmailInfoEntity, IComplaintEmailInfoMap> implements
        IComplaintEmailInfoMap {

    public ComplaintEmailInfoDao() {
        tableName = Constant.appTblPreMap.get("complaint") + "complaint_email_info";
    }

    @Autowired
    @Qualifier("complaint_dao_sqlmap-complaint_email_info")
    public void setMapper(IComplaintEmailInfoMap mapper) {
        this.mapper = mapper;
    }

    @Override
    public ComplaintEmailInfoEntity getByComplaintId(Integer complaintId) {
        return mapper.getByComplaintId(complaintId);
    }

}
