/**
 * 
 */
package com.tuniu.gt.tac.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
*/
public class PrdProcurementIssusFeedbak extends EntityBase{

    private String department; // 组别
    private String area; // 区域
    private String destType; // 目的地类型
    private String orderId; // 订单号
    private String routeId; // 线路编号
    private Date departDate; //出发日期
    private String issueType; // 问题类型
    private String prdOfficer; // 产品专员
    private String prdManager; // 产品经理
    private String prdMajordomo; // 产品总监
    private String supplier; // 供应商
    private String description; // 问题描述
    private Integer resolveState; // 解决状态 0：未解决   1：已解决
    private String addPerson; // 添加人（客服）
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer delFlag; // 删除标志位，0：正常，1：删除
    
    private String prdManagerDep;//产品经理组别
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getDestType() {
        return destType;
    }
    public void setDestType(String destType) {
        this.destType = destType;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getRouteId() {
        return routeId;
    }
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    public String getIssueType() {
        return issueType;
    }
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    public String getPrdOfficer() {
        return prdOfficer;
    }
    public void setPrdOfficer(String prdOfficer) {
        this.prdOfficer = prdOfficer;
    }
    public String getPrdManager() {
        return prdManager;
    }
    public void setPrdManager(String prdManager) {
        this.prdManager = prdManager;
    }
    public String getPrdMajordomo() {
        return prdMajordomo;
    }
    public void setPrdMajordomo(String prdMajordomo) {
        this.prdMajordomo = prdMajordomo;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getResolveState() {
        return resolveState;
    }
    public void setResolveState(Integer resolveState) {
        this.resolveState = resolveState;
    }
    public String getAddPerson() {
        return addPerson;
    }
    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson;
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
    public Date getDepartDate() {
        return departDate;
    }
    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }
	public String getPrdManagerDep() {
		return prdManagerDep;
	}
	public void setPrdManagerDep(String prdManagerDep) {
		this.prdManagerDep = prdManagerDep;
	}
}
