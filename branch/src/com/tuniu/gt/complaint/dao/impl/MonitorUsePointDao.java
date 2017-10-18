/*
 * File Name:MonitorUsePointDao.java
 * Author:xuxuezhi
 * Date:2017年8月5日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IMonitorUsePointMap;
import com.tuniu.gt.complaint.entity.MonitorUsePointEntity;

@Repository("complaint_dao_impl-monitor_use_point")
public class MonitorUsePointDao extends DaoBase<MonitorUsePointEntity, IMonitorUsePointMap>  implements IMonitorUsePointMap
{
    public MonitorUsePointDao() {  
        tableName = Constant.appTblPreMap.get("complaint") + "sys_use_monitor";       
    }
    
    @Autowired
    @Qualifier("complaint_dao_sqlmap-monitor_use_point")
    public void setMapper(IMonitorUsePointMap mapper) {
        this.mapper = mapper;
    }

    
    @Override
    public Integer insertMonitorInfo(MonitorUsePointEntity MonitorUsePointEntity)
    {
        // TODO Auto-generated method stub
        return mapper.insertMonitorInfo(MonitorUsePointEntity);
    }
    
    public Map<String,Object> getUseDeptInfo(Map param)
    {
        // TODO Auto-generated method stub
        return mapper.getUseDeptInfo(param);
    }
    
    
}
