/**
 * 
 */
package com.tuniu.gt.returnvisit.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * 售后回访实体
 * 对应数据表：rv_post_sale
 * 
 * @author jiangye
 */
public class PostSaleReturnVisitEntity extends EntityBase {


    /** 投诉单号 */
    private Integer complaintId;
    
    /** 订单号 */
    private Integer orderId;
    
    /**产品名称*/
    private String prdName;
    
    /**处理岗*/
    private String dealDepart;
    
    /**投诉处理人*/
    private String dealName;
    
    /** 投诉处理人经理姓名 */
    private String dealManagerName;
    
    /**回访电话*/
    private String tel;

    /** 投诉处理人所在事业部名 */
    private String businessUnitName;

    /** 投诉处理人所在二级部门名 */
    private String departmentName;

    /** 投诉处理人所在组名 */
    private String groupName;

    /** 售后综合服务评分 满意3分，一般2分，不满意0分 */
    private Integer score;

    /** 不满意原因 */
    private Integer unsatisfyReason;

    /** 回访日期 */
    private Date returnVisitDate;

    /** 添加时间 */
    private Date addTime;

    /** 更新时间 */
    private Date updateTime;

    /** 删除标志位，0：正常，1：删除 */
    private Integer delFlag;

    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getDealDepart() {
        return dealDepart;
    }

    public void setDealDepart(String dealDepart) {
        this.dealDepart = dealDepart;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealManagerName() {
        return dealManagerName;
    }

    public void setDealManagerName(String dealManagerName) {
        this.dealManagerName = dealManagerName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getUnsatisfyReason() {
        return unsatisfyReason;
    }

    public void setUnsatisfyReason(Integer unsatisfyReason) {
        this.unsatisfyReason = unsatisfyReason;
    }

    public Date getReturnVisitDate() {
        return returnVisitDate;
    }

    public void setReturnVisitDate(Date returnVisitDate) {
        this.returnVisitDate = returnVisitDate;
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
