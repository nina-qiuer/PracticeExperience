/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年8月9日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintEmailInfoDao;
import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;
import com.tuniu.gt.complaint.service.IComplaintEmailInfoService;

/**
 * ComplaintEmailInfoServiceImpl.java
 *
 * @author fangyouming
 */
@Service("complaint_service_complaint_impl-complaint_email_info")
public class ComplaintEmailInfoServiceImpl extends ServiceBaseImpl<ComplaintEmailInfoDao> implements
        IComplaintEmailInfoService {

    @Autowired
    @Qualifier("complaint_dao_impl-complaint_email_info")
    public void setDao(ComplaintEmailInfoDao dao) {
        this.dao = dao;
    }

    @Override
    public ComplaintEmailInfoEntity getComplaintEmailInfoByComplaintId(Integer complaintId) {
        ComplaintEmailInfoEntity ComplaintEmailInfoEntity = dao.getByComplaintId(complaintId);
        return ComplaintEmailInfoEntity;
    }

    @Override
    public void saveComplaintEmailInfo(ComplaintEmailInfoEntity entity) {
        dao.insert(entity);
    }

}
