/*
 * File Name:MonitorUsePointServce.java
 * Author:xuxuezhi
 * Date:2017年8月5日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.service;

import java.util.Map;

import com.tuniu.gt.complaint.entity.MonitorUsePointEntity;

public interface MonitorUsePointServce
{
    public void saveMonitorUsePoint(MonitorUsePointEntity entity);
    
    public String getUserDeptInfo(Map param);
}
