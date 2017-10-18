/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class CustAskConcession extends EntityBase{

    private String orderId; // 订单号
    private String customerDepartment; // 客服部
    private String customerGroup; // 客服组
    private String customer; // 客服
    private String price; // 产品原价
    private String concessionalLimit; //让价额度
    private String personNum; // 订单人数
    private String additionPresentation; // 附加赠送
    private String concessionalBearer; // 让价承担方
    private String prdOfficerName; // 产品姓名
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerDepartment() {
        return customerDepartment;
    }
    public void setCustomerDepartment(String customerDepartment) {
        this.customerDepartment = customerDepartment;
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
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getConcessionalLimit() {
        return concessionalLimit;
    }
    public void setConcessionalLimit(String concessionalLimit) {
        this.concessionalLimit = concessionalLimit;
    }
    public String getPersonNum() {
        return personNum;
    }
    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }
    public String getAdditionPresentation() {
        return additionPresentation;
    }
    public void setAdditionPresentation(String additionPresentation) {
        this.additionPresentation = additionPresentation;
    }
    public String getConcessionalBearer() {
        return concessionalBearer;
    }
    public void setConcessionalBearer(String concessionalBearer) {
        this.concessionalBearer = concessionalBearer;
    }
    public String getPrdOfficerName() {
        return prdOfficerName;
    }
    public void setPrdOfficerName(String prdOfficerName) {
        this.prdOfficerName = prdOfficerName;
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
