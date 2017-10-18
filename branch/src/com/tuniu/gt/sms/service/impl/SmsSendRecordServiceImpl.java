package com.tuniu.gt.sms.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.sms.dao.impl.SmsSendRecordDao;
import com.tuniu.gt.sms.entity.MessageParamEntity;
import com.tuniu.gt.sms.entity.MessageTemplateParamValuePair;
import com.tuniu.gt.sms.entity.SmsSendRecordEntity;
import com.tuniu.gt.sms.service.ISmsSendRecordService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tuniu.frm.core.ServiceBaseImpl;

/**
 * 〈短信发送impl〉<br>
 * 
 * @author yangjian3
 */
@Service("smsSendRecord_service_smsSendRecord_impl-sms_send_record")
public class SmsSendRecordServiceImpl extends ServiceBaseImpl<SmsSendRecordDao> implements ISmsSendRecordService {

    /**
     * Log
     */
    private static Logger logger = Logger.getLogger("MessageSendClient.class");
    
    /**
     * 短信发送成功编码
     */
    public static final String SUCCESS_CODE = "0";

    /**
     * 手机号码path
     */
    public static final String MOBILE_NUM_PATH = "/TUNIU_SMS/SmsDetails/sms/mobile_num";

    /**
     * 返回编码path
     */
    public static final String CODE_NUM_PATH = "/TUNIU_SMS/SmsDetails/sms/code_num";

    /**
     * 返回信息path
     */
    public static final String CODE_NAME_PATH = "/TUNIU_SMS/SmsDetails/sms/code_name";

    /**
     * 成功条数path
     */
    public static final String SUCCESS_COUNT_PATH = "/TUNIU_SMS/SendResult/success_num";

    /**
     * 短信发送<br>
     */
    @Autowired
    @Qualifier("smsSendRecord_dao_impl-sms_send_record")
    public void setDao(SmsSendRecordDao dao) {
        this.dao = dao;
    }
    
    @Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;

    /**
     * 获取本机IP
     * 
     * @return 返回IP地址
     * @throws UnknownHostException
     */
    protected static String myIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @SuppressWarnings("unchecked")
    public int sendMessages(String mobileNum, Integer businessId, String content, String operateId, String taskId, int type) {
    	int succNum = 0;
    	MessageParamEntity messageParamEntity=new MessageParamEntity();
    	messageParamEntity.setTemplateId(1268);//人工触发短信
    	messageParamEntity.setMobileNum(Arrays.asList(mobileNum.split(",")));
    	MessageTemplateParamValuePair messageContent=new MessageTemplateParamValuePair("content", content);
    	List<MessageTemplateParamValuePair> messageContentList=new ArrayList<MessageTemplateParamValuePair>();
    	messageContentList.add(messageContent);
    	messageParamEntity.setSmsTemplateParams(messageContentList);
    	JSONObject messageJson = JSONObject.fromObject(messageParamEntity);
    	String response=tspService.sendShortMessageService(messageJson);
        if (StringUtil.isNotEmpty(response)) { // 2.处理reponse返回
            try {
            	JSONObject resultStr = JSONObject.fromObject(response);
            	if((Boolean) resultStr.get("success")){
            		JSONObject resultData = resultStr.getJSONObject("data");
            		succNum=resultData.getInt("successNum");
            		JSONArray details = resultData.getJSONArray("details");
            		List<SmsSendRecordEntity> recordList = new ArrayList<SmsSendRecordEntity>();
            		SmsSendRecordEntity record = new SmsSendRecordEntity();
            		record.setSendTime(new Date());// 发送时间
            		record.setContent(content);// 短信内容
            		record.setOperatorId(Integer.valueOf(operateId));// 操作人
            		record.setOperatorId(Integer.valueOf(operateId));// 操作人
				    record.setBusinessId(businessId);
				    record.setSmsTaskId(Integer.valueOf(taskId));// 短信平台id
				    record.setType(type);
            		for (int i = 0; i < details.size(); i++) {
            			JSONObject detail=details.getJSONObject(i);
            			record.setResultMsg(detail.getString("message"));// 发送结果信息
    				    record.setSendResult(detail.getString("errorCode"));// 发送结果编码
            			record.setTelNum(detail.getString("mobileNum"));// 手机号码
            			recordList.add(record);
					}
            		dao.addSmsRecord(recordList);
            	}
            } catch (Exception e) {
            	logger.error(e.getMessage(), e);
            }
        }
        return succNum;
    }

    /**
     * {@inheritDoc}
     */
    public int sendGroupMessages(Map<String, String> condition, String operateId, String content) {
        //String warningId = condition.get("Ids");
        return 0;
    }

    @Override
    public int sendGroupMessages(Map<String, String> condition, Integer[] waringIds, String operateId, String content,int type) {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public List<String> getAlreadySendNos(Map<String, Object> params) {
		return dao.getAlreadySendNos(params);
	}

}
