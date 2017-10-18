/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class ConcessionalBiddingAgainstRival extends EntityBase{

    private String orderId; // 订单号
    private String rival; // 竞争对手
    private String rivalPrice; // 对手价格
    private String routeHref; // 对手线路链接
    private String rivalPriceProof; // 对手价格证据
    private String tuniuPrice; // 我司价格
    private String concessionalLimit; //让价额度
    private String additionPresentation; // 附加赠送
    private String concessionalSide; // 让价方
    private String concessionalName; // 让价人名
    private String unConcessionalReason; // 不予让价原因
    private String customerDepartment; // 客服部
    private String customerGroup; // 客服组
    private String customer; // 客服
    private String productDepartment; // 产品部
    private String productGroup; // 产品组
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    
    
    public String getRival() {
        return rival;
    }
    public void setRival(String rival) {
        this.rival = rival;
    }
    public String getRivalPrice() {
        return rivalPrice;
    }
    public void setRivalPrice(String rivalPrice) {
        this.rivalPrice = rivalPrice;
    }
    public String getRouteHref() {
        return routeHref;
    }
    public void setRouteHref(String routeHref) {
        this.routeHref = routeHref;
    }
    public String getRivalPriceProof() {
        return rivalPriceProof;
    }
    public void setRivalPriceProof(String rivalPriceProof) {
        this.rivalPriceProof = rivalPriceProof;
    }
    public String getTuniuPrice() {
        return tuniuPrice;
    }
    public void setTuniuPrice(String tuniuPrice) {
        this.tuniuPrice = tuniuPrice;
    }
    public String getConcessionalLimit() {
        return concessionalLimit;
    }
    public void setConcessionalLimit(String concessionalLimit) {
        this.concessionalLimit = concessionalLimit;
    }
    public String getAdditionPresentation() {
        return additionPresentation;
    }
    public void setAdditionPresentation(String additionPresentation) {
        this.additionPresentation = additionPresentation;
    }
    public String getConcessionalSide() {
        return concessionalSide;
    }
    public void setConcessionalSide(String concessionalSide) {
        this.concessionalSide = concessionalSide;
    }
    public String getConcessionalName() {
        return concessionalName;
    }
    public void setConcessionalName(String concessionalName) {
        this.concessionalName = concessionalName;
    }
    public String getUnConcessionalReason() {
        return unConcessionalReason;
    }
    public void setUnConcessionalReason(String unConcessionalReason) {
        this.unConcessionalReason = unConcessionalReason;
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
    public String getProductDepartment() {
        return productDepartment;
    }
    public void setProductDepartment(String productDepartment) {
        this.productDepartment = productDepartment;
    }
    public String getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
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
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
}
