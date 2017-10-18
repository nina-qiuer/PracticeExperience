/**
 * 
 */
package com.tuniu.gt.complaint.service;

import java.util.Map;

/**
 * [抢单服务类]
 * 
 * @author jiangye
 */
public interface IRobComplaintService {

    /**
     * 抢单
     * @param userId 抢单人id
     * @param amount 抢单数量
     * @return Map<String,Integer>  
     *      robedAmount ：抢到单数  restAmount：剩余单数
     */
    Map<String,Integer> robComplaint(int userId, int amount);
}
