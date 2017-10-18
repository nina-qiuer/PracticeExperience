/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: QcworkDao.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午05:39:53
 *********************************************
 */
package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcTimeoutMap;
import com.tuniu.gt.complaint.entity.QcTimeoutEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author yangjian3
 */
@Repository("complaint_dao_impl-qctimeout")
public class QcTimeoutDao implements IQcTimeoutMap {

    protected IQcTimeoutMap mapper;

    /**
     * set mapper
     */
    @Autowired
    @Qualifier("complaint_dao_sqlmap-qctimeout")
    public void setMapper(IQcTimeoutMap mapper) {
        this.mapper = mapper;
    }

    public List<Map<String, Object>> queryQctimeout(String start, String end) {
        return mapper.queryQctimeout(start, end);
    }

    public void addTimeoutRecord(List<QcTimeoutEntity> dmo) {
        mapper.addTimeoutRecord(dmo);
    }

    public List<Map<String, Object>> queryAllQctimeoutQcid(String pram) {
        return mapper.queryAllQctimeoutQcid(pram);
    }

    @Override
    public void delReTimeOut(String pram) {
        mapper.delReTimeOut(pram);
    }
}
