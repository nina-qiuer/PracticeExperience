package com.tuniu.gt.complaint.service;


/**
 * 〈统计质检单是否超时的Service〉<br>
 * 
 * @author yangjian3
 */
public interface IQcTimeoutService {

    /**
     * 功能描述: 将当天超时未完成的质检单做快照<br>
     */
    void checkQctimeoutUndone();
}
