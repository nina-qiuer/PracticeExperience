/*
 * File Name:TspParamCheckServiceImpl.java
 * Author:xuxuezhi
 * Date:2017年8月23日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.tsp;

import java.util.Map;

public class TspParamCheck
{
    public static RestfulResult<Object> checkQueryCustCmpNumParams(Map<String, Object> params) {

        RestfulResult<Object> checkRes = new RestfulResult<Object>();
        if(params == null){
            return checkRes.fail(AdminErrorCode.PARAMETER_IS_NULL, "参数不能为空");
        }

        Integer custId = null;
        

        if(params.get("custId") != null){
            custId = getCustId(params);
        }

        if(custId == null && params.get("params") != null){
            Map<String, Object> pMap = (Map<String, Object>)params.get("params");
            custId = getCustId(pMap);
        }

        if(custId == null){
            return checkRes.fail(AdminErrorCode.PARAMETER_IS_NULL, "缺少必填参数！");
        }

        if(custId <= 0){
            return checkRes.fail(AdminErrorCode.PARAMETER_IS_NULL, "custId应为大于0的数值！");
        }

        checkRes.setData(custId);

        return checkRes;
    }
    
    /**
     * 获取参数custId
     * 
     * @param custIdMap
     * @return
     */
    private static Integer getCustId(Map<String, Object> custIdMap) {
        Integer custId = 0;
        if(custIdMap.get("custId") instanceof String){
            custId = Integer.parseInt((String)custIdMap.get("custId"));
        }

        if(custIdMap.get("custId") instanceof Integer){
            custId = (Integer)custIdMap.get("custId");
        }

        if(custIdMap.get("custId") instanceof Double){
            custId = ((Double)custIdMap.get("custId")).intValue();
        }

        return custId;
    }
    
}
