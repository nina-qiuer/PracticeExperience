/*
 * File Name:MonitorUsePointServceImpl.java
 * Author:xuxuezhi
 * Date:2017年8月5日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.MonitorUsePointDao;
import com.tuniu.gt.complaint.entity.MonitorUsePointEntity;
import com.tuniu.gt.complaint.service.MonitorUsePointServce;

@Service("complaint_service_complaint_impl-monitor_use_point")
public class MonitorUsePointServceImpl extends ServiceBaseImpl<MonitorUsePointDao> implements MonitorUsePointServce 
{
    @Autowired
    @Qualifier("complaint_dao_impl-monitor_use_point")
    public void setDao(MonitorUsePointDao dao) {
        this.dao = dao;
    }
    public void saveMonitorUsePoint(MonitorUsePointEntity entity){
        dao.insertMonitorInfo(entity);
    }
    public String getUserDeptInfo(Map param){
        Map<String, Object>  userdeptInfo = dao.getUseDeptInfo(param);
        StringBuffer strUserDeptInfo = new StringBuffer();
        strUserDeptInfo.append(userdeptInfo.get("realName")).append(";").append(userdeptInfo.get("oneDept")).append(";").append(userdeptInfo.get("twoDept")).append(";").append(userdeptInfo.get("threeDept")).append(";").append(userdeptInfo.get("fourdept"));
        return strUserDeptInfo.toString();
    }
    
}
