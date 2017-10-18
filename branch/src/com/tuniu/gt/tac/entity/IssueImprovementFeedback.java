/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class IssueImprovementFeedback extends EntityBase{

    private String customerDepartment; // 一级部门
    private String customerScndDepartment; // 二级部门
    private String customerGroup; // 三级组组
    private String customer; // 客服
    private String orderId; // 关联订单号
    private String issueDescription; // 问题描述
    private String advice; // 改进建议
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    public String getCustomerDepartment() {
        return customerDepartment;
    }
    public void setCustomerDepartment(String customerDepartment) {
        this.customerDepartment = customerDepartment;
    }
    public String getCustomerScndDepartment() {
        return customerScndDepartment;
    }
    public void setCustomerScndDepartment(String customerScndDepartment) {
        this.customerScndDepartment = customerScndDepartment;
    }
    public String getCustomerGroup() {
        return customerGroup;
    }
    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getIssueDescription() {
        return issueDescription;
    }
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
    public String getAdvice() {
        return advice;
    }
    public void setAdvice(String advice) {
        this.advice = advice;
    }
    public Date getAddTime() {
        return addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    
}
