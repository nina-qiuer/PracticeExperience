package com.tuniu.gt.sms.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

/**
 * 〈短信发送service〉<br>
 * 
 * @author yangjian3
 */
public interface ISmsSendRecordService extends IServiceBase {

    /**
     * 功能描述: 发送短信公共类<br>
     * 调用短信发送接口<br>
     * 支持群发<br>
     * 自动保存短信发送记录表记录成功和失败的记录
     * 
     * @param mobileNum 手机号码，多个用","隔开,如15051843348,13002585827
     * @param content 短信内容
     * @param operateId 发送人,发送短信员工的id，系统发送为104
     * @param taskId smsid
     * @return int 发送成功的条数
     */
    int sendMessages(String mobileNum, Integer businessId, String content, String operateId, String taskId ,int type);
    
    /**
     * 功能描述: 根据条件群发短信<br>
     * 〈功能详细描述〉
     * 
     * @param search
     * @return
     */
    int sendGroupMessages(Map<String, String> condition, Integer[] waringIds, String operateId, String content ,int type);
    
    List<String> getAlreadySendNos(Map<String, Object> params);
    
}
