/*
 * File Name:MonitorUsePointEntity.java
 * Author:xuxuezhi
 * Date:2017年8月5日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tuniu.gt.frm.entity.EntityBase;

public class MonitorUsePointEntity extends EntityBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String url;
    
    private int viewUserId;
    
    private String viewUserName;
    
    private Timestamp viewTime;
    
    private String sysCode;
    
    private String modeCode;
    
    private String userOrg;
    
    private String remark;
    
    private int mark;

    /**
     * @return the viewUserName
     */
    
    public String getViewUserName()
    {
        return viewUserName;
    }

    /**
     * @param viewUserName the viewUserName to set
     */
    public void setViewUserName(String viewUserName)
    {
        this.viewUserName = viewUserName;
    }

    /**
     * @return the user_org
     */
    
    public String getUserOrg()
    {
        return userOrg;
    }

    /**
     * @param user_org the user_org to set
     */
    public void setUserOrg(String userOrg)
    {
        this.userOrg = userOrg;
    }

    /**
     * @return the remark
     */
    
    public String getRemark()
    {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * @return the mark
     */
    
    public int getMark()
    {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(int mark)
    {
        this.mark = mark;
    }

    /**
     * @return the modeCode
     */
    
    public String getModeCode()
    {
        return modeCode;
    }

    /**
     * @param modeCode the modeCode to set
     */
    public void setModeCode(String modeCode)
    {
        this.modeCode = modeCode;
    }

    /**
     * @return the url
     */
    
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the viewUserId
     */
    
    public int getViewUserId()
    {
        return viewUserId;
    }

    /**
     * @param viewUserId the viewUserId to set
     */
    public void setViewUserId(int viewUserId)
    {
        this.viewUserId = viewUserId;
    }

    /**
     * @return the viewTime
     */
    
    public Timestamp getViewTime()
    {
        return viewTime;
    }

    /**
     * @param viewTime the viewTime to set
     */
    public void setViewTime(Timestamp viewTime)
    {
        this.viewTime = viewTime;
    }

    /**
     * @return the sysCode
     */
    
    public String getSysCode()
    {
        return sysCode;
    }

    /**
     * @param sysCode the sysCode to set
     */
    public void setSysCode(String sysCode)
    {
        this.sysCode = sysCode;
    }
    
    
    
}
