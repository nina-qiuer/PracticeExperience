/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年7月24日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.service;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEmailEntity;
import com.tuniu.gt.frm.entity.UserEntity;

/**
 * IComplaintSolutionEmailService.java
 *
 * @author fangyouming
 */
public interface IComplaintSolutionEmailService extends IServiceBase {

    /**
     * 根据对客解决方案id获取解决方案Email
     */
    public ComplaintSolutionEmailEntity getComplaintSolutionEmailBysolutionId(Integer solutionId);

    /**
     * 保存对客邮件
     * 
     * @param entity
     */
    void saveComplaintSolutionEmail(ComplaintSolutionEmailEntity entity);

    /**
     * 修改对客邮件
     * 
     * @param entity
     */
    void updateComplaintSolutionEmail(ComplaintSolutionEmailEntity entity);

    /**
     * 提交对客解决方案发送邮件
     * 
     * @param entity
     * @param complaintEntity
     */
    void sendComplaintSolutionEmail(ComplaintSolutionEmailEntity entity, ComplaintEntity complaintEntity,
            UserEntity user);
}
