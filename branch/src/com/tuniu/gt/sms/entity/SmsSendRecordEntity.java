/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: SmsSendRecordEntity.java
 * Author:   yangjian3
 * Date:     2014-4-08 下午03:51:57
 *********************************************
 */
package com.tuniu.gt.sms.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class SmsSendRecordEntity extends EntityBase implements Serializable {

    /**
     */
    private static final long serialVersionUID = 4408524254590942254L;

    /**
     * 手机号码
     */
    private String telNum = "";
    
    private Integer businessId; // 业务id，当type=2时是预警订单ID

    /**
     * 短信taskid
     */
    private Integer smsTaskId = 0;

    /**
     * 发送时间
     */
    private Date sendTime = new Date();

    /**
     * 操作人
     */
    private Integer operatorId = 0;

    /**
     * 短信内容
     */
    private String content = "";
    
    /**
     * 短信类型
     */
    private Integer type = 0;//1.售后短信，2.预警短信

    /**
     * 发送结果0-成功；1-失败
     */
    private String sendResult = "0";

    /**
     * 发送结果提示；错误原因
     */
    private String resultMsg = "";

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
     * @return the telNum
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     * @param telNum the telNum to set
     */
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    /**
     * @return the smsTaskId
     */
    public Integer getSmsTaskId() {
        return smsTaskId;
    }

    /**
     * @param smsTaskId the smsTaskId to set
     */
    public void setSmsTaskId(Integer smsTaskId) {
        this.smsTaskId = smsTaskId;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return the operatorId
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the resultMsg
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * @param resultMsg the resultMsg to set
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * @return the sendResult
     */
    public String getSendResult() {
        return sendResult;
    }

    /**
     * @param sendResult the sendResult to set
     */
    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

}
