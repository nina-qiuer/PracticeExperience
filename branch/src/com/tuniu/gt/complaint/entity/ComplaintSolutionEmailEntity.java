/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年7月24日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * ComplaintSolutionEmailEntity.java
 *
 * @author fangyouming
 */
public class ComplaintSolutionEmailEntity extends EntityBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5388288285004661684L;

    private Integer emailType;

    private Integer solutionId;

    private Integer complaintId;

    private String emailName;

    private String route;

    private Integer orderId;

    private String agencyName;

    private Date startDate;

    private String receiveName;

    private String ccName;

    private String remark;

    private String checkProgress;

    private String makeBetter;

    private String guestNum;

    private String passengerInfo;

    private String groupOrders;

    private String emailSender;

    private Date addTime;

    private Date updateTime;

    public Integer getEmailType() {
        return emailType;
    }

    public void setEmailType(Integer emailType) {
        this.emailType = emailType;
    }

    public Integer getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Integer solutionId) {
        this.solutionId = solutionId;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getCheckProgress() {
        return checkProgress;
    }

    public void setCheckProgress(String checkProgress) {
        this.checkProgress = checkProgress;
    }

    public String getMakeBetter() {
        return makeBetter;
    }

    public void setMakeBetter(String makeBetter) {
        this.makeBetter = makeBetter;
    }

    public String getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(String guestNum) {
        this.guestNum = guestNum;
    }

    public String getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(String passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public String getGroupOrders() {
        return groupOrders;
    }

    public void setGroupOrders(String groupOrders) {
        this.groupOrders = groupOrders;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
