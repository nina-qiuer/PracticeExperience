/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: IQcworkMap.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午05:43:52
 *********************************************
 */
package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.QcTimeoutEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author yangjian3
 */
@Repository("complaint_dao_sqlmap-qctimeout")
public interface IQcTimeoutMap {

    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     * 〈功能详细描述〉
     * 
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> queryQctimeout(String start, String end);

    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     * 〈功能详细描述〉
     * 
     * @param start
     * @param end
     * @return
     */
    void addTimeoutRecord(List<QcTimeoutEntity> dmo);
    
    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     * 〈功能详细描述〉
     */
    List<Map<String, Object>> queryAllQctimeoutQcid(String pram);
    
    /**
     * 
     * 功能描述: 将重新分配的设置为未过期<br>
     * 〈功能详细描述〉
     *
     * @param pram
     */
    void delReTimeOut(String pram);
}
