/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class NewEmployeesSalaryAdjustment extends EntityBase{

    private String category; // 类别
    private String customerManager; // 客服经理
    private String workNum; // 工号
    private String businessUnitName; // 一级部门
    private String departmentName; // 二级部门
    private String groupName; // 三级部门
    private String customer; // 客服
    private Integer isFilToRprtLstMnth; // 是否为上月漏报员工，0：否，1：是
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCustomerManager() {
        return customerManager;
    }
    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }
    public String getWorkNum() {
        return workNum;
    }
    public void setWorkNum(String workNum) {
        this.workNum = workNum;
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
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public Integer getIsFilToRprtLstMnth() {
        return isFilToRprtLstMnth;
    }
    public void setIsFilToRprtLstMnth(Integer isFilToRprtLstMnth) {
        this.isFilToRprtLstMnth = isFilToRprtLstMnth;
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
