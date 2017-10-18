/**
 * 
 */
package com.tuniu.gt.complaint.restful;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.trest.TRestClient;

import tuniu.frm.service.Constant;

/**
 * @author jiangye
 *
 */
public class PGARestClient {
    
    private static Logger logger = Logger.getLogger(PGARestClient.class);
    
    private static String PGAURL = Constant.CONFIG.getProperty("PGA_URL");
    
    public static  JSONObject getOrderInfo(Integer orderId) {
        JSONObject result = new JSONObject();
        TRestClient trestClient = new TRestClient(PGAURL);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        String request = JSONObject.fromObject(paramMap).toString();
        trestClient.setData(request);
        logger.info("getOrderInfo from pga BEGIN["+orderId+"]: request body:" + request);
        try {
            String execute = trestClient.execute();
            JSONObject jObject = JSONObject.fromObject(execute);
            logger.info("getOrderInfo from pga ING["+orderId+"] response body:" + jObject);
            boolean isSucc = jObject.getBoolean("success");
            if (isSucc) {
                result = jObject.getJSONObject("data");
            }else {
                logger.info("getOrderInfo from pga FAILD["+orderId+"]： "+JsonUtil.getStringValue(jObject, "msg"));
            }
        } catch (Exception e) {
            logger.error("exception occured when getOrderInfo from pga["+orderId+"]："+e.getMessage(),e);
        }
        logger.info("getOrderInfo from pga END["+orderId+"]");
        
        return result;
    }
    
    public static void main(String[] args) {
        getOrderInfo(3962183);
    }
    
    
}
