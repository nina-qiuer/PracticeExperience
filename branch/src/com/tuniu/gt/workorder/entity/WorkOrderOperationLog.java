/**
 * 
 */
package com.tuniu.gt.workorder.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
 *
 */
public class WorkOrderOperationLog  extends EntityBase{
    
    /**工单id*/
    private Integer woId;

    /**事件*/
    private String event;
    
    /**内容*/
    private String content;
    
    /**操作人*/
    private String operatePerson;
    
    /**操作事件*/
    private Date operateTime;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getWoId() {
        return woId;
    }

    public void setWoId(Integer woId) {
        this.woId = woId;
    }
    
}
