/**
 * 
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.ITspService;
import com.tuniu.gt.tsp.entity.TspResponseEntity;
import com.tuniu.operation.platform.tsg.client.caller.rest.RequestParam;
import com.tuniu.operation.platform.tsg.client.caller.rest.ResponseResult;
import com.tuniu.operation.platform.tsg.client.proxy.tsg.TSPCommonClient;

/**
 * @author Jiang Sir
 *
 */
@Service("newTspService")
public class TspServiceImpl implements ITspService {
    private static Logger logger = Logger.getLogger(TspServiceImpl.class);
    
    @Resource
    private TSPCommonClient tspCommonClient;
    
    public TspResponseEntity execute(String serviceName,String method, Map<String,Object> requestData) {
        JSONObject result = new JSONObject();
        StringBuilder logStr = new StringBuilder();
        try {
            RequestParam requestParam = new RequestParam();
            requestParam.setServiceName(serviceName);
            requestParam.setMethod(method);
            requestParam.setData(JSONObject.fromObject(requestData).toString());
            ResponseResult responseResult = tspCommonClient.responseCaller(requestParam);
            String responseResultStr = responseResult.getReturnStr();
            result =  JSONObject.fromObject(responseResultStr);
            
            logStr.append("tsp execute:").append("\n")
            .append("service#").append(requestParam.getServiceName()).append("\n")
            .append("request#").append(requestParam.getData()).append("\n")
            .append("response#").append(result);
            logger.info(logStr);
            
        } catch(Exception e) {
            logger.error(logStr,e);
        } 
        
        return TspResponseEntity.fromJSONObject(result);
    }

    @Override
    public TspResponseEntity executeGet(String serviceName, Map<String, Object> requestData) {
        return execute(serviceName,"GET",requestData);
    }

    @Override
    public TspResponseEntity executePost(String serviceName, Map<String, Object> requestData) {
        return execute(serviceName,"POST",requestData);
    }
    
}
