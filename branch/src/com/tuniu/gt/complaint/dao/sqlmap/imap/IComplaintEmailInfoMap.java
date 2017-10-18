/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 *
 * Author:fangyouming
 *
 * Date：2017年8月9日
 *
 * Description: 功能描述
 *
 */
package com.tuniu.gt.complaint.dao.sqlmap.imap;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;

/**
 *IComplaintEmailInfoMap.java
 *
 *@author fangyouming
 * 
 */
@Repository("complaint_dao_sqlmap-complaint_email_info")
public interface IComplaintEmailInfoMap extends IMapBase{
    /**
     * 根据投诉单查询发起投诉单邮件
     * @param complaintId
     * @return
     */
    ComplaintEmailInfoEntity getByComplaintId(Integer complaintId);
}
