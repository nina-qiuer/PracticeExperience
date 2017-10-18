package com.tuniu.qms.qs.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信参数实体
 * orderId  operatorId  systemId这三个参数至少填写其中一个
 * BI统计优先级：
 * orderId> operatorId >systemId
 * 如果是订单系统发送的，orderId必填；
 * 如果是客服操作，有订单号则填订单号，
 * 如果没有订单号，则填userId；如果上述
 * 都没有，填写发送系统的systemId。
 * 
 * @author chenhaitao
 */
public class MessageParamEntity {
	
	private final static Logger logger = LoggerFactory.getLogger(MessageParamEntity.class);

    public static Integer TEMPLATE_SATISFIED = 1081;//满意度回访模板 测试：1081 生产：1079
    
    public static Integer TEMPLATE_UNSATISFIED_REASON = 1080;//不满意原因评价模板测试：1080   生产：1078
    
    /** 模板id */
    private Integer templateId;

    /** 电话号码 */
    private List<String> mobileNum;

    /** 客户端id */
    private String clientIp;

    /** 订单id */
    private Integer orderId;

    /** 调用系统id */
    private Integer systemId = 12;

    /** 模板参数列表 */
    private List<MessageTemplateParamValuePair> smsTemplateParams;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<String> getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(List<String> mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getClientIp() {
        if(StringUtils.isEmpty(clientIp)) {
            try {
                clientIp = myIp();
            } catch(UnknownHostException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return clientIp;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getSystemId() {
        return systemId;
    }

    public List<MessageTemplateParamValuePair> getSmsTemplateParams() {
        return smsTemplateParams;
    }

    public void setSmsTemplateParams(List<MessageTemplateParamValuePair> smsTemplateParams) {
        this.smsTemplateParams = smsTemplateParams;
    }

    /**
     * 获取本机IP
     * 
     * @return 返回IP地址
     * @throws UnknownHostException
     */
    protected static String myIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static void main(String[] args) throws Exception {
      /*  MessageParamEntity entity = new MessageParamEntity();
        entity.setTemplateId(642);
        entity.setMobileNum(Arrays.asList("18551847579,18850762323".split(",")));
        entity.setOrderId(1101);

        Map<String, Object> map2 = new HashMap<String, Object>();
        MessageTemplateParamValuePair mtpvp2 = new MessageTemplateParamValuePair("prdName", "韩国首尔5日游");
        MessageTemplateParamValuePair mtpvp1 = new MessageTemplateParamValuePair("dealName", "FLP");
        List<MessageTemplateParamValuePair> smsTemplateParams = new ArrayList<MessageTemplateParamValuePair>();
        smsTemplateParams.add(mtpvp1);
        smsTemplateParams.add(mtpvp2);
        entity.setSmsTemplateParams(smsTemplateParams);
        System.out.println(JSONObject.fromObject(entity));*/
        
        System.out.println(myIp());
    }
}
