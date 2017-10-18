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

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author yangjian3
 */
@Repository("complaint_dao_sqlmap-qcwork")
public interface IQcworkMap {

    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     * 〈功能详细描述〉
     * 
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> queryQcworkCount(Map<String, Object> condi);

    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     * 〈功能详细描述〉
     * 
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> queryQcworkDayCount(Map<String, Object> condi);

    /**
     * 功能描述: 查询一个时间段之前超时未完成的订单<br>
     * 〈功能详细描述〉
     * 
     * @param start
     * @param end
     * @return
     */
    List<Map<String, Object>> queryQcworkTimeOutNotDo(Map<String, Object> condi);

    List<Map<String, Object>> queryQcDetail(Map<String, Object> paramMap);
    
    /**
     * 查询超时未完成的记录明细
     */
    List<Map<String, Object>> queryQcDetailUndone(Map<String, Object> paramMap);
    
}
