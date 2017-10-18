/**
 * 
 */
package com.tuniu.gt.complaint.restful.service;

import net.sf.json.JSONObject;

import com.tuniu.gt.complaint.restful.PGARestClient;
import com.tuniu.gt.toolkit.JsonUtil;

/**
 * @author jiangye
 *
 */
public class PGARestService {
    
    private static Integer DISTRIBUTE_ORDER = 70004;
    
    /**
     * 是否为分销订单
     * @param orderId
     * @return
     */
    public static boolean isDistributeOrder(Integer orderId){
        JSONObject orderInfo = PGARestClient.getOrderInfo(orderId);
        Integer clientType = JsonUtil.getIntValue(orderInfo, "clientType");
        return DISTRIBUTE_ORDER.equals(clientType);
    }
    
    public static void main(String[] args) {
        System.out.println(isDistributeOrder(395588));
    }
}
