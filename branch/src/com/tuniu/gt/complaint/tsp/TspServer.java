/*
 * File Name:TspServer.java
 * Author:xuxuezhi
 * Date:2017年8月23日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.operation.platform.base.json.JsonFormatter;
import com.tuniu.operation.platform.tsg.client.annotation.TSPServiceInfo;

@Controller
@RequestMapping("/cmp-outer")
public class TspServer
{
    private static Logger logger = Logger.getLogger(TspServer.class);
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
    private IComplaintService complaintService;
    /**
     * 查询客户的进行中和已完结投诉单数量，返回投诉单和订单号以及数量
     * @param request
     * @param response
     * @throws Exception
     */
    @TSPServiceInfo(name = "CRM.CMP.TspServer.queryCustCmpNum", description = "获取客户投诉单数量以及投诉单id订单id")
    @RequestMapping("/queryCustCmpNum.htm")
    public void queryCustCmpNum(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RestfulResult<Object> res = new RestfulResult<Object>();
        try{
            Map<String, Object> params = tspCommonUtils.getRestParams(request, response);
            // 入参校验
            res = TspParamCheck.checkQueryCustCmpNumParams(params);
            if(res.getSuccess()){
                Map<String, Object> qParam = new HashMap<String, Object>();
                qParam.put("custId", (Integer)res.getData());
                qParam.put("state", "4");//已完成投诉单
                List<ComplaintEntity> completeCompList = complaintService.getComplaintList(qParam);
                qParam.put("state", "1,2,3,5,6,7,10");//未完成投诉单，不包括已撤销
                List<ComplaintEntity> unCompleteCompList = complaintService.getComplaintList(qParam);
                Map<String,Object> resMap = new HashMap<String, Object>();
                buildDataMap(resMap,completeCompList,new String[]{"completeNum","id","orderId","completeCmp"});
                buildDataMap(resMap,unCompleteCompList,new String[]{"unCompleteNum","id","orderId","unCompleteCmp"});
                res.setData(resMap);
            }
        }
        catch(Exception e){

            logger.error("接口cmp-outer/queryCustCmpNum.htm出错:", e);
            res.fail(AdminErrorCode.INTERNAL_ERROR, "接口异常！");
        }
        finally{

            logger.error("接口cmp-outer/queryCustCmpNum.htm出参："+JsonFormatter.toJsonString(res));
            tspCommonUtils.write(response, res);
        }

    }
    /**
     * @param resMap
     * @param completeCompList
     * @param strings
     */
     
    private void buildDataMap(Map<String, Object> resMap, List<ComplaintEntity> compList, String[] keys)
    {
        List<Map<String,Object>> compResList = new ArrayList<Map<String,Object>>();
        resMap.put(keys[0], 0);
        resMap.put(keys[3], compResList);
        if(null != compList)
        {
            resMap.put(keys[0], compList.size());
            for(int i = 0; i < compList.size(); i++)
            {
                Map<String,Object> completeMap = new HashMap<String, Object>();
                completeMap.put(keys[1], compList.get(i).getId());
                completeMap.put(keys[2], compList.get(i).getOrderId());
                compResList.add(completeMap);
            }
        }
        
    }
}
