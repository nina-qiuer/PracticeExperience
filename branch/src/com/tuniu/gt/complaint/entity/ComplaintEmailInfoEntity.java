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
package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 *ComplaintEmailInfo.java
 *
 *@author fangyouming
 * 
 */
public class ComplaintEmailInfoEntity extends EntityBase implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2076495202828820571L;
    /**
     * 投诉单ID
     */
    private Integer complaintId;
    /**
     * 邮件标题
     */
    private String emailSubject;
    /**
     * 邮件发送人
     */
    private Integer emailSender;
    /**
     * 邮件接收人
     */
    private String emailRec;
    /**
     * 邮件抄送人
     */
    private String emailCc;
    /**
     * 邮件内容
     */
    private String emailContent;
    /**
     * 发送时间
     */
    private Date addTime;
    public Integer getComplaintId() {
        return complaintId;
    }
    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }
    public String getEmailSubject() {
        return emailSubject;
    }
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
    public Integer getEmailSender() {
        return emailSender;
    }
    public void setEmailSender(Integer emailSender) {
        this.emailSender = emailSender;
    }
    public String getEmailRec() {
        return emailRec;
    }
    public void setEmailRec(String emailRec) {
        this.emailRec = emailRec;
    }
    public String getEmailCc() {
        return emailCc;
    }
    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }
    public String getEmailContent() {
        return emailContent;
    }
    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }
    public Date getAddTime() {
        return addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
    
}
