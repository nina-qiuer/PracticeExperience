/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年8月9日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.service;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;

/**
 * IComplaintEmailInfoService.java
 *
 * @author fangyouming
 */
public interface IComplaintEmailInfoService extends IServiceBase {

    /**
     * 根据对客解决方案id获取解决方案Email
     */
    public ComplaintEmailInfoEntity getComplaintEmailInfoByComplaintId(Integer complaintId);

    /**
     * 保存对客邮件
     * 
     * @param entity
     */
    void saveComplaintEmailInfo(ComplaintEmailInfoEntity entity);
}
