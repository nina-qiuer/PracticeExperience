/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: IQcworkService.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午05:30:36
 *********************************************
 */
package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

/**
 * 〈质检人员工作量综合统计Service〉<br>
 * 
 * @author yangjian3
 */
public interface IQcworkService {

    /**
     * 功能描述: 根据开始时间和结束时间查询质检人员工作量统计结果<br>
     */
    List<Map<String, Object>> queryQcwork(String startTime, String endTime);

    List<Map<String, Object>> queryQcDetail(Map<String, Object> paramMap);
    
    /**
     * 查询超时未完成的记录明细
     */
    List<Map<String, Object>> queryQcDetailUndone(Map<String, Object> paramMap);
    
}
