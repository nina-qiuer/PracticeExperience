package com.tuniu.qms.access.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.operation.platform.base.json.JsonFormatter;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.JsonSfUtil;
import com.tuniu.qms.qs.service.PreSaleReturnVisitService;

@Controller
@RequestMapping("/access/sms")
public class SMSServer {
	
	
	@Autowired
	private PreSaleReturnVisitService rvService;
	
	private final static Logger logger = LoggerFactory.getLogger(SMSServer.class);
	
	/**
	 * 接收客人回复的短信
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pushMsgReply", method=RequestMethod.POST)
	public void pushMsgReply(HttpServletRequest request, HttpServletResponse response) {
	
		RestServer server = new RestServer(request, response);
		int resultCode = 0;
	    Map<String,Object> responseMap = new HashMap<String, Object>();
		try {
		
			String message = server.getRestDataStr();
		    Map<String,Object> requestMap = JsonFormatter.toObject(message, Map.class);
	        JSONObject jsonObj = new JSONObject();
	        jsonObj.putAll(requestMap);
	        logger.info("request from MSG with param: "+jsonObj);
	        boolean valid = validateRequestDataFromMsg(jsonObj);
	        if(valid){
                JSONObject  dataJSON = jsonObj.getJSONObject("data");
                String tel = JsonSfUtil.getStringValue(dataJSON, "tel");
                String receiveTime = JsonSfUtil.getStringValue(dataJSON, "receiveTime");
                String smsContent = JsonSfUtil.getStringValue(dataJSON, "smsContent");
                Integer chooseItem = Integer.valueOf(smsContent.trim());
                resultCode = rvService.dealWithMsgCallBack(tel,receiveTime,chooseItem);
                
            }else{
            	
                resultCode = 240001;//入参格式错误
            }
	        switch(resultCode) {
            case 0:
            	responseMap = generateResultString("成功");
                break;
            case 240001:
            	responseMap = generateResultString("入参格式错误");
                break;
            default:
            	responseMap = generateResultString("成功");
                break;
        }
				
		} catch (Exception e) {
			
			e.printStackTrace();
			responseMap  = generateResultString(e.getMessage());
		}
		
		server.writeCustomResponse(responseMap);
	}

	private Map<String, Object> generateResultString(String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        if ("成功".equals(msg)) {
            dataMap.put("result", true);
        } else {
            dataMap.put("result", false);
        }

        dataMap.put("errormsg", msg);
        returnMap.put("data", dataMap);
        return returnMap;
    }
	
	  /**
     * @param requestData
     * @return
     */
    private boolean validateRequestDataFromMsg(JSONObject jsonObj) {
        JSONObject  dataJSON = jsonObj.getJSONObject("data");
        String tel = JsonSfUtil.getStringValue(dataJSON, "tel");
        String receiveTime = JsonSfUtil.getStringValue(dataJSON, "receiveTime");
        String smsContent = JsonSfUtil.getStringValue(dataJSON, "smsContent");
        
        boolean result = true;
        if("".equals(tel) || tel.length()!=11 ){ // 电话号码校验
            result = false;
        }
        
        if(DateUtil.parseDateTime(receiveTime) ==null) { //接收时间校验
            result = false;
        }
        
        smsContent = smsContent.trim();
        if(!smsContent.matches("[1-9]{1}")){ // 回复内容校验
            result = false;
        }
        
        return result;
    }
	
}
