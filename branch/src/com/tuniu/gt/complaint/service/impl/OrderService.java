/**
 * 
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.toolkit.DateUtil;

/**
 * 订单服务类
 * @author jiangye
 * 
 */
@Service
public class OrderService {
    
    public String getSameGroupOrderIds(String startDate, String routeId){
        String orderIds = ComplaintRestClient.querySameGroupOrders(startDate, routeId); // 获取同团期订单
        return orderIds;
    }
    
}
