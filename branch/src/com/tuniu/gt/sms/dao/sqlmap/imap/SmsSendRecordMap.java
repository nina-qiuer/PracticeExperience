/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: SmsSendRecordMap.java
 * Author:   yangjian3
 * Date:     2014-4-08 下午03:51:57
 *********************************************
 */
package com.tuniu.gt.sms.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.sms.entity.SmsSendRecordEntity;

/**
 * 〈短信发送结果〉<br>
 * 
 * @author yangjian3
 */
@Repository("smsSendRecord_dao_sqlmap-sms_send_record")
public interface SmsSendRecordMap extends IMapBase {

    /**
     * 功能描述: 将短信发送结果保存到数据库中<br>
     * 〈功能详细描述〉
     * 
     * @param smsSendRecordList
     */
    void addSmsRecord(List<SmsSendRecordEntity> smsSendRecordList);
    
    List<String> getAlreadySendNos(Map<String, Object> params);

}
