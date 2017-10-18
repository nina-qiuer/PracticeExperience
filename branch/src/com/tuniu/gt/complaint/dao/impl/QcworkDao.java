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

import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcworkMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author yangjian3
 */
@Repository("complaint_dao_impl-qcwork")
public class QcworkDao implements IQcworkMap {

    protected IQcworkMap mapper;

    @Autowired
    @Qualifier("complaint_dao_sqlmap-qcwork")
    public void setMapper(IQcworkMap mapper) {
        this.mapper = mapper;
    }

    public List<Map<String, Object>> queryQcworkCount(Map<String, Object> condi) {
        return mapper.queryQcworkCount(condi);
    }

    public List<Map<String, Object>> queryQcworkDayCount(Map<String, Object> condi) {
        return mapper.queryQcworkDayCount(condi);
    }

    public List<Map<String, Object>> queryQcworkTimeOutNotDo(Map<String, Object> condi) {
        return mapper.queryQcworkTimeOutNotDo(condi);
    }

    public List<Map<String, Object>> queryQcDetail(Map<String, Object> paramMap) {
        return mapper.queryQcDetail(paramMap);
    }

	@Override
	public List<Map<String, Object>> queryQcDetailUndone(Map<String, Object> paramMap) {
		return mapper.queryQcDetailUndone(paramMap);
	}
}
