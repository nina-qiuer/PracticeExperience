/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: SmsSendRecordMap.java
 * Author:   yangjian3
 * Date:     2014-4-08 下午03:51:57
 *********************************************
 */
package com.tuniu.gt.sms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.sms.dao.sqlmap.imap.SmsSendRecordMap;
import com.tuniu.gt.sms.entity.SmsSendRecordEntity;

/**
 * 〈短信发送结果〉<br>
 * 
 * @author yangjian3
 */
@Repository("smsSendRecord_dao_impl-sms_send_record")
public class SmsSendRecordDao extends DaoBase<SmsSendRecordEntity, SmsSendRecordMap> implements SmsSendRecordMap {

	public SmsSendRecordDao() {  
		tableName = "sms_send_record";		
	}
	
    /**
     * 查询对应的表 sms_send_record
     */
    @Autowired
    @Qualifier("smsSendRecord_dao_sqlmap-sms_send_record")
    public void setMapper(SmsSendRecordMap mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    public void addSmsRecord(List<SmsSendRecordEntity> smsSendRecordList) {
        mapper.addSmsRecord(smsSendRecordList);
    }

	@Override
	public List<String> getAlreadySendNos(Map<String, Object> params) {
		return mapper.getAlreadySendNos(params);
	}


}
