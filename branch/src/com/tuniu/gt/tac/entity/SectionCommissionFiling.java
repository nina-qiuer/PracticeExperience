/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class SectionCommissionFiling extends EntityBase{

    private String orderId; // 订单号
    private String customer; // 客服专员
    private String customerWorkId; // 客服工号
    private String sysRatio; // 系统比例
    private String adjustRatio; // 应调整比例
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getCustomerWorkId() {
        return customerWorkId;
    }
    public void setCustomerWorkId(String customerWorkId) {
        this.customerWorkId = customerWorkId;
    }
    public String getSysRatio() {
        return sysRatio;
    }
    public void setSysRatio(String sysRatio) {
        this.sysRatio = sysRatio;
    }
    public String getAdjustRatio() {
        return adjustRatio;
    }
    public void setAdjustRatio(String adjustRatio) {
        this.adjustRatio = adjustRatio;
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
