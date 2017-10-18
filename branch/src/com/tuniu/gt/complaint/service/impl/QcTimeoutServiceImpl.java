/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: IQcworkServiceImpl.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午05:36:08
 *********************************************
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.QcTimeoutDao;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcTimeoutEntity;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IQcTimeoutService;
import com.tuniu.gt.toolkit.DateUtil;

/**
 * 〈统计质检单是否超时Service实现
 */
@Service("complaint_service_impl-qctimeout")
public class QcTimeoutServiceImpl implements IQcTimeoutService {

    private static Logger logger = Logger.getLogger(QcTimeoutServiceImpl.class);

    @Autowired
    @Qualifier("complaint_dao_impl-qctimeout")
    private QcTimeoutDao dao;
    
    @Autowired
    @Qualifier("complaint_service_impl-qc")
    private IQcService qcService;

    @SuppressWarnings("unchecked")
    /**
     * 每日00:05:00做前一日超时未完成记录快照
     */
	public void checkQctimeoutUndone() {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("status", 1); // 处理中的质检单
    	params.put("state", 4);
    	List<QcEntity> qcList = (List<QcEntity>) qcService.fetchList(params);
    	List<QcTimeoutEntity> timeoutEntList = new ArrayList<QcTimeoutEntity>();
    	String yesterdayStr = DateUtil.getYesterdayStr();
    	if (null != qcList) {
    		for (QcEntity qc : qcList) {
    			if (qcService.isTimeout(qc)) {
    				QcTimeoutEntity timeoutEnt = new QcTimeoutEntity();
                    timeoutEnt.setQcId(qc.getId());
                    timeoutEnt.setQcPersonId(qc.getQcPerson());
                    timeoutEnt.setQcPersonName(qc.getQcPersonName());
                    timeoutEnt.setRecordDate(yesterdayStr);
                    timeoutEntList.add(timeoutEnt);
    			}
    		}
    	}
    	
    	if (timeoutEntList.size() > 0) {
            dao.addTimeoutRecord(timeoutEntList);
            logger.info("超时未完成快照已生成，超时未完成记录数为：" + timeoutEntList.size());
        }
    }

}
